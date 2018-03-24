/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fifi
 */
@Entity
@Table(name = "manager_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ManagerData.findAll", query = "SELECT m FROM ManagerData m")
    , @NamedQuery(name = "ManagerData.findById", query = "SELECT m FROM ManagerData m WHERE m.id = :id")
    , @NamedQuery(name = "ManagerData.findByIntercom", query = "SELECT m FROM ManagerData m WHERE m.intercom = :intercom")
    , @NamedQuery(name = "ManagerData.findByPhone", query = "SELECT m FROM ManagerData m WHERE m.phone = :phone")
    , @NamedQuery(name = "ManagerData.findByVersion", query = "SELECT m FROM ManagerData m WHERE m.version = :version")})
public class ManagerData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "intercom")
    private String intercom;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "phone")
    private String phone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private long version;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private AccountAlevel accountAlevel;

    public ManagerData() {
    }

    public ManagerData(Long id) {
        this.id = id;
    }

    public ManagerData(Long id, String intercom, String phone, long version) {
        this.id = id;
        this.intercom = intercom;
        this.phone = phone;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntercom() {
        return intercom;
    }

    public void setIntercom(String intercom) {
        this.intercom = intercom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public AccountAlevel getAccountAlevel() {
        return accountAlevel;
    }

    public void setAccountAlevel(AccountAlevel accountAlevel) {
        this.accountAlevel = accountAlevel;
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
        if (!(object instanceof ManagerData)) {
            return false;
        }
        ManagerData other = (ManagerData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2018.ssbd01.mok.entity.ManagerData[ id=" + id + " ]";
    }
    
}
