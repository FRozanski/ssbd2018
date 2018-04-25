package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.endpoints.MOKEndpointLocal;

/**
 *
 * @author agkan
 */
public class AccountController implements Serializable{
    
    @EJB
    private MOKEndpointLocal mOKEndpointLocal;
    
    private Account account;

    List<Account> pullAllAccounts() {
        return mOKEndpointLocal.pullAllAccounts();
    }

    void pullAccountToEdit(Account account) {
        this.account = mOKEndpointLocal.pullAccountToEdit(account);
    }

    void saveAccountAfterEdit(Account account) {
        mOKEndpointLocal.saveAccountAfterEdit(account);
    }
    
}
