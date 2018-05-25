/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.web.exceptions;

/**
 *
 * @author michal
 */
public class UserAlreadyLogoutException extends WebBaseException {
    
    public UserAlreadyLogoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyLogoutException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".user_already_logout_error"; 
    }
}
