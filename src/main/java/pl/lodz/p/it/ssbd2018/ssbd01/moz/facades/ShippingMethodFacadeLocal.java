/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs definiujący dozwolone operacje na obiektach typu {@link ShippingMethod}
 * @author fifi
 */
@Local
public interface ShippingMethodFacadeLocal {
    
    /**
     * Dodanie obiektu do bazy danych
     * @param shippingMethod obiekt encji
     * @throws AppBaseException
     */
    void create(ShippingMethod shippingMethod) throws AppBaseException;
    
    /**
     * Edycja obiektu w bazie danych
     * @param shippingMethod obiekt encji
     * @throws AppBaseException
     */
    void edit(ShippingMethod shippingMethod) throws AppBaseException;
    
    /**
     * Wyciąga z bazy danych obiekt typu {@link ShippingMethod} o podanym id
     * @param id identyfikator encji
     * @return Obiekt encji typu {@link ShippingMethod}
     * @throws AppBaseException
     */
    ShippingMethod find(Object id) throws AppBaseException;
    
    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link ShippingMethod}
     * @return lista obiektów typu {@link ShippingMethod}
     */
    List<ShippingMethod> findAll();

    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link ShippingMethod} z zadanego zakresu 
     * @param range dwuelementowa tablica liczb całkowitych zawierająca w sobie zakresy wyszukiwania.
     * Zakresy reprezentują id obiektów
     * @return lista obiektów typu {@link ShippingMethod}
     */
    List<ShippingMethod> findRange(int[] range);
    
    /**
     * Zlicza obieky typu {@link ShippingMethod} w bazie danych
     * @return liczba całkowita będąca liczbą obiektów
     */
    int count();
    
}
