/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fifi
 */
@Entity
@Table(name = "shipping_method")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ShippingMethod.findAll", query = "SELECT s FROM ShippingMethod s")
    , @NamedQuery(name = "ShippingMethod.findById", query = "SELECT s FROM ShippingMethod s WHERE s.id = :id")
    , @NamedQuery(name = "ShippingMethod.findByName", query = "SELECT s FROM ShippingMethod s WHERE s.name = :name")
    , @NamedQuery(name = "ShippingMethod.findByPrice", query = "SELECT s FROM ShippingMethod s WHERE s.price = :price")
    , @NamedQuery(name = "ShippingMethod.findByActive", query = "SELECT s FROM ShippingMethod s WHERE s.active = :active")
    , @NamedQuery(name = "ShippingMethod.findByVersion", query = "SELECT s FROM ShippingMethod s WHERE s.version = :version")})
public class ShippingMethod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private BigDecimal price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private long version;

    public ShippingMethod() {
    }

    public ShippingMethod(Long id) {
        this.id = id;
    }

    public ShippingMethod(Long id, String name, BigDecimal price, boolean active, long version) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
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
        if (!(object instanceof ShippingMethod)) {
            return false;
        }
        ShippingMethod other = (ShippingMethod) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2018.ssbd01.moz.entity.ShippingMethod[ id=" + id + " ]";
    }
    
}
