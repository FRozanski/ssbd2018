/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz;

/**
 * Klasa wyjątku sygnalizująca, że wybrana metoda wysyłki nie wymaga dezaktywacji, bo jest już nieaktywna
 * @author agkan
 */
public class ShippingMethodWasDeactivated extends ShippingMethodException {

    public ShippingMethodWasDeactivated(String message, Throwable cause) {
        super(message, cause);
    }

    public ShippingMethodWasDeactivated(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return super.getCode() + ".was_deactivated";
    }
    
}
