/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Unit;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs definiujący dozwolone operacje na obiektach typu {@link Unit}
 * @author fifi
 */
@Local
public interface UnitFacadeLocal {

    /**
     * Wyciąga z bazy danych obiekt typu {@link Unit} o podanym id
     * @param id identyfikator encji
     * @return Obiekt encji typu {@link Unit}
     * @throws AppBaseException
     */
    Unit find(Object id) throws AppBaseException;

    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link Unit}
     * @return  lista obiektów typu {@link Unit}
     */
    List<Unit> findAll();

    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link Unit} z zadanego zakresu 
     * @param range dwuelementowa tablica liczb całkowitych zawierająca w sobie zakresy wyszukiwania.
     * Zakresy reprezentują id obiektów
     * @return lista obiektów typu {@link Unit}
     */
    List<Unit> findRange(int[] range);

    /**
     * Zlicza obieky typu {@link Unit} w bazie danych
     * @return liczba całkowita będąca liczbą obiektów
     */
    int count();    
}
