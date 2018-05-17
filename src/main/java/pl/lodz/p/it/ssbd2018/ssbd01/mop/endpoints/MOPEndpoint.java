/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.endpoints;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.endpoints.MOKEndpointLocal;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author fifi
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class MOPEndpoint implements MOKEndpointLocal{

    @Override
    public List<Account> getAllAccounts() {
        throw new NotImplementedException();
    }

    @Override
    public List<AccessLevel> getAllAccessLevels() {
        throw new NotImplementedException();
    }

    @Override
    public Account getAccountToEdit(Account account) {
        throw new NotImplementedException();
    }

    @Override
    public void saveAccountAfterEdit(Account account) {
        throw new NotImplementedException();
    }

    @Override
    public void registerAccount(Account account) {
        throw new NotImplementedException();
    }

    @Override
    public void confirmAccount(Account account) throws AppBaseException {
        throw new NotImplementedException();
    }

    @Override
    public void lockAccount(Account account) {
        throw new NotImplementedException();
    }

    @Override
    public void unlockAccount(Account account) {
        throw new NotImplementedException();
    }

    @Override
    public void addAccessLevelToAccount(AccessLevel accessLevel, Account account) {
        throw new NotImplementedException();
    }

    @Override
    public void dismissAccessLevelFromAccount(AccessLevel accessLevel, Account account) {
        throw new NotImplementedException();
    }

    @Override
    public String getVeryficationToken(Account account) {
        throw new NotImplementedException();
    }

    @Override
    public Account getAccountByLogin(String login) {
        throw new NotImplementedException();
    }

    @Override
    public Account getAccountByToken(String token) throws AppBaseException {
        throw new NotImplementedException();
    }

    @Override
    public void sendMailWithVeryficationLink(String mail, String veryficationLink) {
        throw new NotImplementedException();
    }
    
}
