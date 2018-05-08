/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;

/**
 *
 * @author fifi
 */
@Local
public interface AccessLevelFacadeLocal {

    AccessLevel find(Object id);

    List<AccessLevel> findAll();
    
    List<AccessLevel> findByLevel(String level);

    List<AccessLevel> findRange(int[] range);

    int count();
    
}
