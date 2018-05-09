/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.shared_facades.AbstractFacadeCreateUpdate;

/**
 *
 * @author fifi
 * @author agkan
 */
@Stateless
public class AccountFacade extends AbstractFacadeCreateUpdate<Account> implements AccountFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mokDS")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }

    @Override
    public Account findByToken(String token) {
        TypedQuery<Account> typedQuery = em.createNamedQuery("Account.findByToken", Account.class).setParameter("token", token);
        return typedQuery.getSingleResult();
    }

    @Override
    public Account findByLogin(String login) {
        TypedQuery<Account> typedQuery = em.createNamedQuery("Account.findByLogin", Account.class).setParameter("login", login);
        return typedQuery.getSingleResult();
    }
    
}
