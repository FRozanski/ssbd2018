/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz;

/**
 * Klasa wyjątku sygnalizująca naruszenie unikalności nazwy metody przesyłki
 * @author Filip
 */
public class ShippingMethodNameNotUniqueException extends ShippingMethodException {
    
    public ShippingMethodNameNotUniqueException(String message) {
        super(message);
    }
    
    public ShippingMethodNameNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return super.getCode() + ".name_not_unique"; 
    }
}
