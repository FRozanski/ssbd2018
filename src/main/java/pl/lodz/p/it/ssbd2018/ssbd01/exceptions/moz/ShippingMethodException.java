/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz;

/**
 *
 * @author Filip
 */
public class ShippingMethodException extends MozBaseException {
    
    public ShippingMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShippingMethodException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".shipping_method"; 
    }
}
