/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz;

/**
 * Klasa wyjątku sygnalizująca wystąpienie blokady optymistycznej podczas próby edycji metody wysyłki
 * @author agkan
 */
public class ShippingMethodOptimisticException extends ShippingMethodException {
    
    public ShippingMethodOptimisticException(String message) {
        super(message);
    }
    
    public ShippingMethodOptimisticException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return super.getCode() + ".optimistic_error"; 
    }
    
}
