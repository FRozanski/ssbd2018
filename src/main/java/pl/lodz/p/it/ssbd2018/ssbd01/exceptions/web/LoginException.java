/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web;

/**
 *
 * @author michal
 */
public class LoginException extends WebBaseException {
    
    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".login_error"; 
    }
}