/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.entity.ShippingMethod;

/**
 *
 * @author fifi
 */
@Stateless
public class ShippingMethodFacade extends AbstractFacade<ShippingMethod> implements ShippingMethodFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mozPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ShippingMethodFacade() {
        super(ShippingMethod.class);
    }
    
}
