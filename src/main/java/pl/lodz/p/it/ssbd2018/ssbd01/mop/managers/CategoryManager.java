/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.managers;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.facades.CategoryFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.LoggerInterceptor;

/**
 * Klasa obsługująca zarządzanie obiektami typu {@link Category}
 * @author Filip
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
@Interceptors(LoggerInterceptor.class)
public class CategoryManager implements CategoryManagerLocal{
    
    @EJB
    CategoryFacadeLocal categoryFacade;
    
    @Override
    @RolesAllowed("getAllCategories")
    public List<Category> getAllCategories() {
        return categoryFacade.findAll();
    }
    
    @Override
    @RolesAllowed("getCategoryById")
    public Category getCategoryById(Long categoryId) throws AppBaseException {
        return categoryFacade.find(categoryId);
    }        
    
    @Override
    @RolesAllowed("activateCategory")
    public void activateCategory(long categoryId) throws AppBaseException {
        Category category = categoryFacade.find(categoryId);
        category.setActive(true);
        categoryFacade.edit(category);
    }
    
    @Override
    @RolesAllowed("deactivateCategory")
    public void deactivateCategory(long categoryId) throws AppBaseException {
        Category category = categoryFacade.find(categoryId);
        category.setActive(false);
        categoryFacade.edit(category);
    }

    /**
     * Metoda służąca do dodawania nowych kategorii produktów do bazy danych.
     * @param category 
     * @throws AppBaseException 
     */
    @Override
    @RolesAllowed("addCategory")
	public void addCategory(Category category) throws AppBaseException {
                category.setActive(true);
		categoryFacade.create(category);
	}
    
}
