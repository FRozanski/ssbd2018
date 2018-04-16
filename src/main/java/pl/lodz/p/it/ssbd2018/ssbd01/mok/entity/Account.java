/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.entity.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.entity.Order1;

/**
 *
 * @author fifi
 */
@Entity
@Table(name = "account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
    , @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id")
    , @NamedQuery(name = "Account.findByLogin", query = "SELECT a FROM Account a WHERE a.login = :login")
    , @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password")
    , @NamedQuery(name = "Account.findByConfirm", query = "SELECT a FROM Account a WHERE a.confirm = :confirm")
    , @NamedQuery(name = "Account.findByActive", query = "SELECT a FROM Account a WHERE a.active = :active")
    , @NamedQuery(name = "Account.findByUserDataId", query = "SELECT a FROM Account a WHERE a.userDataId = :userDataId")
    , @NamedQuery(name = "Account.findByNumberOfProducts", query = "SELECT a FROM Account a WHERE a.numberOfProducts = :numberOfProducts")
    , @NamedQuery(name = "Account.findByNumberOfOrders", query = "SELECT a FROM Account a WHERE a.numberOfOrders = :numberOfOrders")
    , @NamedQuery(name = "Account.findByNumberOfLogins", query = "SELECT a FROM Account a WHERE a.numberOfLogins = :numberOfLogins")
    , @NamedQuery(name = "Account.findByVersion", query = "SELECT a FROM Account a WHERE a.version = :version")})
public class Account implements Serializable {    

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name="ID_ACCOUNT_SEQUENCE" ,sequenceName = "account_id_seq")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ACCOUNT_SEQUENCE")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "login")
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
    @Column(name = "user_data_id")
    private BigInteger userDataId;
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

    public Account(Long id) {
        this.id = id;
    }

    public Account(Long id, String login, String password, boolean confirm, boolean active, long numberOfProducts, long numberOfOrders, long numberOfLogins, long version) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.confirm = confirm;
        this.active = active;
        this.numberOfProducts = numberOfProducts;
        this.numberOfOrders = numberOfOrders;
        this.numberOfLogins = numberOfLogins;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
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

    public BigInteger getUserDataId() {
        return userDataId;
    }

    public void setUserDataId(BigInteger userDataId) {
        this.userDataId = userDataId;
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
