package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.endpoints.MOKEndpointLocal;

/**
 *
 * @author agkan
 */
@ManagedBean
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
    
    void lockAccount(Account account) {
        mokEndpointLocal.lockAccount(account);
    }
    
    void unlockAccount(Account account) {
        mokEndpointLocal.unlockAccount(account);
    }
    
    void confirmAccount(Account account) {
        mokEndpointLocal.confirmAccount(account);
    }
    
    void registerAccount(Account account) {
        mokEndpointLocal.registerAccount(account);
    }
}
