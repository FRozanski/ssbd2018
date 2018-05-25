/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccountAlevel;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccountAlevelException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccountAlevelExistsException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccountOptimisticException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.EmailNotUniqueException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.LoginNotUniqueException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.PhoneNotUniqueException;
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

    @Override
    public void remove(AccountAlevel entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public AccountAlevelFacade() {
        super(AccountAlevel.class);
    }

    @Override
    public AccountAlevel findByAccountAndAccessLevel(Long idAccount, Long idAccessLevel) {
        AccountAlevel accountAlevel = null;
        TypedQuery<AccountAlevel> typedQuery = em.createNamedQuery("AccountAlevel.findByAccountAccessLevel", AccountAlevel.class)
                .setParameter("id_account", idAccount)
                .setParameter("id_alevel", idAccessLevel);
        accountAlevel = typedQuery.getSingleResult();
        return accountAlevel;
    }

    @Override
    @PermitAll
    public void create(AccountAlevel accountAlevel) throws AppBaseException {
        try {
            super.create(accountAlevel);
        } catch (PersistenceException ex) {
            if (ex.getMessage().contains("account_alevel_unique")) {
                throw new AccountAlevelExistsException("access_level_exists_exception");
            }
        } catch (ConstraintViolationException ex) {
            throw new AccountAlevelException("constraint_violation");
        }

    }

}
