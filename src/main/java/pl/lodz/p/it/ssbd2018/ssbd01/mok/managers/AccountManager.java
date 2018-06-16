package pl.lodz.p.it.ssbd2018.ssbd01.mok.managers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
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
import pl.lodz.p.it.ssbd2018.ssbd01.tools.HashUtils;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.LoggerInterceptor;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.SendMailUtils;

/**
 *
 * @author piotrek
 * @author agkan
 * @author michalmalec
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
@Interceptors(LoggerInterceptor.class)
public class AccountManager implements AccountManagerLocal {

    private static final String DEFAULT_ACCESS_LEVEL = "user";
    private static final Logger loger = Logger.getLogger(AccountManager.class.getName());
    private static final String DEFAULT_URL = "http://studapp.it.p.lodz.pl:8001";

    @EJB
    private AccountFacadeLocal accountFacade;

    @EJB
    private ArchivalPasswordFacadeLocal archivalPasswordFacade;

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
    @RolesAllowed("changeMyPassword")
    public void changeMyPassword(Account account) throws AppBaseException {
        account.setPassword(HashUtils.sha256(account.getPassword()));
        accountFacade.edit(account);
        ArchivalPassword archivalPassword = new ArchivalPassword(account.getPassword(), generateCurrentDate(), account);
        archivalPasswordFacade.create(archivalPassword);
    }

    @Override
    @RolesAllowed("changeOthersPassword")
    public void changeOthersPassword(Account account) throws AppBaseException {
        account.setPassword(HashUtils.sha256(account.getPassword()));
        accountFacade.edit(account);
        ArchivalPassword archivalPassword = new ArchivalPassword(account.getPassword(), generateCurrentDate(), account);
        archivalPasswordFacade.create(archivalPassword);
    }

    @Override
    @RolesAllowed("saveAccountAfterEdit")
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
        level.setIdAlevel(accessLevelFacade.findByLevel(DEFAULT_ACCESS_LEVEL));
        accountAlevelFacade.create(level);

        ArchivalPassword archivalPassword = new ArchivalPassword(account.getPassword(), generateCurrentDate(), account);
        archivalPasswordFacade.create(archivalPassword);

        this.sendMailWithVeryficationLink(account.getEmail(), createVeryficationLink(account, servletContext));
    }

    @Override
    @RolesAllowed("lockAccount")
    public void lockAccount(long accountId) throws AppBaseException {
        Account account = accountFacade.find(accountId);
        account.setActive(false);
        accountFacade.edit(account);
        mailSender.sendMailAfterAccountLock(account.getEmail());
    }

    @Override
    @RolesAllowed("unlockAccount")
    public void unlockAccount(long accountId) throws AppBaseException {
        Account account = accountFacade.find(accountId);
        account.setActive(true);
        accountFacade.edit(account);
    }

    @Override
    @RolesAllowed("confirmAccount")
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
    @RolesAllowed("getMyAccountByLogin")
    public Account getMyAccountByLogin(String login) throws AppBaseException {
        return accountFacade.findByLogin(login);
    }

    @Override
    @RolesAllowed("getAccountById")
    public Account getAccountById(long id) throws AppBaseException {
        Account tmpAccount = accountFacade.find(id);
        return tmpAccount;
    }

    @Override
    @RolesAllowed("saveMyAccountAfterEdit")
    public void saveMyAccountAfterEdit(Account myAccount) throws AppBaseException {
        accountFacade.edit(myAccount);
    }
    
    @Override
    @RolesAllowed("updateLoginDateAndIp")
    public void updateLoginDateAndIp(String login, String ip) throws AppBaseException {
        Account account = this.getMyAccountByLogin(login);
        account.setNumberOfLogins(account.getNumberOfLogins() + 1);
        account.setLastLoginDate(this.generateCurrentDate());
        account.setLastLoginIp(ip);
        accountFacade.edit(account);
    }

    @Override
    @RolesAllowed("alterAccountAccessLevel")
    public void alterAccountAccessLevel(Account account, List<String> accessLevelDto) throws AppBaseException {
        if (accessLevelDto.isEmpty()) {
            throw new AccountWithNoAccessLevelException("no_access_level_error");
        }
        List<AccessLevel> accountAccessLevel = account
                .getAccessLevelCollection()
                .stream()
                .map(AccountAlevel::getIdAlevel)
                .collect(Collectors.toList());
        List<AccessLevel> accessLevel = new ArrayList<>();
        for (int i = 0; i < accessLevelDto.size(); i++) {
            accessLevel.add(accessLevelFacade.findByLevel(accessLevelDto.get(i)));
        }
        for (int i = 0; i < accessLevel.size(); i++) {
            if (!accountAccessLevel.contains(accessLevel.get(i))) {
                this.addAccessLevelToAccount(account, accessLevel.get(i));
            }
        }
        for (int i = 0; i < accountAccessLevel.size(); i++) {
            if (!accessLevel.contains(accountAccessLevel.get(i))) {
                this.dismissAccessLevelFromAccount(account, accountAccessLevel.get(i));
            }
        }
    }

    /**
     * Metoda wykorzystywana przez metodę {@link #alterAccountAccessLevel(pl.lodz.p.it.ssbd2018.ssbd01.entities.Account, java.util.List)} w celu dodanie poziomu dostępu.
     * @param account typu {@link Account}
     * @param accessLevel typu {@link AccessLevel}
     * @throws AppBaseException 
     */
    @RolesAllowed("addAccessLevelToAccount")
    private void addAccessLevelToAccount(Account account, AccessLevel accessLevel) throws AppBaseException {
        AccountAlevel accountAlevel = new AccountAlevel();
        accountAlevel.setIdAlevel(accessLevel);
        accountAlevel.setIdAccount(account);
        accountAlevelFacade.create(accountAlevel);
    }

    /**
     * Metoda wykorzystywana przez metodę {@link #alterAccountAccessLevel(pl.lodz.p.it.ssbd2018.ssbd01.entities.Account, java.util.List)} w celu odebrania poziomu dostępu. 
     * @param account typu {@link Account}
     * @param accessLevel typu {@link AccessLevel}
     * @throws AppBaseException 
     */
    @RolesAllowed("dismissAccessLevelFromAccount")
    private void dismissAccessLevelFromAccount(Account account, AccessLevel accessLevel) throws AppBaseException {
        AccountAlevel level = accountAlevelFacade.
                findByAccountAndAccessLevel(account, accessLevel);
        accountAlevelFacade.remove(level);
    }

    
    @Override
    @RolesAllowed("getAllArchivalPasswordsByAccount")
    public List<ArchivalPassword> getAllArchivalPasswordsByAccount(long accountId) {
        return archivalPasswordFacade.findByAccountId(accountId);
    }
    
    /**
     * Metoda zwracająca aktualną datę.
     * @return nowo utworzony obiekt typu {@link Date}
     */
    private Date generateCurrentDate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw"));
        return new Date(calendar.getTime().getTime());
    }

    /**
     * Metoda sprawdzająca ważność tokenu dla danego konta.
     * @param account typu {@link Account}
     * @throws AppBaseException 
     */
    private void checkVerificationToken(Account account) throws AppBaseException {
        Date now = Calendar.getInstance().getTime();
        if (now.after(account.getExpiryDate())) {
            throw new TokenExpiredException("token_expired_exception");
        } else if (account.isUsed() == true) {
            throw new TokenUsedException("token_used_exception");
        }
    }

    /**
     * Metoda sprawdzająca czy dane konto zostało już aktywowane.
     * @param account typu {@link Account}
     * @throws AppBaseException 
     */
    private void checkIfAccountConfirmed(Account account) throws AppBaseException {
        if (account.getConfirm() == true) {
            throw new AccountWasConfirmed("account_was_confirmed_exception");
        }
    }

    /**
     * Metoda pozwalająca wysłać mail z linkiem aktywacyjnym.
     * @param mail adres typu {@link String}
     * @param veryficationLink typu {@link String}
     */
    private void sendMailWithVeryficationLink(String mail, String veryficationLink) {
        mailSender.sendVerificationEmail(mail, veryficationLink);
    }

    /**
     * Metoda tworząca dla danego konta link aktywacyjny.
     * @param account
     * @param servletContext
     * @return link aktywacyjny typu {@link String}
     */
    private String createVeryficationLink(Account account, ServletContext servletContext) {
        String veryficationToken = account.getToken();
        String veryficationLink = DEFAULT_URL + servletContext.getContextPath();
        veryficationLink = veryficationLink + "/ng/#/registrationConfirm?token=" + veryficationToken;
        return veryficationLink;
    }    
}
