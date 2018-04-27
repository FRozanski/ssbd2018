package pl.lodz.p.it.ssbd2018.ssbd01.mok.endpoints;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccountAlevel;
import pl.lodz.p.it.ssbd2018.ssbd01.facades.AccountAlevelFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.facades.AccountFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.CloneUtils;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.HashUtils;

/**
 *
 * @author piotrek
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class MOKEndpoint implements MOKEndpointLocal {
    
    @EJB
    private AccountFacadeLocal accountFacade;
    
    @EJB
    private AccountAlevelFacadeLocal accountAlevelFacade;

    @Override
    @RolesAllowed("pullAllAccounts")
    public List<Account> pullAllAccounts() {
        return accountFacade.findAll();
    }

    @Override
    @RolesAllowed("pullAccountToEdit")
    public Account pullAccountToEdit(Account account) {
        Account tmpAccount = accountFacade.find(account.getId());        
        return (Account) CloneUtils.deepCloneThroughSerialization(tmpAccount);
    }

    @Override
    @RolesAllowed("saveAccountAfterEdit")
    public void saveAccountAfterEdit(Account account) {
        accountFacade.edit(account);
    }

    @Override
    @PermitAll
    public void registerAccount(Account account) {
        account.setActive(true);
        account.setConfirm(false);
        account.setPassword(HashUtils.sha256(account.getPassword()));
        accountFacade.create(account);        
    }

    @Override
    @RolesAllowed("lockAccount")
    public void lockAccount(Account account) {
        account.setActive(false);
        accountFacade.edit(account);
    }

    @Override
    @RolesAllowed("unlockAccount")
    public void unlockAccount(Account account) {
        account.setActive(true);
        accountFacade.edit(account);
    }

    @Override
    @RolesAllowed("addAccessLevelToAccount")
    public void addAccessLevelToAccount(AccessLevel accessLevel, Account account) {
        AccountAlevel accountAlevel = new AccountAlevel();
        accountAlevel.setIdAlevel(accessLevel);
        accountAlevel.setIdAccount(account);
        accountAlevelFacade.create(accountAlevel);
    }

    @Override
    @RolesAllowed("dismissAccessLevelFromAccount")
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
    @RolesAllowed("confirmAccount")
    public void confirmAccount(Account account) {
        account.setConfirm(true);
        accountFacade.edit(account);
    }
}
