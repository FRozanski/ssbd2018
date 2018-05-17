/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.endpoints;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Unit;


/**
 *
 * @author fifi
 */
@Local
public interface MOPEndpointLocal {
    
    List<Product> getAllProducts();
    
    List<Product> getAllProductsByAccount(Account account);
    
    List<Product> getAllProductsByCategory(Category category);
    
    List<Product> getAllProductsFromAccountByCategory(Account account, Category category);
    
    List<Product> getAllProductsByUnit(Unit unit);
    
    List<Product> getAllProductsFromAccountByUnit(Account account, Unit unit);
    
    List<Product> getAllProductsByActive(boolean active);
    
    List<Product> getAllProductsFromAccountByActive(Account account, boolean active);
    
    Product getProductToEdit(Product product);
    
    void updateProduct(Product product);
    
    void setProductState(Product product, boolean state);   
    
    void addProductByAccount(Account account, Product product);
    
    void deleteProductByAccount(Account account, Product product);
    
    void setCategoryToProduct(Product product, Category category);
       
}
