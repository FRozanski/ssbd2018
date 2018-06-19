/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop;

/**
 *
 * @author agkan
 */
public class ConstraintException extends ProductException {
    
    private String internalCode;
    
    public ConstraintException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstraintException(String message, String internalCode) {
        super(message);
        this.internalCode = internalCode;
    }    
    
    @Override
    public String getCode() {
        return super.getCode() + "." + internalCode; 
    }
}
