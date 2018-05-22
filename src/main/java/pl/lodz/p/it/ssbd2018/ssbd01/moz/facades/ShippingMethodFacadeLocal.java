/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 *
 * @author fifi
 */
@Local
public interface ShippingMethodFacadeLocal {

    void create(ShippingMethod shippingMethod) throws AppBaseException;

    void edit(ShippingMethod shippingMethod);

    ShippingMethod find(Object id);

    List<ShippingMethod> findAll();

    List<ShippingMethod> findRange(int[] range);

    int count();
    
}
