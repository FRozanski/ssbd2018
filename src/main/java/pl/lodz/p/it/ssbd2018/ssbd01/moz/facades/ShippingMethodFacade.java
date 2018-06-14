/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facades;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz.ShippingMethodException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz.ShippingMethodNameNotUniqueException;
import pl.lodz.p.it.ssbd2018.ssbd01.shared_facades.AbstractFacadeCreateUpdate;

/**
 * Klasa zapewnia możliwość operowania na obiektach encji typu {@link ShippingMethod} 
 * @author fifi
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ShippingMethodFacade extends AbstractFacadeCreateUpdate<ShippingMethod> implements ShippingMethodFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mozDS")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ShippingMethodFacade() {
        super(ShippingMethod.class);
    }
    
    @Override
    @RolesAllowed("addShippingMethod")
    public void create(ShippingMethod shippingMethod) throws AppBaseException {
        try {
            super.create(shippingMethod);
        } catch (PersistenceException ex) {
            if (ex.getMessage().contains("shipping_method_unique"))
                throw new ShippingMethodNameNotUniqueException("shipping_method_unique");            
        } catch (ConstraintViolationException ex) {
            throw new ShippingMethodException("constraint_violation");
        }                
    }
    
}
