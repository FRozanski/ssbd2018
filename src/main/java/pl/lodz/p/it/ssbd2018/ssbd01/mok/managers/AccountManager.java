package pl.lodz.p.it.ssbd2018.ssbd01.mok.managers;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccountAlevel;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AccountException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.facades.AccessLevelFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.facades.AccountAlevelFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.facades.AccountFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.CloneUtils;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.HashUtils;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.SendMailUtils;

/**
 *
 * @author piotrek
 * @author agkan
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class AccountManager implements AccountManagerLocal {

    private static final String DEFAULT_ACCESS_LEVEL = "user";
    private static final Logger loger = Logger.getLogger(AccountManager.class.getName());
    private static final String DEFAULT_URL = "http://studapp.it.p.lodz.pl:8001";

    @EJB
    private AccountFacadeLocal accountFacade;

    @EJB
    private AccessLevelFacadeLocal accessLevelFacade;

    @EJB
    private AccountAlevelFacadeLocal accountAlevelFacade;

    private final SendMailUtils mailSender = new SendMailUtils();

    @Override
    @RolesAllowed("getAllAccounts")
    public List<Account> getAllAccounts() {
        return accountFacade.findAll();
    }

    @Override
    @RolesAllowed("getAllAccessLevels")
    public List<AccessLevel> getAllAccessLevels() {
        return accessLevelFacade.findAll();
    }

    @Override
    @RolesAllowed("getAccountToEdit")
    public Account getAccountToEdit(Account account) {
        Account tmpAccount = accountFacade.find(account.getId());
        return (Account) CloneUtils.deepCloneThroughSerialization(tmpAccount);
    }

    @Override
    @RolesAllowed("changeYourPassword")
    public void changeYourPassword(Account account, String oldPass, String newPassOne, String newPassTwo) {
        Account tmpAccount = accountFacade.find((account.getId()));
        String passHash = tmpAccount.getPassword();
        if (passHash.contentEquals(HashUtils.sha256(oldPass))) {
            if (newPassOne.length() >= 8) {
                if (newPassOne.contentEquals(newPassTwo)) {
                    try {
                        tmpAccount.setPassword(HashUtils.sha256(newPassOne));
                        accountFacade.edit(tmpAccount);
                    } catch (IllegalArgumentException ae) {
                        throw new IllegalArgumentException("Coś się zepsuło.", ae);
                    }
                } else {
                    throw new IllegalArgumentException("Nowe hasła nie są zgodne.");
                }
            } else {
                throw new IllegalArgumentException("Nowe hasło jest za krótkie. Powinno posiadać conajmniej 8 znaków");
            }
        } else {
            throw new IllegalArgumentException("Niepoprawne stare hasło");
        }
    }
    
    @Override
    @RolesAllowed("changeOthersPassword")
    public void changeOthersPassword(Account account, String newPassOne, String newPassTwo) {
        Account tmpAccount = accountFacade.find((account.getId()));
        if (newPassOne.length() > 8) {
            if (newPassOne.contentEquals(newPassTwo)) {
                try {
                    tmpAccount.setPassword(HashUtils.sha256(newPassOne));
                    accountFacade.edit(tmpAccount);
                } catch (IllegalArgumentException ae) {
                    throw new IllegalArgumentException("Coś się zepsuło.", ae);
                }
            } else {
                throw new IllegalArgumentException("Nowe hasła nie są zgodne.");
            }
        } else {
            throw new IllegalArgumentException("Nowe hasło jest za krótkie. Powinno posiadać conajmniej 8 znaków");
        }
    }
    
    @Override
    @RolesAllowed("saveAccountAfterEdit")
    public void saveAccountAfterEdit(Account account) {
        accountFacade.edit(account);
    }

    @Override
    @PermitAll
    public void registerAccount(Account account, ServletContext servletContext) {
        account.setPassword(HashUtils.sha256(account.getPassword()));
        accountFacade.create(account);
        loger.log(Level.INFO, "Account Created.");

        AccountAlevel level = new AccountAlevel();
        level.setIdAccount(account);
        level.setIdAlevel(accessLevelFacade.findByLevel(DEFAULT_ACCESS_LEVEL).get(0));
        accountAlevelFacade.create(level);
        loger.log(Level.INFO, "Access level added to account.");

        this.sendMailWithVeryficationLink(account.getEmail(), createVeryficationLink(account, servletContext));
        loger.log(Level.INFO, "E-mail with activation token sent.");
    }

    @Override
    @RolesAllowed("lockAccount")
    public void lockAccount(long accountId) throws AccountException{
        try {
            Account account = accountFacade.find(accountId);
            account.setActive(false);
            accountFacade.edit(account);
            
            mailSender.sendMailAfterAccountLock(account.getEmail());
        //FIXME - dodac podzial na wyjatki
        } catch (NullPointerException npe) {
            throw new AccountException("lock_error");
        } catch (OptimisticLockException oe) {
            throw new AccountException("lock_error");
        }
    }

    @Override
    @RolesAllowed("unlockAccount")
    public void unlockAccount(long accountId) throws AccountException {
        try { 
            Account account = accountFacade.find(accountId);
            account.setActive(true);
            accountFacade.edit(account);
        //FIXME - dodac podzial na wyjatki
        } catch (NullPointerException npe) {
            throw new AccountException("unlock_error");
        } catch (OptimisticLockException oe) {
            throw new AccountException("unlock_error");
        }
        
    }

    @Override
    @RolesAllowed("addAccessLevelToAccount")
    public void addAccessLevelToAccount(AccessLevel accessLevel, Account account) {
        AccountAlevel accountAlevel = new AccountAlevel();
        accountAlevel.setIdAlevel(accessLevel);
        accountAlevel.setIdAccount(account);
        accountAlevelFacade.create(accountAlevel);
    }

    @Override
    @RolesAllowed("dismissAccessLevelFromAccount")
    public void dismissAccessLevelFromAccount(AccessLevel accessLevel, Account account) {
        List<AccountAlevel> accountAlevels = accountAlevelFacade.findAll();
        AccountAlevel accountAlevel = null;
        for (AccountAlevel aal : accountAlevels) {
            if (aal.getIdAccount().equals(account)
                    && aal.getIdAlevel().equals(accessLevel)) {
                accountAlevel = aal;
                break;
            }
        }
        if (accountAlevel != null) {
            accountAlevelFacade.remove(accountAlevel);
        }
    }

    @Override
    public void sendMailWithVeryficationLink(String mail, String veryficationLink) {
        mailSender.sendVerificationEmail(mail, veryficationLink);
    }

    @Override
    @PermitAll
    public void confirmAccount(Account account) throws AppBaseException {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Timestamp(calendar.getTime().getTime()));
            Date confirmationDate = new Date(calendar.getTime().getTime());
            long timeDiff = account.getExpiryDate().getTime() - confirmationDate.getTime();

            if (account.getConfirm() == true || account.getConfirmationDate() != null || timeDiff <= 0 || account.isUsed() == true) {
                throw new PersistenceException();
            }

            account.setConfirm(true);
            account.setConfirmationDate(confirmationDate);
            account.setUsed(true);
            accountFacade.edit(account);
            
            mailSender.sendMailAfterActivation(account.getEmail());
        } catch (OptimisticLockException oe) {
            throw AccountException.createAccountExceptionWithOptimisticLock(oe, account);
        } catch (PersistenceException pe) {
            throw AccountException.createAccountExceptionWithDbConstraint(pe, account);
        }
    }

    @Override
    public String getVeryficationToken(Account account) {
        return accountFacade.find(account.getId()).getToken();
    }

    @Override
    public Account getAccountByLogin(String login) {
        return accountFacade.findByLogin(login);
    }

    @Override
    public Account getAccountByToken(String token) throws AppBaseException {
        return accountFacade.findByToken(token);
    }

    private String createVeryficationLink(Account account, ServletContext servletContext) {
        String veryficationToken = account.getToken();
        String veryficationLink = DEFAULT_URL + servletContext.getContextPath();
        veryficationLink = veryficationLink + "/registrationConfirm.xhtml?token=" + veryficationToken;
        return veryficationLink;
    }

    @Override
    @RolesAllowed("getAccountById")
    public Account getAccountById(long id) {
        Account tmpAccount = accountFacade.find(id);
        return (Account) CloneUtils.deepCloneThroughSerialization(tmpAccount);
    }

    @Override
    @RolesAllowed("getAccountAlevel")
    public AccountAlevel getAccountAlevel(Long idAccount, Long idAccessLevel) {
        AccountAlevel accountAlevel = accountAlevelFacade.findByAccountAndAccessLevel(idAccount, idAccessLevel);
        return (AccountAlevel) CloneUtils.deepCloneThroughSerialization(accountAlevel);
    }

    @Override
    @RolesAllowed("getAccessLevelById")
    public AccessLevel getAccessLevelById(Long idAccessLevel) {
        AccessLevel accessLevel = accessLevelFacade.find(idAccessLevel);
        return (AccessLevel) CloneUtils.deepCloneThroughSerialization(accessLevel);
    }
}
