/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.shared_facades;

import javax.annotation.security.PermitAll;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Klasa abstrakcyjna realizująca dodawanie i aktualizowanie obiektów danego typu w bazie dnaych
 * @author fifi
 * @param <T>
 */
public abstract class AbstractFacadeCreateUpdate<T> extends AbstractFacadeBase<T> {
    
    public AbstractFacadeCreateUpdate(Class<T> entityClass) {
        super(entityClass);        
    }
    
    /**
     * Dodanie obiektu do bazy danych
     * @param entity obiekt encji danego typu
     * @throws AppBaseException
     */
    @PermitAll
    public void create(T entity) throws AppBaseException {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    /**
     * Edycja obiektu w bazie danych
     * @param entity obiekt encji danego typu
     * @throws AppBaseException
     */
    @PermitAll
    public void edit(T entity) throws AppBaseException {
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }
}
