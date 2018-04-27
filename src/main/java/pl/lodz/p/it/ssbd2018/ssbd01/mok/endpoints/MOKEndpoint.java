package pl.lodz.p.it.ssbd2018.ssbd01.mok.endpoints;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.facades.AccountFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.CloneUtils;

/**
 *
 * @author piotrek
 */
@Stateful
public class MOKEndpoint implements MOKEndpointLocal {
    
    @EJB
    private AccountFacadeLocal accountFacade;

    @Override
    public List<Account> pullAllAccounts() {
        return accountFacade.findAll();
    }

    @Override
    public Account pullAccountToEdit(Account account) {
        Account tmpAccount = accountFacade.find(account.getId());
        
        return (Account) CloneUtils.deepCloneThroughSerialization(tmpAccount);
    }

    @Override
    public void saveAccountAfterEdit(Account account) {
        accountFacade.edit(account);
    }
}
