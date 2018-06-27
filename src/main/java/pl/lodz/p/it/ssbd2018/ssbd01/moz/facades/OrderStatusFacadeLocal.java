/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderStatus;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs definiujący dozwolone operacje na obiektach typu {@link OrderStatus}
 * @author fifi
 */
@Local
public interface OrderStatusFacadeLocal {
    
    /**
     * Pobiera z bazy danych obiekt typu {@link OrderStatus} o podanym id
     * @param id identyfikator encji
     * @return Obiekt encji typu {@link OrderStatus}
     * @throws AppBaseException
     */
    OrderStatus find(Object id) throws AppBaseException;
    
    /**
     * Pobiera z bazy danych wszystkie obienty typu {@link OrderStatus}
     * @return lista obiektów typu {@link OrderStatus}
     */
    List<OrderStatus> findAll();

    /**
     * Pobiera z bazy danych wszystkie obienty typu {@link OrderStatus} z zadanego zakresu 
     * @param range dwuelementowa tablica liczb całkowitych zawierająca w sobie zakresy wyszukiwania.
     * Zakresy reprezentują id obiektów
     * @return lista obiektów typu {@link OrderStatus}
     */
    List<OrderStatus> findRange(int[] range);
    
    /**
     * Zlicza obieky typu {@link OrderStatus} w bazie danych
     * @return liczba całkowita będąca liczbą obiektów
     */
    int count();
    
}
