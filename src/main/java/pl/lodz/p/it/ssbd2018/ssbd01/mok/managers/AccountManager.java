package pl.lodz.p.it.ssbd2018.ssbd01.mok.managers;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.*;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ArchivalPassword;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccountNotFoundException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccountOptimisticException;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.facades.AccessLevelFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.facades.AccountAlevelFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.facades.AccountFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.facades.ArchivalPasswordFacadeLocal;
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
    private ArchivalPasswordFacadeLocal archivalPasswordFacadeLocal;

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
    public void changeYourPassword(Account account, String oldPass, String newPassOne, String newPassTwo) throws AppBaseException {
        Account tmpAccount = accountFacade.find((account.getId()));
        String passHash = tmpAccount.getPassword();
        if (!passHash.contentEquals(HashUtils.sha256(oldPass))) {
            throw new PasswordNotMatch("password_not_match_error");
        }

        try {
            tmpAccount.setPassword(HashUtils.sha256(newPassOne));
            accountFacade.edit(tmpAccount);
            ArchivalPassword archivalPassword = new ArchivalPassword(tmpAccount.getPassword(), generateCurrentDate(), tmpAccount);
            archivalPasswordFacadeLocal.create(archivalPassword);
        } catch (AppBaseException ex) {
            throw new AccountException("unknown_exception");
        }
    }

    @Override
    @RolesAllowed("changeOthersPassword")
    public void changeOthersPassword(Account account, String newPassOne, String newPassTwo) throws AppBaseException {
        Account tmpAccount = accountFacade.find((account.getId()));

        try {
            tmpAccount.setPassword(HashUtils.sha256(newPassOne));
            accountFacade.edit(tmpAccount);
            ArchivalPassword archivalPassword = new ArchivalPassword(tmpAccount.getPassword(), generateCurrentDate(), tmpAccount);
            archivalPasswordFacadeLocal.create(archivalPassword);
        } catch (AppBaseException ex) {
            throw new AccountException("unknown_exception");
        }
    }

    @Override
    @RolesAllowed("saveAccountAfterEdit")
    public void saveAccountAfterEdit(Account account) throws AppBaseException {
        try {
            accountFacade.edit(account);
        } catch (OptimisticLockException oe) {
            throw new AccountOptimisticException("account_optimistic_error");
        }
    }

    @Override
    @PermitAll
    public void registerAccount(Account account, ServletContext servletContext) throws AppBaseException {
        account.setPassword(HashUtils.sha256(account.getPassword()));
        accountFacade.create(account);

        AccountAlevel level = new AccountAlevel();
        level.setIdAccount(account);
        level.setIdAlevel(accessLevelFacade.findByLevel(DEFAULT_ACCESS_LEVEL).get(0));
        accountAlevelFacade.create(level);

        ArchivalPassword archivalPassword = new ArchivalPassword(account.getPassword(), generateCurrentDate(), account);
        archivalPasswordFacadeLocal.create(archivalPassword);

        this.sendMailWithVeryficationLink(account.getEmail(), createVeryficationLink(account, servletContext));
    }

    @Override
    @RolesAllowed("lockAccount")
    public void lockAccount(long accountId) throws AccountException {
        try {
            Account account = accountFacade.find(accountId);
            account.setActive(false);
            accountFacade.edit(account);
            mailSender.sendMailAfterAccountLock(account.getEmail());

        } catch (NullPointerException npe) {
            throw new AccountNotFoundException("wrong_account_id_error");
        } catch (OptimisticLockException oe) {
            throw new AccountOptimisticException("account_optimistic_error");
        } catch (AppBaseException ex) {
            throw new AccountException("unknow_error");
        }
    }

    @Override
    @RolesAllowed("unlockAccount")
    public void unlockAccount(long accountId) throws AccountException {
        try {
            Account account = accountFacade.find(accountId);
            account.setActive(true);
            accountFacade.edit(account);
        } catch (NullPointerException npe) {
            throw new AccountNotFoundException("wrong_account_id_error");
        } catch (OptimisticLockException oe) {
            throw new AccountOptimisticException("account_optimistic_error");
        } catch (AppBaseException ex) {
            throw new AccountException("unknow_error");
        }

    }

    @Override
    @RolesAllowed("addAccessLevelToAccount")
    public void addAccessLevelToAccount(AccessLevel accessLevel, Account account) throws AppBaseException {
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
            throw new AccountOptimisticException("account_optimistic_error");
        }
    }

    @Override
    @RolesAllowed("getAccountById")
    public String getVeryficationToken(Account account) {
        return accountFacade.find(account.getId()).getToken();
    }

    @Override
    @RolesAllowed("getAccountById")
    public Account getAccountByLogin(String login) throws AppBaseException {
        Account tmpAccount = accountFacade.findByLogin(login);
        return (Account) CloneUtils.deepCloneThroughSerialization(tmpAccount);
    }

    @Override
    @RolesAllowed("getAccountById")
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

    @Override
    @RolesAllowed("getMyAccountById")
    public Account getMyAccountById(long id) {
        Account tmpAccount = accountFacade.find(id);
        return (Account) CloneUtils.deepCloneThroughSerialization(tmpAccount);
    }

    @Override
    @RolesAllowed("saveMyAccountAfterEdit")
    public void saveMyAccountAfterEdit(Account myAccount) throws AppBaseException {
        accountFacade.edit(myAccount);
    }

    private Date generateCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        return new Date(calendar.getTime().getTime());
    }
}
