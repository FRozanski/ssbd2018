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
 * @author Dominik
 */
@XmlRootElement
public class AccountDto implements Serializable{
    
    // nie ma tu wszystkich pól, klasa utworzona dla testu
    private String login;
    private long numberOfProducts;
    private long numberOfOrders;
    private long numberOfLogins;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String street;
    private String streetNumber;
    private String flatNumber;
    private String postalCode;
    private String city;
    private String country;
    private boolean confirm;
    
    public AccountDto() {
        
    }

    public AccountDto(String login, long numberOfProducts, long numberOfOrders, long numberOfLogins, String firstName, String lastName, String email, String phone, String street, String streetNumber, String flatNumber, String postalCode, String city, String country, boolean confirm) {
        this.login = login;
        this.numberOfProducts = numberOfProducts;
        this.numberOfOrders = numberOfOrders;
        this.numberOfLogins = numberOfLogins;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.streetNumber = streetNumber;
        this.flatNumber = flatNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.confirm = confirm;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(long numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public long getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(long numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public long getNumberOfLogins() {
        return numberOfLogins;
    }

    public void setNumberOfLogins(long numberOfLogins) {
        this.numberOfLogins = numberOfLogins;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }
    
    
    
    
    
    
}