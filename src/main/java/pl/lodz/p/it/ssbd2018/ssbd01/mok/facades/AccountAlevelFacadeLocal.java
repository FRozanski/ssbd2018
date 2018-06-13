/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccountAlevel;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs definiujący dozwolone operacje na obiektach typu {@link AccountAlevel}
 * @author fifi
 */
@Local
public interface AccountAlevelFacadeLocal {
    
    /**
     * Dodanie obiektu do bazy danych
     * @param accountAlevel obiekt encji
     * @throws AppBaseException
     */
    void create(AccountAlevel accountAlevel) throws AppBaseException;
    
    /**
     * Usunięcie obiektu z bazy danych
     * @param accountAlevel obiekt encji
     */
    void remove(AccountAlevel accountAlevel);
    
    /**
     * Wyciąga z bazy danych obiekt typu {@link AccountAlevel} o podanym id
     * @param id identyfikator encji
     * @return Obiekt encji danego typu
     * @throws AppBaseException
     */
    AccountAlevel find(Object id) throws AppBaseException;
    
    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link AccountAlevel}
     * @return lista obiektów typu {@link AccountAlevel}
     */
    List<AccountAlevel> findAll();

    /**
     * Wyciąga z bazy danych wszystkie obienty typu {@link AccountAlevel} z zadanego zakresu 
     * @param range dwuelementowa tablica liczb całkowitych zawierająca w sobie zakresy wyszukiwania.
     * Zakresy reprezentują id obiektów
     * @return lista obiektów typu {@link AccountAlevel}
     */
    List<AccountAlevel> findRange(int[] range);
    
    /**
     * Zlicza obieky typu {@link AccountAlevel} w bazie danych
     * @return liczba całkowita będąca liczbę obiektów
     */
    int count();
    
    /**
     * Wyciąga z bazy danych obiekt typu {@link AccountAlevel} na podstawie konta i przypisanych mu poziomów dostępu
     * @param account konto
     * @param accessLevel poziom dostępu
     * @return Obiekt typu {@link AccountAlevel}
     * @throws AppBaseException
     */
    AccountAlevel findByAccountAndAccessLevel(Account account, AccessLevel accessLevel) throws AppBaseException;
    
}
