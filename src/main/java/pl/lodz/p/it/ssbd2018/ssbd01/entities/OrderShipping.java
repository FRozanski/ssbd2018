/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "order_shipping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderShipping.findAll", query = "SELECT o FROM OrderShipping o")
    , @NamedQuery(name = "OrderShipping.findById", query = "SELECT o FROM OrderShipping o WHERE o.id = :id")
    , @NamedQuery(name = "OrderShipping.findByShippingMethodName", query = "SELECT o FROM OrderShipping o WHERE o.shippingMethodName = :shippingMethodName")
    , @NamedQuery(name = "OrderShipping.findByName", query = "SELECT o FROM OrderShipping o WHERE o.name = :name")
    , @NamedQuery(name = "OrderShipping.findBySurname", query = "SELECT o FROM OrderShipping o WHERE o.surname = :surname")
    , @NamedQuery(name = "OrderShipping.findByStreet", query = "SELECT o FROM OrderShipping o WHERE o.street = :street")
    , @NamedQuery(name = "OrderShipping.findByStreetNumber", query = "SELECT o FROM OrderShipping o WHERE o.streetNumber = :streetNumber")
    , @NamedQuery(name = "OrderShipping.findByFlatNumber", query = "SELECT o FROM OrderShipping o WHERE o.flatNumber = :flatNumber")
    , @NamedQuery(name = "OrderShipping.findByPostalCode", query = "SELECT o FROM OrderShipping o WHERE o.postalCode = :postalCode")
    , @NamedQuery(name = "OrderShipping.findByCity", query = "SELECT o FROM OrderShipping o WHERE o.city = :city")
    , @NamedQuery(name = "OrderShipping.findByCountry", query = "SELECT o FROM OrderShipping o WHERE o.country = :country")
    , @NamedQuery(name = "OrderShipping.findByVersion", query = "SELECT o FROM OrderShipping o WHERE o.version = :version")})
public class OrderShipping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "shipping_method_name")
    private String shippingMethodName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "surname")
    private String surname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "street")
    private String street;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "street_number")
    private String streetNumber;
    @Size(max = 10)
    @Column(name = "flat_number")
    private String flatNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "postal_code")
    private String postalCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "country")
    private String country;
    @Basic(optional = false)
    @NotNull
    @Version
    @Column(name = "version")
    private long version = 0;
    @OneToMany(mappedBy = "shippingId")
    private Collection<Order1> order1Collection = new ArrayList<>();
    
    public OrderShipping(){        
    }

    public OrderShipping(String shippingMethodName, String name, String surname, String street, String streetNumber, String postalCode, String city, String country, long version) {
        this.shippingMethodName = shippingMethodName;
        this.name = name;
        this.surname = surname;
        this.street = street;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public String getShippingMethodName() {
        return shippingMethodName;
    }

    public void setShippingMethodName(String shippingMethodName) {
        this.shippingMethodName = shippingMethodName;
    }

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

    @XmlTransient
    public Collection<Order1> getOrder1Collection() {
        return order1Collection;
    }

    public void setOrder1Collection(Collection<Order1> order1Collection) {
        this.order1Collection = order1Collection;
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
        if (!(object instanceof OrderShipping)) {
            return false;
        }
        OrderShipping other = (OrderShipping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2018.ssbd01.moz.entity.OrderShipping[ id=" + id + " ]";
    }
    
}
