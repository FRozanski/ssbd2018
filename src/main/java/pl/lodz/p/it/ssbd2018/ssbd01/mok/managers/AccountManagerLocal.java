package pl.lodz.p.it.ssbd2018.ssbd01.mok.managers;

import java.util.List;
import javax.ejb.Local;
import javax.servlet.ServletContext;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.BasicAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ArchivalPassword;
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
    
    List<ArchivalPassword> getAllArchivalPasswordsByAccount(long accountId);
    
    Account getAccountToEdit(Account account) throws AppBaseException;
    
    void saveAccountAfterEdit(Account account) throws AppBaseException; 
    
    void registerAccount(Account account, ServletContext servletContext) throws AppBaseException;
    
    void confirmAccount(long accountId) throws AppBaseException;
    
    void lockAccount(long accountId) throws AppBaseException;
    
    void confirmAccountByToken(String token) throws AppBaseException;
            
    void unlockAccount(long accountId) throws AppBaseException;
    
    public void updateLoginDateAndIp(String login, String ip) throws AppBaseException;
    
    public void alterAccountAccessLevel(Account account, List<String> accessLevel) throws AppBaseException;
    
    public Account getMyAccountToEdit(Account account) throws AppBaseException;
    
    public Account getMyAccountByLogin(String login) throws AppBaseException;

    public String getVeryficationToken(Account account) throws AppBaseException;
    
    public Account getAccountById(long id) throws AppBaseException;
    
    public Account getAccountByLogin(String login) throws AppBaseException;
    
    public Account getAccountByToken(String token) throws AppBaseException;

    public AccessLevel getAccessLevelById(Long idAccessLevel) throws AppBaseException;
    
    public void changeMyPassword(Account account) throws AppBaseException;
    
    public void changeOthersPassword(Account account) throws AppBaseException;

    public Account getMyAccountById(long id) throws AppBaseException;

    public void saveMyAccountAfterEdit(Account myAccount) throws AppBaseException;
}
