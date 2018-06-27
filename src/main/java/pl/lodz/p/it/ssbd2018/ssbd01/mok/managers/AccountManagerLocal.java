package pl.lodz.p.it.ssbd2018.ssbd01.mok.managers;

import java.util.List;
import javax.ejb.Local;
import javax.servlet.ServletContext;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ArchivalPassword;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs definiujący dozwolone operacje na obiektach typu {@link Account}
 * 
 * @author piotrek
 * @author michalmalec
 */
@Local
public interface AccountManagerLocal {
    
    
    /**
     * Metoda zwracająca listę kont pobranych z bazy danych.
     * @return lista obiektów typu {@link Account}
     */
    List<Account> getAllAccounts();
    
    /**
     * Metoda zwracająca listę poziomów dostępu pobranych z bazy danych.
     * @return lista obiektów typu {@link AccessLevel}
     */
    List<AccessLevel> getAllAccessLevels();
    
    /**
     * Meta zwraca wszystkie archiwalne hasła dla danego konta.
     * @param accountId numer id konta
     * @return lista obiektów typu {@link ArchivalPassword}
     */
    List<ArchivalPassword> getAllArchivalPasswordsByAccount(long accountId);
    
//    Account getAccountToEdit(Account account) throws AppBaseException;
    
    /**
     * Metoda pozwalająca na zapisanie zmian dla danego konta po edycji do bazy danych.
     * @param account obiekt typu {@link Account}
     * @throws AppBaseException
     */
    void saveAccountAfterEdit(Account account) throws AppBaseException; 
    
    /**
     * Metoda pozwalająca zarejestrować użytkownika umieszczając jego dane w bazie danych.
     * @param account obiekt typu {@link Account}
     * @param servletContext typu {@link ServletContext}
     * @throws AppBaseException
     */
    void registerAccount(Account account, ServletContext servletContext) throws AppBaseException;
    
    /**
     * Metoda pozwalająca aktywować konto użytkownika. Wysyła wiadomość e-mail informując o zdarzeniu.
     * @param accountId numer id konta
     * @throws AppBaseException
     */
    void confirmAccount(long accountId) throws AppBaseException;
    
    /**
     * Metoda pozwalająca zablokować konto użytkownika. Wysyła wiadomość e-mail informując o zdarzeniu.
     * @param accountId numer id konta
     * @throws AppBaseException
     */
    void lockAccount(long accountId) throws AppBaseException;
    
    /**
     * Metoda pozwalająca aktywować konto poprzez podany token. Wysyła wiadomość e-mail informując o zdarzeniu.
     * @param token typu {@link String}
     * @throws AppBaseException 
     */
    void confirmAccountByToken(String token) throws AppBaseException;
       
    /**
     * Metoda pozwalająca odblokować konto.
     * @param accountId numer id konta
     * @throws AppBaseException
     */
    void unlockAccount(long accountId) throws AppBaseException;
    
    /**
     * Metoda aktualizująca dane oraz numer IP ostatniego logowania.
     * @param login konta typu {@link String}
     * @param ip numer IP typu {@link String}
     * @throws AppBaseException 
     */
    public void updateLoginDateAndIp(String login, String ip) throws AppBaseException;
    
    /**
     * Metoda pozwalająca na udzielenie poziomów dostępu dla danego konta zdefiniowanych w liście podawanej do metody jako argument.
     * @param account obiekt typu {@link Account}
     * @param accessLevel lista poziomów dostępu typu {@link String}
     * @throws AppBaseException 
     */
    public void alterAccountAccessLevel(Account account, List<String> accessLevel) throws AppBaseException;
    
    /**
     * Metoda zwracająca obiekt typu {@link Account} po podanym loginie konta.
     * @param login konta typu {@link String}
     * @throws AppBaseException
     * @return obiekt typu {@link Account}
     */
    public Account getMyAccountByLogin(String login) throws AppBaseException;
    
    /**
     * Metoda zwracająca obiekt typu {@link Account} po podanym numerze id konta.
     * @param id konta
     * @throws AppBaseException
     * @return obiekt typu {@link Account}
     */
    public Account getAccountById(long id) throws AppBaseException;
    
    /**
     * Metoda pozwalająca właścicielowi konta na zmianę hasła. 
     * @param account obiekt typu {@link Account}
     * @throws AppBaseException 
     */
    public void changeMyPassword(Account account) throws AppBaseException;
    
    /**
     * Metoda pozwalająca administratorowi na zmianę hasła danego konta.
     * @param account obiekt typu {@link Account}
     * @throws AppBaseException
     */
    public void changeOthersPassword(Account account) throws AppBaseException;

    /**
     * Metoda pozwalająca włąścicielowi konta na zapisanie zmian do bazy danych.
     * @param myAccount obiekt typu {@link Account}
     * @throws AppBaseException
     */
    public void saveMyAccountAfterEdit(Account myAccount) throws AppBaseException;
}
