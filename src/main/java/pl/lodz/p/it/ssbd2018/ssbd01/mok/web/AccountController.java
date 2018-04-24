package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author agkan
 */
public class AccountController implements Serializable{
    
    @EJB
    private MOKEndpointLocal mOKEndpointLocal;
    
    private Account account;

    List<Account> listAllAccounts() {
        return mOKEndpointLocal.getAllAccounts; //TODO: czy tak metoda sie nazywa
    }

    void getAccountToEdit(Account account) {
        this.account = mOKEndpointLocal.getAccountToEdit(account); //TODO: czy tak metoda sie nazywa
    }

    void saveAccount(Account account) {
        mOKEndpointLocal.saveAllAccounts(); //TODO: czy tak metoda sie nazywa
    }
    
}
