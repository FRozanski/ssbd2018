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

/**
 * Klasa obsługująca zarządzanie obiektami typu {@link ShippingMethod}
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
    @RolesAllowed("activateShippingMethod")
    public void activateShippingMethod(long shippingMethodId) throws AppBaseException {
        ShippingMethod shippingMethod = shippingFacade.find(shippingMethodId);
        shippingMethod.setActive(true);
        shippingFacade.edit(shippingMethod);
    }

    @Override
    @RolesAllowed("deactivateShippingMethod")
    public void deactivateShippingMethod(long shippingMethodId) throws AppBaseException {
        ShippingMethod shippingMethod = shippingFacade.find(shippingMethodId);
        shippingMethod.setActive(false);
        shippingFacade.edit(shippingMethod);
    }
    
}
