/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.managers;

import java.util.List;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.facades.ProductFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.LoggerInterceptor;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author fifi
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
@Interceptors(LoggerInterceptor.class)
public class ProductManager implements ProductManagerLocal{
    
    private static final Logger loger = Logger.getLogger(AccountManager.class.getName());
    
    @EJB
    ProductFacadeLocal productFacade;
    
    @Override
    @RolesAllowed("getMyProducts")
    public List<Product> getMyProducts(String login) throws AppBaseException {
        return productFacade.findByOwnerLogin(login);
    }
    
    @Override
    @RolesAllowed("getAllProducts")
    public List<Product> getAllProducts() {
        return productFacade.findAll();
    }
    
    @Override
    @RolesAllowed("getAllActiveProducts")
    public List<Product> getAllActiveProducts() throws AppBaseException {
        return productFacade.findByActiveProductAndCategory();
    }
    
    
    @Override
    @RolesAllowed("addProductByAccount")
    public void addProductByAccount(Account account, Product product) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("deleteProductByAccount")
    public void deleteProductByAccount(Account account, Product product) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("updateProduct")
    public void updateProduct(Product product) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("setProductState")
    public void setProductState(Product product, boolean active) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("getAllProductsContainName")
    public List<Product> getAllProductsContainName(String name) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("getProductFromAccountByName")
    public Product getProductFromAccountByName(Account account, String name) {
        throw new NotImplementedException();
    }

    @Override
    @RolesAllowed("setProductCategory")
    public void setProductCategory(Product product, Category category) {
        throw new NotImplementedException();
    }
    
}
