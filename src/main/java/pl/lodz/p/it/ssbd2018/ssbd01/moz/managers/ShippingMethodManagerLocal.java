/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.managers;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs manager do zarządzania metodami przesyłki
 * @author michal
 */
@Local
public interface ShippingMethodManagerLocal {
    
    List<ShippingMethod> getAllMethods();
    
    /**
     * Dodawanie nowych metod wysyłki
     * @param shippingMethod metoda wysyłki
     * @throws AppBaseException
     */
    void addShippingMethod(ShippingMethod shippingMethod) throws AppBaseException;
    
    void setShippingMethodActive(ShippingMethod shippingMethod, boolean active);
}
