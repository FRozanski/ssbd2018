/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.shared_facades;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 * Bazowa klasa abstrakcyjna dla wszystkich klas fasad
 * Klasa zapewnia wyszukiwanie i zliczanie obiektów danego typu 
 * @author fifi
 * @param <T>
 */
public abstract class AbstractFacadeBase<T> {

    private Class<T> entityClass;

    public AbstractFacadeBase(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();    
    
    /**
     * Pobiera z bazy danych obiekt o podanym id
     * @param id identyfikator encji
     * @return Obiekt encji danego typu
     * @throws AppBaseException
     */
    public T find(Object id) throws AppBaseException {
        return getEntityManager().find(entityClass, id);
    }
    
    /**
     * Pobiera z bazy danych wszystkie obienty danego typu
     * @return lista obiektów danego typu
     */
    @RolesAllowed("findAll")
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Pobiera z bazy danych obiekty danego typu z zadanego zakresu 
     * @param range dwuelementowa tablica liczb całkowitych zawierająca w sobie zakresy wyszukiwania.
     * Zakresy reprezentują id obiektów
     * @return lista obiektów danego typu
     */
    @RolesAllowed("findRange")
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Zlicza obieky w bazie danych danego typu
     * @return liczba całkowita będąca liczbą obiektów
     */
    @RolesAllowed("count")
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
