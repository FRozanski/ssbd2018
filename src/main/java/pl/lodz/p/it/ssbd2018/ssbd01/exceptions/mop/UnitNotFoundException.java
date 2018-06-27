/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop;

/**
 *
 * @author piotrek
 * @author michal
 */
public class UnitNotFoundException extends UnitException {
    
    public UnitNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnitNotFoundException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".not_found"; 
    }
}
