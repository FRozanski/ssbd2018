/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Order1;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 *
 * @author fifi
 */
@Local
public interface Order1FacadeLocal {

    void create(Order1 order1) throws AppBaseException;

    void edit(Order1 order1) throws AppBaseException;

    Order1 find(Object id);

    List<Order1> findAll();

    List<Order1> findRange(int[] range);

    int count();
    
}
