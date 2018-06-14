/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderProducts;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs definiujący dozwolone operacje na obiektach typu {@link OrderProducts}
 * @author fifi
 */
@Local
public interface OrderProductsFacadeLocal {
    
    /**
     * Dodanie obiektu do bazy danych
     * @param orderProducts obiekt encji
     * @throws AppBaseException
     */
    void create(OrderProducts orderProducts) throws AppBaseException;
    
    /**
     * Edycja obiektu w bazie danych
     * @param orderProducts obiekt encji
     * @throws AppBaseException
     */
    void edit(OrderProducts orderProducts) throws AppBaseException;
    
    /**
     * Wyciąga z bazy danych obiekt typu {@link OrderProducts} o podanym id
     * @param id identyfikator encji
     * @return Obiekt encji typu {@link OrderProducts}
     * @throws AppBaseException
     */
    OrderProducts find(Object id) throws AppBaseException;
    
    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link OrderProducts}
     * @return lista obiektów typu {@link OrderProducts}
     */
    List<OrderProducts> findAll();
    
    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link OrderProducts} z zadanego zakresu 
     * @param range dwuelementowa tablica liczb całkowitych zawierająca w sobie zakresy wyszukiwania.
     * Zakresy reprezentują id obiektów
     * @return lista obiektów typu {@link OrderProducts}
     */
    List<OrderProducts> findRange(int[] range);
    
    /**
     * Zlicza obieky typu {@link OrderProducts} w bazie danych
     * @return liczba całkowita będąca liczbą obiektów
     */
    int count();
    
}
