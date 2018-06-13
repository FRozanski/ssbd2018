/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ArchivalPassword;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs definiujący dozwolone operacje na obiektach typu {@link ArchivalPassword}
 * @author michal
 */
@Local
public interface ArchivalPasswordFacadeLocal {
    
    /**
     * Dodanie obiektu do bazy danych
     * @param archivalPassword obiekt encji
     * @throws AppBaseException
     */
    void create(ArchivalPassword archivalPassword) throws AppBaseException;

    /**
     * Wyciąga z bazy danych obiekt typu {@link ArchivalPassword} o podanym id
     * @param id identyfikator encji
     * @return Obiekt encji typu {@link ArchivalPassword}
     * @throws AppBaseException
     */
    ArchivalPassword find(Object id) throws AppBaseException;

    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link ArchivalPassword}
     * @return lista obiektów typu {@link ArchivalPassword}
     */
    List<ArchivalPassword> findAll();

    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link ArchivalPassword} względem id konta
     * @param id identyfikator konta
     * @return lista obiektów typu {@link ArchivalPassword}
     */
    List<ArchivalPassword> findByAccountId(Long id);

    /**
     * Zlicza obieky typu {@link ArchivalPassword} w bazie danych
     * @return liczba całkowita będąca liczbą obiektów
     */
    int count();
    
}
