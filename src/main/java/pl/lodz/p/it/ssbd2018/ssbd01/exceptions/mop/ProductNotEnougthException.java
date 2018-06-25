/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop;

/**
 *
 * @author piotrek
 */
public class ProductNotEnougthException extends ProductException {
    
    public ProductNotEnougthException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotEnougthException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".product_qty_not_enougth"; 
    }
}
