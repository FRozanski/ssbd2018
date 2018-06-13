/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.dto;

/**
 *
 * @author michal
 */
public abstract class BasicNewAccountDto {
    
    private String firstPassword;
    private String secondPassword;
    
    /**
     * @return the firstPassword
     */
    public String getFirstPassword() {
        return firstPassword;
    }

    /**
     * @param firstPassword the firstPassword to set
     */
    public void setFirstPassword(String firstPassword) {
        this.firstPassword = firstPassword;
    }

    /**
     * @return the secondPassword
     */
    public String getSecondPassword() {
        return secondPassword;
    }

    /**
     * @param secondPassword the secondPassword to set
     */
    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }
    
}
