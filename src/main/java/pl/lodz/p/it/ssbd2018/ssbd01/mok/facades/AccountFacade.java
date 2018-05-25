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
import javax.validation.ConstraintViolationException;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccountOptimisticException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.EmailNotUniqueException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.LoginNotUniqueException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.PhoneNotUniqueException;
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
        } catch (PersistenceException ex) {
            if (ex.getMessage().contains("login_unique")) {
                throw new LoginNotUniqueException("login_unique");
            } else if (ex.getMessage().contains("phone_unique")) {
                throw new PhoneNotUniqueException("phone_unique");
            } else if (ex.getMessage().contains("email_unique")) {
                throw new EmailNotUniqueException("email_unique");
            }
        } catch (ConstraintViolationException ex) {
            throw new AccountException("constraint_violation");
        }

    }

    @Override
    @PermitAll
    public void edit(Account account) throws AppBaseException {
        try {
            super.edit(account);
        } catch (OptimisticLockException oe) {
            throw new AccountOptimisticException("account_optimistic_error");
        } catch (PersistenceException ex) {
            if (ex.getMessage().contains("login_unique")) {
                throw new LoginNotUniqueException("login_unique");
            } else if (ex.getMessage().contains("phone_unique")) {
                throw new PhoneNotUniqueException("phone_unique");
            } else if (ex.getMessage().contains("email_unique")) {
                throw new EmailNotUniqueException("email_unique");
            }
        } catch (ConstraintViolationException ex) {
            throw new AccountException("constraint_violation");
        }

    }

    @Override
    public Account findByToken(String token) throws AppBaseException {
        Account account;
        TypedQuery<Account> typedQuery = em.createNamedQuery("Account.findByToken", Account.class).setParameter("token", token);
        account = typedQuery.getSingleResult();
        em.flush();
        return account;
    }

    @Override
    public Account findByLogin(String login) {
        TypedQuery<Account> typedQuery = em.createNamedQuery("Account.findByLogin", Account.class).setParameter("login", login);
        return typedQuery.getSingleResult();
    }

}
