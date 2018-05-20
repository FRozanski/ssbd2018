/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok;

/**
 *
 * @author agkan
 */
public class PasswordTooShortException extends AccountException {
    
    public PasswordTooShortException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordTooShortException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".password_too_short"; 
    }
    
}
