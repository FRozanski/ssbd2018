/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs definiujący dozwolone operacje na obiektach typu {@link Product}
 * @author fifi
 */
@Local
public interface ProductFacadeLocal {

    /**
     * Dodanie obiektu do bazy danych
     * @param product  obiekt encji
     * @throws AppBaseException
     */
    void create(Product product) throws AppBaseException;

    /**
     * Edycja obiektu w bazie danych
     * @param product obiekt encji
     * @throws AppBaseException
     */
    void edit(Product product) throws AppBaseException;

    /**
     * Wyciąga z bazy danych obiekt typu {@link Product} o podanym id
     * @param id identyfikator encji
     * @return Obiekt encji typu {@link Product}
     * @throws AppBaseException
     */
    Product find(Object id) throws AppBaseException;

    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link Product}
     * @return lista obiektów typu {@link Product}
     */
    List<Product> findAll();

    /**
     * Wyciąga z bazy danych obienty typu {@link Product} z zadanego zakresu 
     * @param range dwuelementowa tablica liczb całkowitych zawierająca w sobie zakresy wyszukiwania.
     * @return lista obiektów typu {@link Product}
     */
    List<Product> findRange(int[] range);

    /**
     * Zlicza obieky typu {@link Product} w bazie danych
     * @return liczba całkowita będąca liczbą obiektów
     */
    int count();
    
}
