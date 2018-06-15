/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fifi
 */
@Entity
@Table(name = "public.order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Order1.findAll", query = "SELECT o FROM Order1 o")
    , @NamedQuery(name = "Order1.findById", query = "SELECT o FROM Order1 o WHERE o.id = :id")
    , @NamedQuery(name = "Order1.findByOrderPlacedDate", query = "SELECT o FROM Order1 o WHERE o.orderPlacedDate = :orderPlacedDate")
    , @NamedQuery(name = "Order1.findByTotalPrice", query = "SELECT o FROM Order1 o WHERE o.totalPrice = :totalPrice")
    , @NamedQuery(name = "Order1.findByOrderNumber", query = "SELECT o FROM Order1 o WHERE o.orderNumber = :orderNumber")
    , @NamedQuery(name = "Order1.findByBuyer", query = "SELECT o FROM Order1 o WHERE o.buyerId.login = :login")
    , @NamedQuery(name = "Order1.findBySeller", query = "SELECT o FROM Order1 o WHERE o.sellerId.login = :login")
    , @NamedQuery(name = "Order1.findByIsClosed", query = "SELECT o FROM Order1 o WHERE o.isClosed = :isClosed")
    , @NamedQuery(name = "Order1.findByVersion", query = "SELECT o FROM Order1 o WHERE o.version = :version")})
public class Order1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name = "ID_ORDER_SEQUENCE", sequenceName = "order_id_seq", allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ORDER_SEQUENCE")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "order_placed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderPlacedDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "order_number")
    private long orderNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_closed")
    private boolean isClosed;
    @Basic(optional = false)
    @NotNull
    @Version
    @Column(name = "version")
    private long version = 0;
    @OneToMany(mappedBy = "orderId")
    private Collection<OrderProducts> orderProductsCollection = new ArrayList<>();
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account buyerId;
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account sellerId;
    @JoinColumn(name = "shipping_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OrderShipping shippingId;
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OrderStatus statusId;

    public Order1() {
    }

    public Order1(Date orderPlacedDate, BigDecimal totalPrice, long orderNumber, boolean isClosed, long version) {
        this.orderPlacedDate = orderPlacedDate;
        this.totalPrice = totalPrice;
        this.orderNumber = orderNumber;
        this.isClosed = isClosed;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public Date getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public void setOrderPlacedDate(Date orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    public boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    @XmlTransient
    public Collection<OrderProducts> getOrderProductsCollection() {
        return orderProductsCollection;
    }

    public void setOrderProductsCollection(Collection<OrderProducts> orderProductsCollection) {
        this.orderProductsCollection = orderProductsCollection;
    }

    public Account getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Account buyerId) {
        this.buyerId = buyerId;
    }

    public Account getSellerId() {
        return sellerId;
    }

    public void setSellerId(Account sellerId) {
        this.sellerId = sellerId;
    }

    public OrderShipping getShippingId() {
        return shippingId;
    }

    public void setShippingId(OrderShipping shippingId) {
        this.shippingId = shippingId;
    }

    public OrderStatus getStatusId() {
        return statusId;
    }

    public void setStatusId(OrderStatus statusId) {
        this.statusId = statusId;
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
        if (!(object instanceof Order1)) {
            return false;
        }
        Order1 other = (Order1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2018.ssbd01.moz.entity.Order1[ id=" + id + " ]";
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
