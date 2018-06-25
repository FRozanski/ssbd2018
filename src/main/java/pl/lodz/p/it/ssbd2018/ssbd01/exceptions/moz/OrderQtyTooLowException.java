/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz;

/**
 *
 * @author piotrek
 */
public class OrderQtyTooLowException extends OrderException {
    
    public OrderQtyTooLowException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderQtyTooLowException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".qty_too_low"; 
    }
}