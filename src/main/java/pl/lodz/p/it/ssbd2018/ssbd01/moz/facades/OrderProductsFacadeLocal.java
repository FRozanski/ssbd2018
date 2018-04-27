/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderProducts;

/**
 *
 * @author fifi
 */
@Local
public interface OrderProductsFacadeLocal {

    void create(OrderProducts orderProducts);

    void edit(OrderProducts orderProducts);

    OrderProducts find(Object id);

    List<OrderProducts> findAll();

    List<OrderProducts> findRange(int[] range);

    int count();
    
}
