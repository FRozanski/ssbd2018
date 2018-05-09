/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author fifi
 */
@Named
@RequestScoped
public class CreateAccountBean {

    private String login;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    private String street;
    private String streetNumber;
    private String flatNumber;
    private String postalCode;
    private String city;
    private String country;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    private String veryficationLink = "";
    private String confirmationMessage = "";
    
    public CreateAccountBean() {
    }
    
    @Inject
    private AccountController accountController;
    
    public void registerAccount() {
        //temp
        Account newAccount = new Account();
        newAccount.setLogin(login);
        newAccount.setPassword(password);
        newAccount.setEmail(email);
        newAccount.setName(firstname);
        newAccount.setSurname(lastname);
        newAccount.setPhone(phone);
        newAccount.setStreet(street);
        newAccount.setStreetNumber(streetNumber);
        newAccount.setFlatNumber(flatNumber);
        newAccount.setPostalCode(postalCode);
        newAccount.setCity(city);
        newAccount.setCountry(country);
        accountController.registerAccount(newAccount);
    }
    
    public void createVeryficationLink() {
        Account account = accountController.getAccountByLogin(login);
        String token = account.getToken();
        veryficationLink = "/registrationConfirm.xhtml?token=" + token;
    }
    
    public void confirmRegistration() {
        Account account = accountController.getAccountByToken(token);
        if (account == null) {
            confirmationMessage = "Invalid Token";
            return;
        }
        
        Calendar calendar = Calendar.getInstance();
        Date expiryDate = account.getExpiryDate();
        if (expiryDate != null) {
            long timeDiff = expiryDate.getTime() - calendar.getTime().getTime();
            if (timeDiff <= 0) {
                confirmationMessage = "Token Expired";
                return;
            }
        } else {
            confirmationMessage = "Invalid Token";
            return;
        }
        accountController.confirmAccount(account);
        confirmationMessage = "Activated account";
    }

    public String getConfirmationMessage() {
        return confirmationMessage;
    } 

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVeryficationLink() {
        return veryficationLink;
    }   
}
