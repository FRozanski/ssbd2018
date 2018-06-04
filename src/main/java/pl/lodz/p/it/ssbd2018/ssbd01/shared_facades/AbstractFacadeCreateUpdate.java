/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.shared_facades;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 *
 * @author fifi
 */
public abstract class AbstractFacadeCreateUpdate<T> extends AbstractFacadeBase<T> {
    
    public AbstractFacadeCreateUpdate(Class<T> entityClass) {
        super(entityClass);        
    }
    
    @PermitAll
    public void create(T entity) throws AppBaseException {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    @RolesAllowed("edit")
    public void edit(T entity) throws AppBaseException {
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }
}
