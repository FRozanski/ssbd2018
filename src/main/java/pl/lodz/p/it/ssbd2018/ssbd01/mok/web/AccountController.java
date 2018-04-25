package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.endpoints.MOKEndpointLocal;

/**
 *
 * @author agkan
 */
@SessionScoped
public class AccountController implements Serializable{
    
    @EJB
    private transient MOKEndpointLocal mokEndpointLocal;
    
    private Account account;

    List<Account> pullAllAccounts() {
        return mokEndpointLocal.pullAllAccounts();
    }

    void pullAccountToEdit(Account account) {
        this.account = mokEndpointLocal.pullAccountToEdit(account);
    }

    void saveAccountAfterEdit(Account account) {
        mokEndpointLocal.saveAccountAfterEdit(account);
    }
    
}
