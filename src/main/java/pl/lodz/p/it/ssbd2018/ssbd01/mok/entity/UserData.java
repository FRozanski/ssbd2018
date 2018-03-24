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
@Table(name = "user_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserData.findAll", query = "SELECT u FROM UserData u")
    , @NamedQuery(name = "UserData.findById", query = "SELECT u FROM UserData u WHERE u.id = :id")
    , @NamedQuery(name = "UserData.findByNip", query = "SELECT u FROM UserData u WHERE u.nip = :nip")
    , @NamedQuery(name = "UserData.findByVersion", query = "SELECT u FROM UserData u WHERE u.version = :version")})
public class UserData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "nip")
    private String nip;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private long version;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private AccountAlevel accountAlevel;

    public UserData() {
    }

    public UserData(Long id) {
        this.id = id;
    }

    public UserData(Long id, String nip, long version) {
        this.id = id;
        this.nip = nip;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
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
        if (!(object instanceof UserData)) {
            return false;
        }
        UserData other = (UserData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2018.ssbd01.mok.entity.UserData[ id=" + id + " ]";
    }
    
}
