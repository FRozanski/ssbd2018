/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facades;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.it.p.ssbd2018.ssbd01.exceptions.moz.OrderNotFoundException;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Order1;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop.ProductNotFoundException;
import pl.lodz.p.it.ssbd2018.ssbd01.shared_facades.AbstractFacadeCreateUpdate;

/**
 * Klasa zapewnia możliwość operowania na obiektach encji typu {@link Order1} 
 * @author fifi
 */
@Stateless
public class Order1Facade extends AbstractFacadeCreateUpdate<Order1> implements Order1FacadeLocal {

    @PersistenceContext(unitName = "ssbd01mozDS")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Order1Facade() {
        super(Order1.class);
    }
    
    @Override
    @RolesAllowed("findOrderByBuyerLogin")
    public List<Order1> findByBuyerLogin(String login) throws AppBaseException {
        try {
            TypedQuery<Order1> typedQuery = em.createNamedQuery("Order1.findByBuyer", Order1.class).setParameter("login", login);
            if (typedQuery.getResultList().isEmpty()) {
                throw new NoResultException();
            }
            return typedQuery.getResultList();
        } catch (NoResultException ex) {
            throw new OrderNotFoundException("product_not_found_exception");
        }
    }
    
    @Override
    @RolesAllowed("findOrderBySellerLogin")
    public List<Order1> findBySellerLogin(String login) throws AppBaseException {
        try {
            TypedQuery<Order1> typedQuery = em.createNamedQuery("Order1.findBySeller", Order1.class).setParameter("login", login);
            if (typedQuery.getResultList().isEmpty()) {
                throw new NoResultException();
            }
            return typedQuery.getResultList();
        } catch (NoResultException ex) {
            throw new OrderNotFoundException("product_not_found_exception");
        }
    }
}
