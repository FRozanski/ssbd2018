/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facade;

import javax.persistence.EntityManager;

/**
 *
 * @author fifi
 */
public abstract class AbstractFacadeCreateUpdate<T> extends AbstractFacadeBase<T>{
    
    public AbstractFacadeCreateUpdate(Class<T> entityClass) {
        super(entityClass);
    }

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
}
