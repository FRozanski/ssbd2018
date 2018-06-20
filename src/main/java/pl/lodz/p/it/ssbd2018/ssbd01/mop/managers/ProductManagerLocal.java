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
    
    /**
     * Dodaje produkt przypisany do użytkownika o podanym loginie
     * @param newProduct            obiekt DTO (ang. Data Transfer Object) nowo tworzonego produktu
     * @param login                 login użytkownika dodającego nowy produkt
     * @throws AppBaseException     główny wyjątek aplikacji
     */
    void addProductByAccountLogin(NewProductDto newProduct, String login) throws AppBaseException;                                     
    
    /**
     * Usunięcie produktu należącego do użytkownika o podanym loginie
     * @param productId
     * @param login
     * @throws AppBaseException
     */
    void deleteProductByAccount(long productId, String login) throws AppBaseException;                                  
    
    void updateProduct(Product product);                                                            
    
    void setProductState(Product product, boolean active);
    
    /**
     * Dezaktywuje produkt o podanym id
     * @param productId
     * @param login
     * @throws AppBaseException     głóqny wyjątek aplikacji
     */
    void deactiveProduct(long productId, String login) throws AppBaseException;
    
    /**
     * Aktywuje produkt o podanym id
     * @param productId
     * @param login
     * @throws AppBaseException     głóqny wyjątek aplikacji
     */
    void activeProduct(long productId, String login) throws AppBaseException;
            
    List<Product> getAllProducts();
    
    List<Product> getMyProducts(String login) throws AppBaseException;
    
    List<Product> getAllProductsContainName(String name);   
    
    List<Product> getAllActiveProducts() throws AppBaseException;
                
    Product getProductFromAccountByName(Account account, String name);       
    
    void setProductCategory(Product product, Category category);
    /**
     * Wyszukuje obiekt encji jednostki miary po zadanym numerze identyfikującym
     * @param unitId                numer identyfikujący jednostkę miary
     * @return                      obiekt encji jednostki miary
     * @throws AppBaseException     główny wyjątek aplikacji
     */
    Unit getUnitById(Long unitId) throws AppBaseException;   
    
    /**
     * Pobiera z bazy danych wszystkie jednostki miar
     * @return lista obiektów klasy {@link Unit}
     */
    List<Unit> getAllUnits();
}
