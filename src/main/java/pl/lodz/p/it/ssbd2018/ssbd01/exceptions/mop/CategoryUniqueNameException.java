/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop;

/**
 * @author dlange
 * Klasa wyjątku wykorzystywana w sytuacji zamiaru umieszczenia w bazie danych kategorii o nieunikalnej nazwie.
 */
public class CategoryUniqueNameException extends CategoryException {
    
    public CategoryUniqueNameException(String message) {
        super(message);
    }

    public CategoryUniqueNameException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getCode() {
        return super.getCode() + ".category_unique_name"; 
    }
}