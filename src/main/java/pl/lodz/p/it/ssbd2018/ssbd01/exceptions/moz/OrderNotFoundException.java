/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz;

import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz.OrderException;

/**
 *
 * @author michal
 */
public class OrderNotFoundException extends OrderException {
    
    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotFoundException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".order_not_found"; 
    }
}
