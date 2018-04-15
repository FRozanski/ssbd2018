/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facade;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.entity.UserData;

/**
 *
 * @author fifi
 */
@Local
public interface UserDataFacadeLocal {

    void create(UserData userData);

    void edit(UserData userData);

    void remove(UserData userData);

    UserData find(Object id);

    List<UserData> findAll();

    List<UserData> findRange(int[] range);

    int count();
    
}
