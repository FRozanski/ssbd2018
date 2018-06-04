/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ArchivalPassword;
import pl.lodz.p.it.ssbd2018.ssbd01.shared_facades.AbstractFacadeCreateUpdate;

/**
 *
 * @author michal
 */
@Stateless
public class ArchivalPasswordFacade extends AbstractFacadeCreateUpdate<ArchivalPassword> implements ArchivalPasswordFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mokDS")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArchivalPasswordFacade() {
        super(ArchivalPassword.class);
    }

    @Override
    @RolesAllowed("findArchivalPasswordByAccountId")
    public List<ArchivalPassword> findByAccountId(Long id) {
        TypedQuery<ArchivalPassword> typedQuery = em.createNamedQuery("ArchivalPassword.findByAccountId", ArchivalPassword.class).setParameter("id_account", id);
        return typedQuery.getResultList();
    }
}
