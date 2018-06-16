/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop;

/**
 *
 * @author michal
 */
public class UserIsNotProductOwner extends ProductException {
    
    public UserIsNotProductOwner(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIsNotProductOwner(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".user_is_not_product_owner"; 
    }
}
