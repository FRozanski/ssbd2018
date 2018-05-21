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
public class TokenUsedException extends VeryficationTokenException {
    
    public TokenUsedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenUsedException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".token_already_used"; 
    }
    
}
