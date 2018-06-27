/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs definiujący dozwolone operacje na obiektach typu {@link Category}
 * @author fifi
 */
@Local
public interface CategoryFacadeLocal {

    /**
     * Dodanie obiektu do bazy danych
     * @param category obiekt encji
     * @throws AppBaseException
     */
    void create(Category category) throws AppBaseException;

    /**
     * Edycja obiektu w bazie danych
     * @param category obiekt encji
     * @throws AppBaseException
     */
    void edit(Category category) throws AppBaseException;

    /**
     * Pobiera z bazy danych obiekt typu {@link Category} o podanym id
     * @param id identyfikator encji
     * @return Obiekt encji typu {@link Category}
     * @throws AppBaseException
     */
    Category find(Object id) throws AppBaseException;

    /**
     * Pobiera z bazy danych wszystkie obienty typu {@link Category}
     * @return lista obiektów typu {@link Category}
     */
    List<Category> findAll();

    /**
     * Pobiera z bazy danych obiekty obienty typu {@link Category} z zadanego zakresu 
     * @param range dwuelementowa tablica liczb całkowitych zawierająca w sobie zakresy wyszukiwania.
     * Zakresy reprezentują id obiektów
     * @return lista obiektów typu {@link Category}
     */
    List<Category> findRange(int[] range);

    /**
     * Zlicza obieky typu {@link Category} w bazie danych
     * @return liczba całkowita będąca liczbą obiektów
     */
    int count();

    /**
     * Pobiera z bazy danych obiekty typu {@link Category} oznaczone jako aktywne
     * @return lista obiektów typu {@link Category}
     * @throws AppBaseException główny wyjątek aplikacji
     */
    List<Category> findAllActive() throws AppBaseException;
    
}
