/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 *
 * @author agkan
 */
@Named
@RequestScoped
public class ConfirmAccountBean {
    
    private String token;
    private String confirmationMessage = "";   
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("AccountMessages");
    
    @Inject
    private AccountController accountController;
    
    public void confirmAccount() {
        try {
            Account account = accountController.getAccountByToken(token);
            accountController.confirmAccount(account);
            confirmationMessage = BUNDLE.getString("ActivatedAccount");
        } catch(AppBaseException ex) {
            //Logger.getLogger(ListAccountBean.class.getName()).log(Level.SEVERE, null, ex);
            confirmationMessage = BUNDLE.getString("InvalidToken");
        }
    }

    public String getConfirmationMessage() {
        return confirmationMessage;
    } 
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
