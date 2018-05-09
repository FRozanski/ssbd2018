/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import java.util.Calendar;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author agkan
 */
@Named
@RequestScoped
public class VeryficationTokenBean {
    
    private String token;
    private String confirmationMessage = "";    
    
    @Inject
    private AccountController accountController;
    
    public void confirmRegistration() {
        Account account = accountController.getAccountByToken(token);
        if (account == null) {
            confirmationMessage = "Invalid Token";
            return;
        }
        
        Calendar calendar = Calendar.getInstance();
        Date expiryDate = account.getExpiryDate();
        if (expiryDate != null) {
            long timeDiff = expiryDate.getTime() - calendar.getTime().getTime();
            if (timeDiff <= 0) {
                confirmationMessage = "Token Expired";
                return;
            }
        } else {
            confirmationMessage = "Invalid Token";
            return;
        }
        accountController.confirmAccount(account);
        confirmationMessage = "Activated account";
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
