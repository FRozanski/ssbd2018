package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import java.util.List;
import javax.inject.Inject;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author agkan
 */
public class ListAccountBean {

    public ListAccountBean() {
    }
    
    @Inject
    private AccountController accountController;
    
    public List<Account> getAccounts() {
        return accountController.listAllAccounts();
    }
    
    public String editAccount(Account account) {
        accountController.getAccountToEdit(account);
        return "list-edit";
    }
    
    public void saveAccount(Account account) {
        accountController.saveAccount(account);
    }
}
