/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop;

/**
 * @author dlange
 * Klasa wyjątku wykorzystywana w sytuacji zamiaru umieszczenia w bazie danych kategorii o nazwie, której długość jest niepoprawna.
 */
public class CategoryNameLengthException extends CategoryException {
    
    public CategoryNameLengthException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryNameLengthException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return super.getCode() + ".category_name_length"; 
    }
}