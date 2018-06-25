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
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop.CategoryException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop.CategoryOptimisticException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop.CategoryUniqueNameException;
import pl.lodz.p.it.ssbd2018.ssbd01.shared_facades.AbstractFacadeCreateUpdate;

/**
 * Klasa zapewnia możliwość operowania na obiektach encji typu {@link Category} 
 * @author fifi
 * @author dlange
 */
@Stateless
public class CategoryFacade extends AbstractFacadeCreateUpdate<Category> implements CategoryFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mopDS")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryFacade() {
        super(Category.class);
    }
    
    @Override
    @RolesAllowed("getAllCategories")
    public List<Category> findAll() {
        return super.findAll();
    }    
    
    @Override
    @RolesAllowed("editCategory")
    public void edit(Category category) throws AppBaseException {
        try {
            super.edit(category);
        } catch (OptimisticLockException oe) {
            throw new CategoryOptimisticException("category_optimistic_exception");
        } catch (ConstraintViolationException ex) {
            throw new CategoryException("constraint_violation");
        }
    }


    /**
     * Metoda służąca do dodawania nowych kategorii produktów do bazy danych
     * @param category
     * @return status operacji
     * @throws AppBaseException
     * @throws CategoryUniqueNameException
     */
    @Override
    @RolesAllowed("addCategory")
    public void create(Category category) throws AppBaseException {
        try {
            super.create(category);
        } catch (PersistenceException pe) {
            if (pe.getMessage().contains("category_name_unique")) {
                throw new CategoryUniqueNameException("category_name_unique");
            }
        }
    }
}
