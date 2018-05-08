/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.VeryficationToken;
import pl.lodz.p.it.ssbd2018.ssbd01.shared_facades.AbstractFacadeCreateUpdate;

/**
 *
 * @author agkan
 */
@Stateless
public class VeryficationTokenFacade extends AbstractFacadeCreateUpdate<VeryficationToken> implements VeryficationTokenFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mokDS")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }    
    
    public VeryficationTokenFacade() {
        super(VeryficationToken.class);
    }

    @Override
    public void remove(VeryficationToken entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
}
