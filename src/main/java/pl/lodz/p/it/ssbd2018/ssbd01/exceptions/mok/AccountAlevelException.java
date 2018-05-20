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
public class AccountAlevelException extends MokBaseException {
    
    public AccountAlevelException(String message) {
        super(message);
    }
    
    public AccountAlevelException(String message, Throwable cause) {
        super(message, cause);
    }  

    @Override
    public String getCode() {
        return super.getCode() + ".account_alevel"; 
    }
}
