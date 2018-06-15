/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.managers;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.facades.ShippingMethodFacadeLocal;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author michal
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class ShippingMethodManager implements ShippingMethodManagerLocal {
    
    @EJB
    ShippingMethodFacadeLocal shippingFacade;
    
    @Override
    @RolesAllowed("getAllMethods")
    public List<ShippingMethod> getAllMethods() {
        return shippingFacade.findAll();
    }
    
    @Override
    @RolesAllowed("addShippingMethod")
    public void addShippingMethod(ShippingMethod shippingMethod) throws AppBaseException{
        shippingFacade.create(shippingMethod);
    }
    
    @Override
    @RolesAllowed("setShippingMethodActive")
    public void setShippingMethodActive(ShippingMethod shippingMethod, boolean active) {
        throw new NotImplementedException();
    }
    
}
