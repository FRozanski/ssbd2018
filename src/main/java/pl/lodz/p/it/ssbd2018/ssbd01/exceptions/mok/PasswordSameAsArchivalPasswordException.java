/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok;

/**
 *
 * @author rozfi
 */
public class PasswordSameAsArchivalPasswordException extends AccountException {
    
    public PasswordSameAsArchivalPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public PasswordSameAsArchivalPasswordException(String message) {
        super(message);
    }    

    @Override
    public String getCode() {
        return super.getCode() + ".password_same_as_archival_error"; 
    }
}