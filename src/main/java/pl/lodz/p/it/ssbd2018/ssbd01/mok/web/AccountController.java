package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.managers.AccountManagerLocal;

/**
 *
 * @author agkan
 * @author michalmalec
 */
@ManagedBean(name = "accountController")
@SessionScoped
public class AccountController implements Serializable{
    
    @EJB
    private transient AccountManagerLocal mokEndpointLocal;
    
    private Account account;
    private Account newAccount;

    List<Account> getAllAccounts() {
        return mokEndpointLocal.getAllAccounts();
    }
    
    List<AccessLevel> getAllAccessLevels() {
        return mokEndpointLocal.getAllAccessLevels();
    }

    void getAccountToEdit(Account account) {
        this.account = mokEndpointLocal.getAccountToEdit(account);
    }

    void saveAccountAfterEdit(Account account) {
        mokEndpointLocal.saveAccountAfterEdit(account);
    }
    
    public void registerAccount(Account a) {
        newAccount = new Account();
        newAccount.setLogin(a.getLogin());
        newAccount.setPassword(a.getPassword());
        newAccount.setEmail(a.getEmail());
        newAccount.setName(a.getName());
        newAccount.setSurname(a.getSurname());
        newAccount.setPhone(a.getPhone());
        newAccount.setStreet(a.getStreet());
        newAccount.setStreetNumber(a.getStreetNumber());
        newAccount.setFlatNumber(a.getFlatNumber());
        newAccount.setPostalCode(a.getPostalCode());
        newAccount.setCity(a.getCity());
        newAccount.setCountry(a.getCountry());
//        mokEndpointLocal.registerAccount(newAccount);   
    }

    void sendMailWithVeryficationLink(String email, String veryficationLink) {
        mokEndpointLocal.sendMailWithVeryficationLink(email, veryficationLink);
    }
    
    public void confirmAccount(Account account) throws AppBaseException {
        mokEndpointLocal.confirmAccount(account);
    }
    
    
    public void addAccessLevelToAccount(AccessLevel accessLevel, Account account) {
        mokEndpointLocal.addAccessLevelToAccount(accessLevel, account);
    }
    
    public void dismissAccessLevelFromAccount(AccessLevel accessLevel, Account account) {
        mokEndpointLocal.dismissAccessLevelFromAccount(accessLevel, account);
    }

    String getToken(Account account) {
        return mokEndpointLocal.getVeryficationToken(account);
    }    

    Account getAccountByLogin(String login) {
        return mokEndpointLocal.getAccountByLogin(login);
    }
    
    Account getAccountByToken(String token) throws AppBaseException{
        return mokEndpointLocal.getAccountByToken(token);
    }
}
