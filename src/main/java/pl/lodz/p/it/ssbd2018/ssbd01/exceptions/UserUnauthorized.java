/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions;


/**
 *
 * @author michal
 */
public class UserUnauthorized extends AppBaseException {
    
    public UserUnauthorized(String message) {
        super(message);
    }
    
    public UserUnauthorized(String message, Throwable cause) {
        super(message, cause);
    }  

    @Override
    public String getCode() {
        return super.getCode() + ".user_unauthorized"; 
    }
}