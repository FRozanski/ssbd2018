package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author agkan
 */
@Named
@RequestScoped
public class ListAccountBean {

    public ListAccountBean() {
    }
    
    @Inject
    private AccountController accountController;
    
    public List<Account> getAccounts() {
        return accountController.getAllAccounts();
    }
    
    public List<AccessLevel> getAllAccessLevels() {
        return accountController.getAllAccessLevels();
    }
    
    public String editAccount(Account account) {
        accountController.getAccountToEdit(account);
        return "list-edit";
    }
    
    public void saveAccount(Account account) {
        accountController.saveAccountAfterEdit(account);
    }
    
    public void lockAccount(Account account) {
        accountController.lockAccount(account);
    }
    
    public void unlockAccount(Account account) {
        accountController.unlockAccount(account);
    }
    
    public void confirmAccount(Account account) {
        accountController.confirmAccount(account);
    }
}
