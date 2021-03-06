/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.managers;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Unit;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.EditProductDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.NewProductDto;

/**
 * Interfejs manager do zarządzania produktami
 *
 * @author fifi
 */
@Local
public interface ProductManagerLocal {

    /**
     * Dodaje produkt przypisany do użytkownika o podanym loginie
     *
     * @param newProduct obiekt DTO (ang. Data Transfer Object) nowo tworzonego
     * produktu
     * @param login login użytkownika dodającego nowy produkt
     * @throws AppBaseException główny wyjątek aplikacji
     */
    void addProductByAccountLogin(NewProductDto newProduct, String login) throws AppBaseException;

    /**
     * Usunięcie produktu należącego do użytkownika o podanym loginie
     *
     * @param productId
     * @param login
     * @throws AppBaseException
     */
    void deleteProductByAccount(long productId, String login) throws AppBaseException;

    /**
     * Dezaktywuje produkt o podanym id
     *
     * @param productId
     * @param login
     * @throws AppBaseException główny wyjątek aplikacji
     */
    void deactiveProduct(long productId, String login) throws AppBaseException;

    /**
     * Aktywuje produkt o podanym id
     *
     * @param productId
     * @param login
     * @throws AppBaseException główny wyjątek aplikacji
     */
    void activeProduct(long productId, String login) throws AppBaseException;

    /**
     * Pobranie listy wszystkich produktów.
     *
     * @return type List lista produktów
     */
    List<Product> getAllProducts();

    /**
     * Pobranie listy produktów konkretnego użytkownika
     *
     * @param login login użytkownika
     * @throws AppBaseException główny wyjątek aplikacji
     * @return type List lista produktów
     */
    List<Product> getMyProducts(String login) throws AppBaseException;

    /**
     * Pobranie listy aktywnych produktów.
     *
     * @return type List lista produktów
     * @throws AppBaseException główny wyjątek aplikacji
     */
    List<Product> getAllActiveProducts() throws AppBaseException;

    /**
     * Wyszukuje obiekt encji jednostki miary po zadanym numerze identyfikującym
     *
     * @param unitId numer identyfikujący jednostkę miary
     * @return obiekt encji jednostki miary
     * @throws AppBaseException główny wyjątek aplikacji
     */
    Unit getUnitById(Long unitId) throws AppBaseException;

    /**
     * Wyszukuje obiekt encji produktów po zadanym id i ze zgodnym właścicielem
     *
     * @param login login właściciela produktu
     * @param id id produktu
     * @return obiekt encji - produkt
     * @throws AppBaseException główny wyjątek aplikacji
     */
    Product getProductByIdAndLogin(String login, Long id) throws AppBaseException;

    /**
     * Wyszukuje obiekt encji produktów po zadanym id, sprawdza właściciela i
     * dokonuje edycji zgodnie z DTO
     *
     * @param login login właściciela produktu
     * @param editProductDto dto produktu
     * @throws AppBaseException główny wyjątek aplikacji
     */
    void editProduct(EditProductDto editProductDto, String login) throws AppBaseException;

    /**
     * Pobiera z bazy danych wszystkie jednostki miar
     *
     * @return lista obiektów klasy {@link Unit}
     */
    List<Unit> getAllUnits();
}
