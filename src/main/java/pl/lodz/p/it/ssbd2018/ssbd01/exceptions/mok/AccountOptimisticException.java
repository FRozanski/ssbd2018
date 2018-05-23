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
public class AccountOptimisticException extends AccountException {
    
    public AccountOptimisticException(String message) {
        super(message);
    }
    
    public AccountOptimisticException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return super.getCode() + ".account_optimistic_error"; 
    }
    
}