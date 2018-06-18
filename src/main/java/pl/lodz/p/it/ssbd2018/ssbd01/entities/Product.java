/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.entities;

import java.io.Serializable;
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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.PRODUCT_DESCRIPTION_LENGTH_ERROR;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.PRODUCT_NAME_LENGTH_ERROR;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.PRODUCT_NAME_PATTERN_ERROR;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.PRODUCT_PRICE_ERROR;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.PRODUCT_QTY_ERROR;

/**
 *
 * @author fifi
 * @author michal
 */
@Entity
@Table(name = "product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    , @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id")
    , @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name")
    , @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description")
    , @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price")
    , @NamedQuery(name = "Product.findByQty", query = "SELECT p FROM Product p WHERE p.qty = :qty")
    , @NamedQuery(name = "Product.findByActiveProductAndCategory", query = "SELECT p FROM Product p WHERE p.active = :active and p.categoryId.active = :activeCategory")
    , @NamedQuery(name = "Product.findByVersion", query = "SELECT p FROM Product p WHERE p.version = :version")
    , @NamedQuery(name = "Product.findByOwnerLogin", query = "SELECT p FROM Product p WHERE p.ownerId.login = :login")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name="ID_PRODUCT_SEQUENCE" ,sequenceName = "product_id_seq", allocationSize=1, initialValue=1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_PRODUCT_SEQUENCE")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Pattern(regexp = "[a-zA-ZąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\s]+", message = PRODUCT_NAME_PATTERN_ERROR)    
    @Size(min = 1, max = 32, message = PRODUCT_NAME_LENGTH_ERROR)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 800, message = PRODUCT_DESCRIPTION_LENGTH_ERROR)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Pattern(regexp = "[0-9]{1,6}+(\\.[0-9][0-9]?)?", message = PRODUCT_PRICE_ERROR)  
    @Digits(integer = 6, fraction = 2, message = PRODUCT_PRICE_ERROR)   
    @Column(name = "price")
    private String price;
    @Basic(optional = false)
    @NotNull
    @Pattern(regexp = "[0-9]{1,6}+(\\.[0-9][0-9][0-9]?)?", message = PRODUCT_QTY_ERROR)  
    @Digits(integer = 6, fraction = 3, message = PRODUCT_QTY_ERROR)  
    @Column(name = "qty")
    private String qty;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @NotNull
    @Version
    @Column(name = "version")
    private long version = 0;
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account ownerId;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Category categoryId;
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Unit unitId;
    
    public Product(){        
    }

    public Product(String name, String description, String price, String qty, boolean active, long version) {        
        this.name = name;
        this.description = description;
        this.price = price;
        this.qty = qty;
        this.active = active;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Account getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Account ownerId) {
        this.ownerId = ownerId;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public Unit getUnitId() {
        return unitId;
    }

    public void setUnitId(Unit unitId) {
        this.unitId = unitId;
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
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2018.ssbd01.mop.entity.Product[ id=" + id + " ]";
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
