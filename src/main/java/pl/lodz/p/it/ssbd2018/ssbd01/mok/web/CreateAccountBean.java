/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import javax.annotation.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author fifi
 */
@ManagedBean
@RequestScoped
public class CreateAccountBean {

    public CreateAccountBean() {
    }
    
    @Inject
    private AccountController accountController;
    
    public void registerAccount(Account account) {
        accountController.registerAccount(account);
    }
    
}
