package pl.lodz.p.it.ssbd2018.ssbd01.mok.endpoints;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

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
    
    void confirmAccount(Account account);
    
    void lockAccount(Account account);
    
    void unlockAccount(Account account);
    
    void addAccessLevelToAccount(AccessLevel accessLevel, Account account);
    
    void dismissAccessLevelFromAccount(AccessLevel accessLevel, Account account);

    public String getVeryficationToken(Account account);

    public Account getAccountByLogin(String login);
    
    public Account getAccountByToken(String token);
}
