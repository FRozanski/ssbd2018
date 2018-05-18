package pl.lodz.p.it.ssbd2018.ssbd01.mok.endpoints;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
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
@Stateful
public class MOKEndpoint implements MOKEndpointLocal {
    
    private static final String DEFAULT_ACCESS_LEVEL = "user";
    
    @EJB
    private AccountFacadeLocal accountFacade;
    
    @EJB
    private AccessLevelFacadeLocal accessLevelFacade;
    
    @EJB
    private AccountAlevelFacadeLocal accountAlevelFacade;
    
    private SendMailUtils mailSender = new SendMailUtils();

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
//    @RolesAllowed("getAccountToEdit")
    public Account getAccountToEdit(Account account) {
        Account tmpAccount = accountFacade.find(account.getId());        
        return (Account) CloneUtils.deepCloneThroughSerialization(tmpAccount);
    }

    @Override
//    @RolesAllowed("saveAccountAfterEdit")
    public void saveAccountAfterEdit(Account account) {
        accountFacade.edit(account);
    }

    @Override
    @PermitAll
    public void registerAccount(Account account) {
        account.setPassword(HashUtils.sha256(account.getPassword()));
        accountFacade.create(account);
        
        AccountAlevel level = new AccountAlevel();
        level.setIdAccount(account);
        level.setIdAlevel(accessLevelFacade.findByLevel(DEFAULT_ACCESS_LEVEL).get(0));
        accountAlevelFacade.create(level);
    }

    @Override
    @RolesAllowed("lockAccount")
    public void lockAccount(Account account) {
        account.setActive(false);
        accountFacade.edit(account);
    }

    @Override
    @RolesAllowed("unlockAccount")
    public void unlockAccount(Account account) {
        account.setActive(true);
        accountFacade.edit(account);
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
        if (accountAlevel != null)
            accountAlevelFacade.remove(accountAlevel);
    }

    @Override
    public void sendMailWithVeryficationLink(String mail, String veryficationLink) {        
        mailSender.sendEmail(mail, veryficationLink);
    }

    @Override
    public void confirmAccount(Account account) throws AppBaseException{   
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Timestamp(calendar.getTime().getTime()));
            Date confirmationDate = new Date(calendar.getTime().getTime());
            long timeDiff = account.getExpiryDate().getTime() - confirmationDate.getTime();
            
            if (account.getConfirm() == true || account.getConfirmationDate() != null || timeDiff <= 0 || account.isUsed() == true)
                throw new PersistenceException();
            
            account.setConfirm(true);
            account.setConfirmationDate(confirmationDate);
            account.setUsed(true);
            accountFacade.edit(account);
        } catch(OptimisticLockException oe) {
            throw AccountException.createAccountExceptionWithOptimisticLock(oe, account);
        } catch(PersistenceException pe) {
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
    public Account getAccountByToken(String token) throws AppBaseException{
        return accountFacade.findByToken(token);
    }

    @Override
//    @RolesAllowed("getAccountById")
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
