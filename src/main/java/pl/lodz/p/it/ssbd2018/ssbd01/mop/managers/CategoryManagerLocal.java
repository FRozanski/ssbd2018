/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.managers;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Interfejs manager do zarządzania kategoriami produktów
 * @author Filip
 */
@Local
public interface CategoryManagerLocal {
    
    /**
     * Pobiera z bazy wszystkie kategorie produktów
     * @return lista obiektów typu {@link Category}
     */
    List<Category> getAllCategories();

    /**
     * Wyszukuje obiekt encji kategorii produktu po zadanym numerze identyfikującym
     * @param categoryId            numer identyfikujący kategorię produktu
     * @return                      obiekt encji kategorii produktu
     * @throws AppBaseException     główny wyjątek aplikacji
     */
    Category getCategoryById(Long categoryId) throws AppBaseException;
    
    /**
     * Aktywuje obiekt encji kategorii produktu po zadanym numerze identyfikującym
     * @param categoryId numer identyfikujący kategorię produktu
     * @throws AppBaseException główny wyjątek aplikacji
     */
    public void activateCategory(long categoryId) throws AppBaseException;
    
    /**
     * Deaktywuje obiekt encji kategorii produktu po zadanym numerze identyfikującym
     * @param categoryId numer identyfikujący kategorię produktu
     * @throws AppBaseException główny wyjątek aplikacji
     */
    public void deactivateCategory(long categoryId) throws AppBaseException;

    /**
     * Tworzy nowy obiekt kategorii produktów i umieszcza go w bazie danych
     * @param category typu {@Link Category}
     * @throws AppBaseException główny wyjątek aplikacji
     */
    public void addCategory(Category category) throws AppBaseException ;

    /**
     * Pobiera z bazy aktywne kategorie produktów
     * @return lista obiektów typu {@link Category}
     * @throws AppBaseException główny wyjątek aplikacji
     */
    List<Category> getActiveCategories() throws AppBaseException;
}
