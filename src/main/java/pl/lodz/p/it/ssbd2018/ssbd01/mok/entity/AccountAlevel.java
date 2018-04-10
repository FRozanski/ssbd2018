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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fifi
 */
@Entity
@Table(name = "account_alevel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccountAlevel.findAll", query = "SELECT a FROM AccountAlevel a")
    , @NamedQuery(name = "AccountAlevel.findById", query = "SELECT a FROM AccountAlevel a WHERE a.id = :id")
    , @NamedQuery(name = "AccountAlevel.findByVersion", query = "SELECT a FROM AccountAlevel a WHERE a.version = :version")})
public class AccountAlevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private long version;
    @JoinColumn(name = "id_alevel", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AccessLevel idAlevel;
    @JoinColumn(name = "id_account", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account idAccount;

    public AccountAlevel() {
    }

    public AccountAlevel(Long id) {
        this.id = id;
    }

    public AccountAlevel(Long id, long version) {
        this.id = id;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public AccessLevel getIdAlevel() {
        return idAlevel;
    }

    public void setIdAlevel(AccessLevel idAlevel) {
        this.idAlevel = idAlevel;
    }

    public Account getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Account idAccount) {
        this.idAccount = idAccount;
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
        if (!(object instanceof AccountAlevel)) {
            return false;
        }
        AccountAlevel other = (AccountAlevel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2018.ssbd01.mok.entity.AccountAlevel[ id=" + id + " ]";
    }
    
}
