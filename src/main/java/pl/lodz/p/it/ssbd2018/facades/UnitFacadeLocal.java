/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.entities.Unit;

/**
 *
 * @author fifi
 */
@Local
public interface UnitFacadeLocal {

    Unit find(Object id);

    List<Unit> findAll();

    List<Unit> findRange(int[] range);

    int count();    
}
