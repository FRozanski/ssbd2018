/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2018.entities.OrderShipping;

/**
 *
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
