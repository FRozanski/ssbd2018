/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Order1;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs definiujący dozwolone operacje na obiektach typu {@link Order1}
 * @author fifi
 */
@Local
public interface Order1FacadeLocal {

    /**
     * Dodanie obiektu do bazy danych
     * @param order1 obiekt encji
     * @throws AppBaseException
     */
    void create(Order1 order1) throws AppBaseException;

    /**
     * Edycja obiektu w bazie danych
     * @param order1 obiekt encji
     * @throws AppBaseException
     */
    void edit(Order1 order1) throws AppBaseException;

    /**
     * Pobiera z bazy danych obiekt typu {@link Order1} o podanym id
     * @param id identyfikator encji
     * @return Obiekt encji typu {@link Order1}
     * @throws AppBaseException
     */
    Order1 find(Object id) throws AppBaseException;

    /**
     * Pobiera z bazy danych wszystkie obienty typu {@link Order1}
     * @return lista obiektów typu {@link Order1}
     */
    List<Order1> findAll();

    /**
     * Pobiera z bazy danych wszystkie obienty typu {@link Order1} z zadanego zakresu 
     * @param range dwuelementowa tablica liczb całkowitych zawierająca w sobie zakresy wyszukiwania.
     * Zakresy reprezentują id obiektów
     * @return lista obiektów typu {@link Order1}
     */
    List<Order1> findRange(int[] range);
    
    
    List<Order1> findBySellerLogin(String login) throws AppBaseException;
            
            
    List<Order1> findByBuyerLogin(String login) throws AppBaseException;

    /**
     * Zlicza obieky typu {@link Order1} w bazie danych
     * @return liczba całkowita będąca liczbą obiektów
     */
    int count();
    
}
