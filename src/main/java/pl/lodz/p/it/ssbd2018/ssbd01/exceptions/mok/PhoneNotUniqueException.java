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
public class PhoneNotUniqueException extends AccountException {
    
    public PhoneNotUniqueException(String message) {
        super(message);
    }
    
    public PhoneNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return super.getCode() + ".phone_not_unique"; 
    }
    
}