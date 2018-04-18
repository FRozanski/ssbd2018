/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facade;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.entity.OrderStatus;

/**
 *
 * @author fifi
 */
@Local
public interface OrderStatusFacadeLocal {

    OrderStatus find(Object id);

    List<OrderStatus> findAll();

    List<OrderStatus> findRange(int[] range);

    int count();
    
}
