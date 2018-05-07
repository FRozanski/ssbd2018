/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fifi
 */
@Entity
@Table(name = "account")
@SecondaryTable(name="user_data", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
    , @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id")
    , @NamedQuery(name = "Account.findByLogin", query = "SELECT a FROM Account a WHERE a.login = :login")
    , @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password")
    , @NamedQuery(name = "Account.findByConfirm", query = "SELECT a FROM Account a WHERE a.confirm = :confirm")
    , @NamedQuery(name = "Account.findByActive", query = "SELECT a FROM Account a WHERE a.active = :active")
    , @NamedQuery(name = "Account.findByNumberOfProducts", query = "SELECT a FROM Account a WHERE a.numberOfProducts = :numberOfProducts")
    , @NamedQuery(name = "Account.findByNumberOfOrders", query = "SELECT a FROM Account a WHERE a.numberOfOrders = :numberOfOrders")
    , @NamedQuery(name = "Account.findByNumberOfLogins", query = "SELECT a FROM Account a WHERE a.numberOfLogins = :numberOfLogins")
    , @NamedQuery(name = "Account.findByVersion", query = "SELECT a FROM Account a WHERE a.version = :version")})
public class Account implements Serializable {    

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name="ID_ACCOUNT_SEQUENCE" ,sequenceName = "account_id_seq", allocationSize=1, initialValue=9)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ACCOUNT_SEQUENCE")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "login", updatable = false)
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "confirm")
    private boolean confirm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @NotNull
    @Column(name = "number_of_products")
    private long numberOfProducts;
    @Basic(optional = false)
    @NotNull
    @Column(name = "number_of_orders")
    private long numberOfOrders;
    @Basic(optional = false)
    @NotNull
    @Column(name = "number_of_logins")
    private long numberOfLogins;
    @Basic(optional = false)
    @NotNull
    @Version
    @Column(name = "version")
    private long version;
    @OneToMany(mappedBy = "buyerId")
    private Collection<Order1> ordersAsBuyer = new ArrayList<>();
    @OneToMany(mappedBy = "sellerId")
    private Collection<Order1> ordersAsSeller = new ArrayList<>();
    @OneToMany(mappedBy = "ownerId")
    private Collection<Product> productCollection = new ArrayList<>();
    
    //secondary table UserData
    @Basic(optional = false)
    @NotNull
    @Version
    @Column(name = "version", table = "user_data")
    @JoinColumns( {
      @JoinColumn(name = "version", referencedColumnName = "version", table = "account") })
    private long versionUserData;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "name", table = "user_data")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "surname", table = "user_data")
    private String surname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "email", table = "user_data")
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 18)
    @Column(name = "phone", table = "user_data")
    private String phone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "street", table = "user_data")
    private String street;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "street_number", table = "user_data")
    private String streetNumber;
    @Size(max = 10)
    @Column(name = "flat_number", table = "user_data")
    private String flatNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "postal_code", table = "user_data")
    private String postalCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "city", table = "user_data")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "country", table = "user_data")
    private String country;
    
    public Account(){
    }

    public Long getId() {
        return id;
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

    public boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
    
    @XmlTransient
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    @XmlTransient
    public Collection<Order1> getOrder1Collection() {
        return ordersAsBuyer;
    }

    public void setOrder1Collection(Collection<Order1> order1Collection) {
        this.ordersAsBuyer = order1Collection;
    }

    @XmlTransient
    public Collection<Order1> getOrder1Collection1() {
        return ordersAsSeller;
    }

    public void setOrder1Collection1(Collection<Order1> order1Collection1) {
        this.ordersAsSeller = order1Collection1;
    }
    
    //secondary table UserData
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2018.ssbd01.mok.entity.Account[ id=" + id + " ]";
    }        
}
