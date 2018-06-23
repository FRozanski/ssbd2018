/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.it.p.ssbd2018.ssbd01.exceptions.moz;

/**
 *
 * @author dlange
 */
public class OrderStatusNotFoundException extends MozBaseException {
    
    public OrderStatusNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderStatusNotFoundException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".orderStatus.notFound"; 
    }
}