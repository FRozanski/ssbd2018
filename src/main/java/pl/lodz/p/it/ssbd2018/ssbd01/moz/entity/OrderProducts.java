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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fifi
 */
@Entity
@Table(name = "order_products")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderProducts.findAll", query = "SELECT o FROM OrderProducts o")
    , @NamedQuery(name = "OrderProducts.findById", query = "SELECT o FROM OrderProducts o WHERE o.id = :id")
    , @NamedQuery(name = "OrderProducts.findByProductName", query = "SELECT o FROM OrderProducts o WHERE o.productName = :productName")
    , @NamedQuery(name = "OrderProducts.findByProductQty", query = "SELECT o FROM OrderProducts o WHERE o.productQty = :productQty")
    , @NamedQuery(name = "OrderProducts.findByProductUnitName", query = "SELECT o FROM OrderProducts o WHERE o.productUnitName = :productUnitName")
    , @NamedQuery(name = "OrderProducts.findByProductValue", query = "SELECT o FROM OrderProducts o WHERE o.productValue = :productValue")
    , @NamedQuery(name = "OrderProducts.findByVersion", query = "SELECT o FROM OrderProducts o WHERE o.version = :version")})
public class OrderProducts implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name="ID_ORDERPRODUCTS_SEQUENCE" ,sequenceName = "order_products_id_seq")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ORDERPRODUCTS_SEQUENCE")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "product_name")
    private String productName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "product_qty")
    private BigDecimal productQty;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "product_unit_name")
    private String productUnitName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "product_value")
    private BigDecimal productValue;
    @Basic(optional = false)
    @NotNull
    @Version
    @Column(name = "version")
    private long version;
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Order1 orderId;
    
    public OrderProducts(){        
    }
    
    public OrderProducts(Long id) {
        this.id = id;
    }

    public OrderProducts(Long id, String productName, BigDecimal productQty, String productUnitName, BigDecimal productValue, long version) {
        this.id = id;
        this.productName = productName;
        this.productQty = productQty;
        this.productUnitName = productUnitName;
        this.productValue = productValue;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductQty() {
        return productQty;
    }

    public void setProductQty(BigDecimal productQty) {
        this.productQty = productQty;
    }

    public String getProductUnitName() {
        return productUnitName;
    }

    public void setProductUnitName(String productUnitName) {
        this.productUnitName = productUnitName;
    }

    public BigDecimal getProductValue() {
        return productValue;
    }

    public void setProductValue(BigDecimal productValue) {
        this.productValue = productValue;
    }

    public Order1 getOrderId() {
        return orderId;
    }

    public void setOrderId(Order1 orderId) {
        this.orderId = orderId;
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
        if (!(object instanceof OrderProducts)) {
            return false;
        }
        OrderProducts other = (OrderProducts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2018.ssbd01.moz.entity.OrderProducts[ id=" + id + " ]";
    }
    
}
