/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccountAlevel;
import pl.lodz.p.it.ssbd2018.ssbd01.shared_facades.AbstractFacadeCreateUpdate;

/**
 *
 * @author fifi
 */
@Stateless
public class AccountAlevelFacade extends AbstractFacadeCreateUpdate<AccountAlevel> implements AccountAlevelFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mokDS")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void remove(AccountAlevel entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
    
    public AccountAlevelFacade() {
        super(AccountAlevel.class);
    }
    
}