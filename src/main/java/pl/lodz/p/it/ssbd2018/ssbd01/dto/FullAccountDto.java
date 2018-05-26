/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author michal
 */
public class FullAccountDto {
    
    private long id;
    private String login;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String street;
    private String streetNumber;
    private String flatNumber;
    private String postalCode;
    private String city;
    private String country;
    private long version;
    private long numberOfProducts;
    private long numberOfOrders;
    private long numberOfLogins;
    private boolean confirm;
    private boolean active;
    private String lastLoginDate;
    private String lastLoginIp;
    private List<String> accessLevels = new ArrayList<>();

    /**
     * @return the numberOfProducts
     */
    public long getNumberOfProducts() {
        return numberOfProducts;
    }

    /**
     * @param numberOfProducts the numberOfProducts to set
     */
    public void setNumberOfProducts(long numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    /**
     * @return the numberOfOrders
     */
    public long getNumberOfOrders() {
        return numberOfOrders;
    }

    /**
     * @param numberOfOrders the numberOfOrders to set
     */
    public void setNumberOfOrders(long numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    /**
     * @return the numberOfLogins
     */
    public long getNumberOfLogins() {
        return numberOfLogins;
    }

    /**
     * @param numberOfLogins the numberOfLogins to set
     */
    public void setNumberOfLogins(long numberOfLogins) {
        this.numberOfLogins = numberOfLogins;
    }

    /**
     * @return the confirm
     */
    public boolean getConfirm() {
        return isConfirm();
    }

    /**
     * @param confirm the confirm to set
     */
    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    /**
     * @return the active
     */
    public boolean getActive() {
        return isActive();
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the accessLevels
     */
    public List<String> getAccessLevels() {
        return accessLevels;
    }

    /**
     * @param accessLevels the accessLevels to set
     */
    public void setAccessLevels(List<String> accessLevels) {
        this.accessLevels = accessLevels;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the streetNumber
     */
    public String getStreetNumber() {
        return streetNumber;
    }

    /**
     * @param streetNumber the streetNumber to set
     */
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * @return the flatNumber
     */
    public String getFlatNumber() {
        return flatNumber;
    }

    /**
     * @param flatNumber the flatNumber to set
     */
    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the version
     */
    public long getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(long version) {
        this.version = version;
    }

    /**
     * @return the confirm
     */
    public boolean isConfirm() {
        return confirm;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the lastLoginDate
     */
    public String getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * @param lastLoginDate the lastLoginDate to set
     */
    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * @return the lastLoginIp
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * @param lastLoginIp the lastLoginIp to set
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
  
    
}
