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
public class PasswordOrUsernameException extends WebBaseException {
    
    public PasswordOrUsernameException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordOrUsernameException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".null_password_or_username_error"; 
    }
}
