/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author piotrek
 */
@XmlRootElement
public class PassDto implements Serializable {
    
//    private String accountId;
    private String login;
    private String oldPass;
    private String newPassOne;
    private String newPassTwo;
    
    public PassDto(String login, String oldPass, String newPassOne, String newPassTwo) {
        this.login = login;
        this.oldPass = oldPass;
        this.newPassOne = newPassOne;
        this.newPassTwo = newPassTwo;
    }
    
    public PassDto() {
        
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }  
    
//    public String getAccountId() {
//        return accountId;
//    }

    public String getOldPass() {
        return oldPass;
    }

    public String getNewPassOne() {
        return newPassOne;
    }

    public String getNewPassTwo() {
        return newPassTwo;
    }

//    public void setAccountId(String accountId) {
//        this.accountId = accountId;
//    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public void setNewPassOne(String newPassOne) {
        this.newPassOne = newPassOne;
    }

    public void setNewPassTwo(String newPassTwo) {
        this.newPassTwo = newPassTwo;
    } 
}
