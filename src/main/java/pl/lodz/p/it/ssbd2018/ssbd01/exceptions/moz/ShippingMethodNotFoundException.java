/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz;

/**
 * Klasa wyjątku sygnalizująca nie znalezienie żądanej metody wysyłki
 * @author agkan
 */
public class ShippingMethodNotFoundException extends ShippingMethodException{
    
    public ShippingMethodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShippingMethodNotFoundException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".not_found"; 
    }
    
}
