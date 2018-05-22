/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.LoginNotUniqueException;
import pl.lodz.p.it.ssbd2018.ssbd01.shared_facades.AbstractFacadeCreateUpdate;

/**
 *
 * @author fifi
 * @author agkan
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
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
    @PermitAll
    public void create(Account account) throws AppBaseException {
        try {
            super.create(account);
        } catch (Exception e) {
            System.out.println("WYJATEK");
            throw new LoginNotUniqueException("ms");
        }
    }

    @Override
    public Account findByToken(String token) throws AppBaseException{
        Account account  = null;
        try {
            TypedQuery<Account> typedQuery = em.createNamedQuery("Account.findByToken", Account.class).setParameter("token", token);
            account = typedQuery.getSingleResult();            
            em.flush();
        } catch(OptimisticLockException oe) {

        } catch(PersistenceException pe) {

        }
        return account;
    }

    @Override
    public Account findByLogin(String login) {
        TypedQuery<Account> typedQuery = em.createNamedQuery("Account.findByLogin", Account.class).setParameter("login", login);
        return typedQuery.getSingleResult();
    }
    
}
