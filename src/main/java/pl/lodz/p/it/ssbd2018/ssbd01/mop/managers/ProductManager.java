/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.managers;

import java.util.List;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Unit;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.NewProductDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.facades.CategoryFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.facades.ProductFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.facades.UnitFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.mapper.NewProductMapper;
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
    
    @EJB
    CategoryFacadeLocal categoryFace;
    
    @EJB
    UnitFacadeLocal unitFacade;
    
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
//    @RolesAllowed("addProductByAccount")
    public void addProductByAccountLogin(NewProductDto newProduct, String login) throws AppBaseException{
        Product product = new Product();
        NewProductMapper.INSTANCE.newProductDtoToProduct(newProduct, product);
        Category category = this.getCategoryById(newProduct.getCategoryId());
        Unit unit = this.getUnitById(newProduct.getUnitId());
        Account owner = productFacade.findByLogin(login);
        product.setCategoryId(category);
        product.setOwnerId(owner);
        product.setUnitId(unit);
        productFacade.create(product);
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

    @Override
    public void addMyNewProduct(Product product) throws AppBaseException {
        productFacade.create(product);
    }

    @Override
    public Category getCategoryById(Long categoryId) throws AppBaseException {
        return categoryFace.find(categoryId);
    }

    @Override
    public Unit getUnitById(Long unitId) throws AppBaseException {
        return unitFacade.find(unitId);
    }    
}
