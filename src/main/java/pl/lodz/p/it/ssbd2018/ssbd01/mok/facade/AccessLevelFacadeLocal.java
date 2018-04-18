/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facade;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.entity.AccessLevel;

/**
 *
 * @author fifi
 */
@Local
public interface AccessLevelFacadeLocal {

    void create(AccessLevel accessLevel);

    void edit(AccessLevel accessLevel);

    AccessLevel find(Object id);

    List<AccessLevel> findAll();

    List<AccessLevel> findRange(int[] range);

    int count();
    
}
