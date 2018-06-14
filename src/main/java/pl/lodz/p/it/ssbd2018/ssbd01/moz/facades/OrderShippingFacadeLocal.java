/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderShipping;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs definiujący dozwolone operacje na obiektach typu {@link OrderShipping}
 * @author fifi
 */
@Local
public interface OrderShippingFacadeLocal {
    
    /**
     * Dodanie obiektu do bazy danych
     * @param orderShipping obiekt encji
     * @throws AppBaseException
     */
    void create(OrderShipping orderShipping) throws AppBaseException;
    
    /**
     * Edycja obiektu w bazie danych
     * @param orderShipping obiekt encji
     * @throws AppBaseException
     */
    void edit(OrderShipping orderShipping) throws AppBaseException;

    /**
     * Wyciąga z bazy danych obiekt typu {@link OrderShipping} o podanym id
     * @param id identyfikator encji
     * @return Obiekt encji typu {@link OrderShipping}
     * @throws AppBaseException
     */
    OrderShipping find(Object id) throws AppBaseException;
    
    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link OrderShipping}
     * @return lista obiektów typu {@link OrderShipping}
     */
    List<OrderShipping> findAll();
    
    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link OrderShipping} z zadanego zakresu 
     * @param range dwuelementowa tablica liczb całkowitych zawierająca w sobie zakresy wyszukiwania.
     * Zakresy reprezentują id obiektów
     * @return lista obiektów typu {@link OrderShipping}
     */
    List<OrderShipping> findRange(int[] range);
    
    /**
     * Zlicza obieky typu {@link OrderShipping} w bazie danych
     * @return liczba całkowita będąca liczbą obiektów
     */
    int count();
    
}
