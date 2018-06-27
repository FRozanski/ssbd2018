/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facades;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.it.p.ssbd2018.ssbd01.exceptions.moz.OrderStatusNotFoundException;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderStatus;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.shared_facades.AbstractFacadeBase;

/**
 * Klasa zapewnia możliwość operowania na obiektach encji typu {@link OrderStatus} 
 * @author fifi
 */
@Stateless
public class OrderStatusFacade extends AbstractFacadeBase<OrderStatus> implements OrderStatusFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mozDS")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderStatusFacade() {
        super(OrderStatus.class);
    }
    
    @Override
    @RolesAllowed("getOrderStatusById")
    public OrderStatus find(Object id) throws AppBaseException {
        OrderStatus orderStatus = super.find(id);
        if (orderStatus == null) {
            throw new OrderStatusNotFoundException("order_status_not_found");
        }
        return orderStatus;
    }
    
}
