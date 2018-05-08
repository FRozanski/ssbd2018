/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
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

    public AccessLevelFacade() {
        super(AccessLevel.class);
    }

    @Override
    public List<AccessLevel> findByLevel(String level) {
        Query query = getEntityManager().createNamedQuery("AccessLevel.findByLevel").setParameter("level", level);
        return query.getResultList();
    }
    
}
