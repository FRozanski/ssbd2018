/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.NAME_PATTERN_ERROR;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.SHIPPING_METHOD_NAME_LENGTH_ERROR;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.SHIPPING_METHOD_NAME_PATTERN_ERROR;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.SHIPPING_METHOD_PRICE_PRECISION_ERROR;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.SHIPPING_METHOD_PRICE_TOO_HIGH_ERROR;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.SHIPPING_METHOD_PRICE_TOO_LOW_ERROR;

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
    @SequenceGenerator(name="ID_SHIPPINGMETHOD_SEQUENCE" ,sequenceName = "shipping_method_id_seq", allocationSize=1, initialValue=1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SHIPPINGMETHOD_SEQUENCE")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Pattern(regexp = "[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+([ '-][a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+)*", message = SHIPPING_METHOD_NAME_PATTERN_ERROR)
    @Size(min = 1, max = 16, message = SHIPPING_METHOD_NAME_LENGTH_ERROR)
    @Column(name = "name")
    private String name;
    @DecimalMin(value="0.0", message = SHIPPING_METHOD_PRICE_TOO_LOW_ERROR)
    @DecimalMax(value="999.99", message = SHIPPING_METHOD_PRICE_TOO_HIGH_ERROR)
    @Digits(integer=3, fraction = 2, message = SHIPPING_METHOD_PRICE_PRECISION_ERROR)
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private BigDecimal price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account createdBy;
    @Basic(optional = false)
    @NotNull
    @Version
    @Column(name = "version")
    private long version = 0;
    
    public ShippingMethod(){        
    }

    public ShippingMethod(String name, BigDecimal price, boolean active, Account createdBy, long version) {
        this.name = name;
        this.price = price;
        this.active = active;
        this.createdBy = createdBy;
        this.version = version;
    }

    public Long getId() {
        return id;
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
    
    public Account getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(Account createdBy) {
        this.createdBy = createdBy;
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
    
}
