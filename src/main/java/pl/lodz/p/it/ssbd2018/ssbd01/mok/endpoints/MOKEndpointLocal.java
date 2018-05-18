package pl.lodz.p.it.ssbd2018.ssbd01.mok.endpoints;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccountAlevel;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 *
 * @author piotrek
 */
@Local
public interface MOKEndpointLocal {
    
    List<Account> getAllAccounts();
    
    List<AccessLevel> getAllAccessLevels();
    
    Account getAccountToEdit(Account account);
    
    void saveAccountAfterEdit(Account account); 
    
    void registerAccount(Account account);
    
    void confirmAccount(Account account) throws AppBaseException;
    
    void lockAccount(Account account);
    
    void unlockAccount(Account account);
    
    void addAccessLevelToAccount(AccessLevel accessLevel, Account account);
    
    void dismissAccessLevelFromAccount(AccessLevel accessLevel, Account account);

    public String getVeryficationToken(Account account);
    
    public Account getAccountById(long id);

    public Account getAccountByLogin(String login);
    
    public Account getAccountByToken(String token) throws AppBaseException;

    public void sendMailWithVeryficationLink(String mail, String veryficationLink);

    public AccountAlevel getAccountAlevel(Long idAccount, Long idAccessLevel);

    public AccessLevel getAccessLevelById(Long idAccessLevel);
}
