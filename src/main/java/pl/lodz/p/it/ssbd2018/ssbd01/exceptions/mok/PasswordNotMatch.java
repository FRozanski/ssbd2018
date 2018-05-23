/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok;

/**
 *
 * @author michal
 */
public class PasswordNotMatch extends AccountException {
    
    public PasswordNotMatch(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordNotMatch(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".password_not_match_error"; 
    }
    
}