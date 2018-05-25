package pl.lodz.p.it.ssbd2018.ssbd01.mok.managers;

import java.util.List;
import javax.ejb.Local;
import javax.servlet.ServletContext;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccountAlevel;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 *
 * @author piotrek
 * @author michalmalec
 */
@Local
public interface AccountManagerLocal {
    
    List<Account> getAllAccounts();
    
    List<AccessLevel> getAllAccessLevels();
    
    Account getAccountToEdit(Account account) throws AppBaseException;
    
    void saveAccountAfterEdit(Account account) throws AppBaseException; 
    
    void registerAccount(Account account, ServletContext servletContext) throws AppBaseException;
    
    void confirmAccount(long accountId) throws AppBaseException;
    
    void lockAccount(long accountId) throws AppBaseException;
    
    void confirmAccountByToken(String token) throws AppBaseException;
            
    void unlockAccount(long accountId) throws AppBaseException;
    
    public Account getMyAccountToEdit(Account account) throws AppBaseException;
    
    public Account getMyAccountByLogin(String login) throws AppBaseException;
    
    void addAccessLevelToAccount(AccessLevel accessLevel, Account account) throws AppBaseException;
    
    void dismissAccessLevelFromAccount(AccessLevel accessLevel, Account account);

    public String getVeryficationToken(Account account) throws AppBaseException;
    
    public Account getAccountById(long id) throws AppBaseException;
    
    public Account getAccountByLogin(String login) throws AppBaseException;
    
    public Account getAccountByToken(String token) throws AppBaseException;

    public void sendMailWithVeryficationLink(String mail, String veryficationLink);
    
    public AccountAlevel getAccountAlevel(Long idAccount, Long idAccessLevel);

    public AccessLevel getAccessLevelById(Long idAccessLevel) throws AppBaseException;
    
    public void changeMyPassword(Account account) throws AppBaseException;
    
    public void changeOthersPassword(Account account) throws AppBaseException;

    public Account getMyAccountById(long id) throws AppBaseException;

    public void saveMyAccountAfterEdit(Account myAccount) throws AppBaseException;
}
