/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderShipping;
import pl.lodz.p.it.ssbd2018.ssbd01.shared_facades.AbstractFacadeCreateUpdate;

/**
 * Klasa zapewnia możliwość operowania na obiektach encji typu {@link OrderShipping} 
 * @author fifi
 */
@Stateless
public class OrderShippingFacade extends AbstractFacadeCreateUpdate<OrderShipping> implements OrderShippingFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mozDS")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderShippingFacade() {
        super(OrderShipping.class);
    }
    
}
