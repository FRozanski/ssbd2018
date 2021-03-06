/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.managers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Unit;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop.ConstraintException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop.ProductPriceException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop.ProductQtyException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop.UserIsNotProductOwner;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.facades.AccountFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.managers.AccountManager;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicProductDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.EditProductDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.NewProductDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.facades.CategoryFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.facades.ProductFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.facades.UnitFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.mapper.NewProductMapper;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.mapper.ProductMapper;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.LoggerInterceptor;

/**
 * Klasa obsługująca zarządzanie obiektami typu {@link Product}
 * @author fifi
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
@Interceptors(LoggerInterceptor.class)
public class ProductManager implements ProductManagerLocal{
    
    private static final Logger loger = Logger.getLogger(AccountManager.class.getName());
    
    @EJB(beanName = "ProductMOP")
    ProductFacadeLocal productFacade;
    
    @EJB
    CategoryFacadeLocal categoryFace;
    
    @EJB(beanName = "UnitMOP")
    UnitFacadeLocal unitFacade;
    
    @EJB
    CategoryManagerLocal categoryManager;
    
    @EJB(beanName = "AccountMOP")
    AccountFacadeLocal accountFacade;
    
    @Override
    @RolesAllowed("getMyProducts")
    public List<Product> getMyProducts(String login) throws AppBaseException {
        return productFacade.findByOwnerLogin(login);
    }
    
    @Override
    @RolesAllowed("getProductByIdAndLogin")
    public Product getProductByIdAndLogin(String login, Long id) throws AppBaseException {
        Product product = productFacade.find(id);
        checkIfUserIsOwner(login, product);
        return product;
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
    @RolesAllowed("addProductByAccountLogin")
    public void addProductByAccountLogin(NewProductDto newProduct, String login) throws AppBaseException{
        this.validatePriceAndQtyOfProduct(newProduct);
        Product product = new Product();
        NewProductMapper.INSTANCE.newProductDtoToProduct(newProduct, product);
        Category category = categoryManager.getCategoryById(newProduct.getCategoryId());
        Unit unit = this.getUnitById(newProduct.getUnitId());
        Account owner = productFacade.findByLogin(login);
        product.setCategoryId(category);
        product.setOwnerId(owner);
        product.setUnitId(unit);
        product.setActive(true);
        this.validateConstraints(product);
        owner.setNumberOfProducts(owner.getNumberOfProducts() + 1);
        productFacade.create(product);
    }
    
    @Override
    @RolesAllowed("activeProduct")
    public void activeProduct(long productId, String login) throws AppBaseException {
        Product product = productFacade.find(productId);
        checkIfUserIsOwner(login, product);
        product.setActive(true);
        productFacade.edit(product);
    }
    
    @Override
    @RolesAllowed("deactiveProduct")
    public void deactiveProduct(long productId, String login) throws AppBaseException {
        Product product = productFacade.find(productId);
        checkIfUserIsOwner(login, product);
        product.setActive(false);
        productFacade.edit(product);
    }
    
    @Override
    @RolesAllowed("editProduct")
    public void editProduct(EditProductDto editProductDto, String login) throws AppBaseException {
        Product product = productFacade.find(editProductDto.getId());
        checkIfUserIsOwner(login, product);
        this.validatePriceAndQtyOfProduct(editProductDto);
        ProductMapper.INSTANCE.editableProductToEntity(editProductDto, product);
        this.validateConstraints(product);
        Unit unit = this.unitFacade.find(editProductDto.getIdUnit());
        product.setUnitId(unit);
        productFacade.edit(product);
    }

    @Override
    @RolesAllowed("deleteProductByAccount")
    public void deleteProductByAccount(long productId, String login) throws AppBaseException {
        Product product = productFacade.find(productId);
        checkIfUserIsOwner(login, product);
        Account account = accountFacade.findByLogin(login);
        long newProdNum = account.getNumberOfProducts() - 1;
        if (newProdNum <= 0) {
            newProdNum = 0;
        }
        productFacade.remove(product);
        account.setNumberOfProducts(newProdNum);
        accountFacade.edit(account);
    }

    @Override
    @RolesAllowed("getUnitById")
    public Unit getUnitById(Long unitId) throws AppBaseException {
        return unitFacade.find(unitId);
    }
    
    private void validateConstraints(Product product) throws AppBaseException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
        List<String> errors = new ErrorCodes().getAllErrors();

        if (constraintViolations.size() > 0) {
            for (int i = 0; i < errors.size(); i++) {
                for (ConstraintViolation<Product> temp : constraintViolations) {
                    if (errors.get(i).equals(temp.getMessage())) {
                        throw new ConstraintException("constraint_error", errors.get(i));
                    }
                }
            }
        }
    }
    
    private void checkIfUserIsOwner(String login, Product product) throws AppBaseException{
        if(!product.getOwnerId().getLogin().equals(login)) {
            throw new UserIsNotProductOwner("not_product_owner");
        }
    }

    @Override
    @RolesAllowed("getAllUnits")
    public List<Unit> getAllUnits() {
        return unitFacade.findAll();
    }

    private void validatePriceAndQtyOfProduct(BasicProductDto product) throws AppBaseException{
        try {
            BigDecimal price = new BigDecimal(product.getPrice());
        } catch(NumberFormatException e) {
            throw new ProductPriceException("product_price_exception");
        }
        try {
            BigDecimal qty = new BigDecimal(product.getQty());
        } catch(NumberFormatException e) {
            throw new ProductQtyException("product_qty_exception");
        }
    }
}
