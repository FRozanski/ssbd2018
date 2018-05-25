package pl.lodz.p.it.ssbd2018.ssbd01.mok.managers;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.servlet.ServletContext;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccountAlevel;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.*;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ArchivalPassword;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
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
    //@RolesAllowed("getAllAccounts")
    public List<Account> getAllAccounts() {
        return accountFacade.findAll();
    }

    @Override
    //@RolesAllowed("getAllAccessLevels")
    public List<AccessLevel> getAllAccessLevels() {
        return accessLevelFacade.findAll();
    }

    @Override
    //@RolesAllowed("getAccountToEdit")
    public Account getAccountToEdit(Account account) throws AppBaseException {
        Account tmpAccount = accountFacade.find(account.getId());
        return (Account) CloneUtils.deepCloneThroughSerialization(tmpAccount);
    }

    @Override
    //@RolesAllowed("getMyAccountToEdit")
    public Account getMyAccountToEdit(Account account) throws AppBaseException {
        Account tmpAccount = accountFacade.find(account.getId());
        return (Account) CloneUtils.deepCloneThroughSerialization(tmpAccount);
    }

    @Override
    //@RolesAllowed("changeYourPassword")
    public void changeMyPassword(Account account) throws AppBaseException {
        account.setPassword(HashUtils.sha256(account.getPassword()));
        accountFacade.edit(account);
        ArchivalPassword archivalPassword = new ArchivalPassword(account.getPassword(), generateCurrentDate(), account);
        archivalPasswordFacadeLocal.create(archivalPassword);
    }

    @Override
    //@RolesAllowed("changeOthersPassword")
    public void changeOthersPassword(Account account) throws AppBaseException {
        account.setPassword(HashUtils.sha256(account.getPassword()));
        accountFacade.edit(account);
        ArchivalPassword archivalPassword = new ArchivalPassword(account.getPassword(), generateCurrentDate(), account);
        archivalPasswordFacadeLocal.create(archivalPassword);
    }

    @Override
    //@RolesAllowed("saveAccountAfterEdit")
    public void saveAccountAfterEdit(Account account) throws AppBaseException {
        accountFacade.edit(account);
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
    //@RolesAllowed("lockAccount")
    public void lockAccount(long accountId) throws AppBaseException {
        Account account = accountFacade.find(accountId);
        account.setActive(false);
        accountFacade.edit(account);
        mailSender.sendMailAfterAccountLock(account.getEmail());
    }

    @Override
    //@RolesAllowed("unlockAccount")
    public void unlockAccount(long accountId) throws AppBaseException {
        Account account = accountFacade.find(accountId);
        account.setActive(true);
        accountFacade.edit(account);
    }

    @Override
    //@RolesAllowed("addAccessLevelToAccount")
    public void addAccessLevelToAccount(long accountId, long accessLevelId) throws AppBaseException {
        AccountAlevel accountAlevel = new AccountAlevel();
        AccessLevel accessLevel = accessLevelFacade.find(accessLevelId);
        Account account = accountFacade.find(accountId);
        accountAlevel.setIdAlevel(accessLevel);
        accountAlevel.setIdAccount(account);
        accountAlevelFacade.create(accountAlevel);
    }

    @Override
    //@RolesAllowed("dismissAccessLevelFromAccount")
    public void dismissAccessLevelFromAccount(long accountId, long accessLevelId) throws AppBaseException {
        AccessLevel accessLevel = accessLevelFacade.find(accessLevelId);
        Account account = accountFacade.find(accountId);
        AccountAlevel level = accountAlevelFacade.
                findByAccountAndAccessLevel(account, accessLevel);
        accountAlevelFacade.remove(level);
    }

    @Override
    public void sendMailWithVeryficationLink(String mail, String veryficationLink) {
        mailSender.sendVerificationEmail(mail, veryficationLink);
    }

    @Override
    //@RolesAllowed("confirmAccount")
    public void confirmAccount(long accountId) throws AppBaseException {
        Account account = accountFacade.find(accountId);
        this.checkIfAccountConfirmed(account);

        Date confirmationDate = Calendar.getInstance().getTime();
        account.setConfirm(true);
        account.setConfirmationDate(confirmationDate);
        accountFacade.edit(account);
        mailSender.sendMailAfterActivation(account.getEmail());
    }

    @Override
    @PermitAll
    public void confirmAccountByToken(String token) throws AppBaseException {
        Account account = accountFacade.findByToken(token);
        Date confirmationDate = Calendar.getInstance().getTime();
        this.checkIfAccountConfirmed(account);
        this.checkVerificationToken(account);
        account.setConfirm(true);
        account.setConfirmationDate(confirmationDate);
        account.setUsed(true);
        accountFacade.edit(account);
        mailSender.sendMailAfterActivation(account.getEmail());
    }

    @Override
    //@RolesAllowed("getAccountById")
    public String getVeryficationToken(Account account) throws AppBaseException {
        return accountFacade.find(account.getId()).getToken();
    }

    @Override
    //@RolesAllowed("getAccountById")
    public Account getAccountByLogin(String login) throws AppBaseException {
        Account tmpAccount = accountFacade.findByLogin(login);
        return (Account) CloneUtils.deepCloneThroughSerialization(tmpAccount);
    }

    @Override
    //@RolesAllowed("getMyAccountById")
    public Account getMyAccountByLogin(String login) throws AppBaseException {
        Account tmpAccount = accountFacade.findByLogin(login);
        return (Account) CloneUtils.deepCloneThroughSerialization(tmpAccount);
    }

    @Override
    //@RolesAllowed("getAccountById")
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
    //@RolesAllowed("getAccountById")
    public Account getAccountById(long id) throws AppBaseException {
        Account tmpAccount = accountFacade.find(id);
        return tmpAccount;
    }

    @Override
    //@RolesAllowed("getAccessLevelById")
    public AccessLevel getAccessLevelById(Long idAccessLevel) throws AppBaseException {
        AccessLevel accessLevel = accessLevelFacade.find(idAccessLevel);
        return (AccessLevel) CloneUtils.deepCloneThroughSerialization(accessLevel);
    }

    @Override
    //@RolesAllowed("getMyAccountById")
    public Account getMyAccountById(long id) throws AppBaseException {
        Account tmpAccount = accountFacade.find(id);
        return (Account) CloneUtils.deepCloneThroughSerialization(tmpAccount);
    }

    @Override
    //@RolesAllowed("saveMyAccountAfterEdit")
    public void saveMyAccountAfterEdit(Account myAccount) throws AppBaseException {
        accountFacade.edit(myAccount);
    }

    private Date generateCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        return new Date(calendar.getTime().getTime());
    }

    private void checkVerificationToken(Account account) throws AppBaseException {
        Date now = Calendar.getInstance().getTime();
        if (now.after(account.getExpiryDate())) {
            throw new TokenExpiredException("token_expired_exception");
        } else if (account.isUsed() == true) {
            throw new TokenUsedException("token_used_exception");
        }
    }

    private void checkIfAccountConfirmed(Account account) throws AppBaseException {
        if (account.getConfirm() == true) {
            throw new AccountWasConfirmed("account_was_confirmed_exception");
        }
    }
}
