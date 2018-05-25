/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccountAlevel;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 *
 * @author fifi
 */
@Local
public interface AccountAlevelFacadeLocal {

    void create(AccountAlevel accountAlevel) throws AppBaseException;

    void remove(AccountAlevel accountAlevel);

    AccountAlevel find(Object id) throws AppBaseException;

    List<AccountAlevel> findAll();

    List<AccountAlevel> findRange(int[] range);

    int count();

    AccountAlevel findByAccountAndAccessLevel(Account account, AccessLevel accessLevel) throws AppBaseException;
    
}
