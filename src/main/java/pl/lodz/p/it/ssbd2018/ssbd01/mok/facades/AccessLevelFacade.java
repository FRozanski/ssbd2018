/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccessLevelExistsException;
import pl.lodz.p.it.ssbd2018.ssbd01.shared_facades.AbstractFacadeBase;

/**
 *
 * @author fifi
 */
@Stateless
public class AccessLevelFacade extends AbstractFacadeBase<AccessLevel> implements AccessLevelFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mokDS")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public AccessLevel find(Object id) throws AppBaseException {
        AccessLevel accessLevel = super.find(id);
        if (accessLevel == null) {
            throw new AccessLevelExistsException("access_level_not_found_exception");
        }
        return accessLevel;
    }

    public AccessLevelFacade() {
        super(AccessLevel.class);
    }

    @Override
    public AccessLevel findByLevel(String level) throws AppBaseException {
        try {
            AccessLevel accessLevel;
            TypedQuery<AccessLevel> typedQuery = em.createNamedQuery("AccessLevel.findByLevel", AccessLevel.class).setParameter("level", level);
            accessLevel = typedQuery.getSingleResult();
            return accessLevel;
        } catch (NoResultException ex) {
            throw new AccessLevelExistsException("access_level_not_found_exception");
        }
    }
    
}
