/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.managers;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author fifi
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class ProductManager implements ProductManagerLocal{

    @Override
    public void addProductByAccount(Account account, Product product) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteProductByAccount(Account account, Product product) {
        throw new NotImplementedException();
    }

    @Override
    public void updateProduct(Product product) {
        throw new NotImplementedException();
    }

    @Override
    public void setProductState(Product product, boolean active) {
        throw new NotImplementedException();
    }

    @Override
    public List<Product> getAllProducts() {
        throw new NotImplementedException();
    }

    @Override
    public List<Product> getAllProductsContainName(String name) {
        throw new NotImplementedException();
    }

    @Override
    public Product getProductFromAccountByName(Account account, String name) {
        throw new NotImplementedException();
    }

    @Override
    public void setProductCategory(Product product, Category category) {
        throw new NotImplementedException();
    }

    
}
