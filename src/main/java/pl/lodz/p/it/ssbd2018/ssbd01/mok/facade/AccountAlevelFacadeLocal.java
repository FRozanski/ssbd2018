/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facade;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.entity.AccountAlevel;

/**
 *
 * @author fifi
 */
@Local
public interface AccountAlevelFacadeLocal {

    void create(AccountAlevel accountAlevel);

    void edit(AccountAlevel accountAlevel);

    void remove(AccountAlevel accountAlevel);

    AccountAlevel find(Object id);

    List<AccountAlevel> findAll();

    List<AccountAlevel> findRange(int[] range);

    int count();
    
}
