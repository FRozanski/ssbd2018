/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.entities;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author michal
 */
@Entity
@Table(name = "archival_password")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArchivalPassword.findAll", query = "SELECT a FROM ArchivalPassword a")
    , @NamedQuery(name = "ArchivalPassword.findById", query = "SELECT a FROM ArchivalPassword a WHERE a.id = :id")
    , @NamedQuery(name = "ArchivalPassword.findByPassword", query = "SELECT a FROM ArchivalPassword a WHERE a.password = :password")
    , @NamedQuery(name = "ArchivalPassword.findBySettingDate", query = "SELECT a FROM ArchivalPassword a WHERE a.settingDate = :settingDate")
    , @NamedQuery(name = "ArchivalPassword.findByAccountId", query = "SELECT a FROM ArchivalPassword a WHERE a.idAccount.id = :id_account")})
public class ArchivalPassword implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @SequenceGenerator(name="ID_ARCHIVALPASSWORD_SEQUENCE" ,sequenceName = "archival_password_id_seq", allocationSize=1, initialValue=1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ARCHIVALPASSWORD_SEQUENCE")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "password", updatable = false)
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "setting_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date settingDate;
    @Basic(optional = false)
    @NotNull
    @Version
    @Column(name = "version")
    private long version;
    @JoinColumn(name = "id_account", referencedColumnName = "id", updatable = false)
    @NotNull
    @ManyToOne(optional = false)
    private Account idAccount;

    public ArchivalPassword() {
        this.version = 0;
    }

    public ArchivalPassword(String password, Date settingDate, Account idAccount) {
        this.password = password;
        this.settingDate = settingDate;
        this.version = 0;
        this.idAccount = idAccount;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getSettingDate() {
        return settingDate;
    }

    public void setSettingDate(Date settingDate) {
        this.settingDate = settingDate;
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
        if (!(object instanceof ArchivalPassword)) {
            return false;
        }
        ArchivalPassword other = (ArchivalPassword) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.netbeans.rest.application.config.ArchivalPassword[ id=" + id + " ]";
    }
    
}
