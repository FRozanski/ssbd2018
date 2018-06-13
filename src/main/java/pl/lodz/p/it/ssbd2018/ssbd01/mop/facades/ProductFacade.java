/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.facades;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop.ProductNotFoundException;
import pl.lodz.p.it.ssbd2018.ssbd01.shared_facades.AbstractFacadeCreateUpdate;

/**
 *
 * @author fifi
 * @author michalmalec
 */
@Stateless
public class ProductFacade extends AbstractFacadeCreateUpdate<Product> implements ProductFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mopDS")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductFacade() {
        super(Product.class);
    }
    
    @Override
    @RolesAllowed("findProductByOwnerLogin")
    public List<Product> findByOwnerLogin(String login) throws AppBaseException {
        try {
            TypedQuery<Product> typedQuery = em.createNamedQuery("Product.findByOwnerLogin", Product.class).setParameter("login", login);
            if (typedQuery.getResultList().isEmpty()) {
                throw new NoResultException();
            }
            return typedQuery.getResultList();
        } catch (NoResultException ex) {
            throw new ProductNotFoundException("product_not_found_exception");
        }
    }
}
