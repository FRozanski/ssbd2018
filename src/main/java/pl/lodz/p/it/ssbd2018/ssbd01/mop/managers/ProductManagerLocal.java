/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.managers;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Unit;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.NewProductDto;


/**
 *
 * @author fifi
 */
@Local
public interface ProductManagerLocal {
    
    void addProductByAccountLogin(NewProductDto newProduct, String login) throws AppBaseException;                                     
    
    void deleteProductByAccount(Account account, Product product);                                  
    
    void updateProduct(Product product);                                                            
    
    void setProductState(Product product, boolean active);                                          
            
    List<Product> getAllProducts();
    
    List<Product> getMyProducts(String login) throws AppBaseException;
    
    List<Product> getAllProductsContainName(String name);   
    
    List<Product> getAllActiveProducts() throws AppBaseException;
                
    Product getProductFromAccountByName(Account account, String name);                              
    
    void setProductCategory(Product product, Category category);                                    

    void addMyNewProduct(Product product) throws AppBaseException;

    Category getCategoryById(Long categoryId) throws AppBaseException;

    Unit getUnitById(Long unitId) throws AppBaseException;

    Account getMyAccountByLogin(String login) throws AppBaseException;
            
           
}
