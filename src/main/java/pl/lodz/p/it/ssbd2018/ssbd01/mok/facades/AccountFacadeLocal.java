/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs definiujący dozwolone operacje na obiektach typu {@link Account}
 * @author fifi
 */
@Local
public interface AccountFacadeLocal {
    
    /**
     * Dodanie obiektu do bazy danych
     * @param account obiekt encji
     * @throws AppBaseException
     */
    void create(Account account) throws AppBaseException;

    /**
     * Edycja obiektu w bazie danych
     * @param account obiekt encji
     * @throws AppBaseException
     */
    void edit(Account account) throws AppBaseException;

    /**
     * Wyciąga z bazy danych obiekt typu {@link Account} o podanym id
     * @param id identyfikator encji
     * @return Obiekt encji typu {@link Account}
     * @throws AppBaseException
     */
    Account find(Object id) throws AppBaseException;
    
    /**
     * Wyciąga z bazy danych obiekt typu {@link Account} o podanym tokenie
     * @param token token
     * @return Obiekt encji typu {@link Account}
     * @throws AppBaseException
     */
    Account findByToken(String token) throws AppBaseException;
    
    /**
     * Wyciąga z bazy danych obiekt typu {@link Account} o podanym loginie
     * @param login login użytkownika
     * @return Obiekt encji typu {@link Account}
     * @throws AppBaseException
     */
    Account findByLogin(String login) throws AppBaseException;

    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link Account}
     * @return lista obiektów typu {@link Account}
     */
    List<Account> findAll();

    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link Account} z zadanego zakresu 
     * @param range dwuelementowa tablica liczb całkowitych zawierająca w sobie zakresy wyszukiwania.
     * Zakresy reprezentują id obiektów
     * @return lista obiektów typu {@link Account}
     */
    List<Account> findRange(int[] range);

    /**
     * Zlicza obieky typu {@link Account} w bazie danych
     * @return liczba całkowita będąca liczbą obiektów
     */
    int count();
    
}
