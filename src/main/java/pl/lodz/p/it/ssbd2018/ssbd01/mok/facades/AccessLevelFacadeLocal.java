/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs definiujący dozwolone operacje na obiektach typu {@link AccessLevel}
 * @author fifi
 */
@Local
public interface AccessLevelFacadeLocal {
    
    /**
     * Wyciąga z bazy danych obiekt typu {@link AccessLevel} o podanym id
     * @param id identyfikator encji
     * @return Obiekt encji typu {@link AccessLevel}
     * @throws AppBaseException
     */
    AccessLevel find(Object id) throws AppBaseException;
    
    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link AccessLevel}
     * @return lista obiektów typu {@link AccessLevel}
     */
    List<AccessLevel> findAll();
    
    /**
     * Wyciąga z bazy danych obiekt typu {@link AccessLevel} o podanym poziomie dostępu
     * @param level poziom dostępu
     * @return Obiekt encji typu {@link AccessLevel}
     * @throws AppBaseException
     */
    AccessLevel findByLevel(String level) throws AppBaseException;
    
    /**
     * Wyciąga z bazy danych obiekty obienty typu {@link AccessLevel} z zadanego zakresu 
     * @param range dwuelementowa tablica liczb całkowitych zawierająca w sobie zakresy wyszukiwania.
     * Zakresy reprezentują id obiektów
     * @return lista obiektów typu {@link AccessLevel}
     */
    List<AccessLevel> findRange(int[] range);
    
    /**
     * Zlicza obieky typu {@link AccessLevel} w bazie danych
     * @return liczba całkowita będąca liczbą obiektów
     */
    int count();
    
}
