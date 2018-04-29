package pl.lodz.p.it.ssbd2018.ssbd01.mok.endpoints;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccountAlevel;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.facades.AccountAlevelFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.facades.AccountFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.CloneUtils;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.HashUtils;

/**
 *
 * @author piotrek
 */
@Stateful
public class MOKEndpoint implements MOKEndpointLocal {
    
    @EJB
    private AccountFacadeLocal accountFacade;
    
    @EJB
    private AccountAlevelFacadeLocal accountAlevelFacade;

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

    @Override
    public void registerAccount(Account account) {
        account.setActive(true);
        account.setConfirm(false);
        account.setPassword(HashUtils.sha256(account.getPassword()));
        accountFacade.create(account);        
    }

    @Override
    public void lockAccount(Account account) {
        account.setActive(false);
        accountFacade.edit(account);
    }

    @Override
    public void unlockAccount(Account account) {
        account.setActive(true);
        accountFacade.edit(account);
    }

    @Override
    public void addAccessLevelToAccount(AccessLevel accessLevel, Account account) {
        AccountAlevel accountAlevel = new AccountAlevel();
        accountAlevel.setIdAlevel(accessLevel);
        accountAlevel.setIdAccount(account);
        accountAlevelFacade.create(accountAlevel);
    }

    @Override
    public void dismissAccessLevelFromAccount(AccessLevel accessLevel, Account account) {
        List<AccountAlevel> accountAlevels = accountAlevelFacade.findAll();
        AccountAlevel accountAlevel = null;
        for (AccountAlevel aal : accountAlevels) {
            if (aal.getIdAccount().equals(account) 
                    && aal.getIdAlevel().equals(accessLevel)) {
                accountAlevel = aal;
                break;
            }
        }
        if (accountAlevel != null)
            accountAlevelFacade.remove(accountAlevel);
    }

    @Override
    public void confirmAccount(Account account) {
        account.setConfirm(true);
        accountFacade.edit(account);
    }
}
