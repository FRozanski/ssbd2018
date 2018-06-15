/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.it.p.ssbd2018.ssbd01.exceptions.moz;

import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 *
 * @author michal
 */
public class MozBaseException extends AppBaseException {
    
    public MozBaseException(String message) {
        super(message);
    }
    
    public MozBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return super.getCode() + ".moz"; 
    }
    
    
}