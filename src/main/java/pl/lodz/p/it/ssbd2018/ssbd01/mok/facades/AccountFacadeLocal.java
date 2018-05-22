/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 *
 * @author fifi
 */
@Local
public interface AccountFacadeLocal {

    void create(Account account) throws AppBaseException;

    void edit(Account account);

    Account find(Object id);
    
    Account findByToken(String token) throws AppBaseException;
    
    Account findByLogin(String login);

    List<Account> findAll();

    List<Account> findRange(int[] range);

    int count();
    
}
