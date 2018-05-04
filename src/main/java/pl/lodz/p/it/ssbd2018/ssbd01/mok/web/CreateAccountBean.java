/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import javax.annotation.ManagedBean;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author fifi
 */
@Named
@RequestScoped
public class CreateAccountBean {

    private String login;
    private String password;
    private String email;
    
    public CreateAccountBean() {
    }
    
    @Inject
    private AccountController accountController;
    
    public void registerAccount() {
        //temp
        Account newAccount = new Account(login, password, false, true, 0, 0, 0, 0);
        accountController.registerAccount(newAccount);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
