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
public class OrderQtyFormatException extends OrderException {
    
    public OrderQtyFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderQtyFormatException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".qty_format_error"; 
    }
    
}