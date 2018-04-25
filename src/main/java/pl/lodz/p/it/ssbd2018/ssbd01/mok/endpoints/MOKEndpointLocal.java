package pl.lodz.p.it.ssbd2018.ssbd01.mok.endpoints;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author piotrek
 */
@Local
public interface MOKEndpointLocal {
    
    List<Account> pullAllAccounts();
    
    Account pullAccountToEdit(Account account);
    
    void saveAccountAfterEdit(Account account); 
}
