/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop;

/**
 *
 * @author agkan
 */
public class ProductPriceException extends ProductException {
    
    public ProductPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductPriceException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".price_error"; 
    }
    
}
