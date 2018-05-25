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
    , @NamedQuery(name = "AccountAlevel.findByVersion", query = "SELECT a FROM AccountAlevel a WHERE a.version = :version")
    , @NamedQuery(name = "AccountAlevel.findByAccountAccessLevel", query = "SELECT a FROM AccountAlevel a WHERE a.idAccount = :id_account AND a.idAlevel = :id_alevel")})
public class AccountAlevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name="ID_ACCOUNTALEVEL_SEQUENCE" ,sequenceName = "account_alevel_id_seq", allocationSize=1, initialValue=10)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ACCOUNTALEVEL_SEQUENCE")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Version
    @Column(name = "version")
    private long version = 0;

    @JoinColumn(name = "id_alevel", referencedColumnName = "id", updatable = false)
    @ManyToOne(optional = false)
    private AccessLevel idAlevel;
    @JoinColumn(name = "id_account", referencedColumnName = "id", updatable = false)
    @ManyToOne(optional = false)
    private Account idAccount;
    
    public AccountAlevel(){
    }

    public Long getId() {
        return id;
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
