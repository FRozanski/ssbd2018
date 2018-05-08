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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author agkan
 */
@Entity
@Table(name = "veryfication_token")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VeryficationToken.findAll", query = "SELECT v FROM VeryficationToken v")
    , @NamedQuery(name = "VeryficationToken.findById", query = "SELECT v FROM VeryficationToken v WHERE v.id = :id")
    , @NamedQuery(name = "VeryficationToken.findByToken", query = "SELECT v FROM VeryficationToken v WHERE v.token = :token")
    , @NamedQuery(name = "VeryficationToken.findByExpiryDate", query = "SELECT v FROM VeryficationToken v WHERE v.expiryDate = :expiryDate")})
public class VeryficationToken implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name="ID_VERYFICATION_TOKEN_SEQUENCE" ,sequenceName = "veryfication_token_id_seq")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_VERYFICATION_TOKEN_SEQUENCE")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "token")
    private String token;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expiry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;
    @JoinColumn(name = "id_account", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account idAccount;

    public VeryficationToken() {
    }

    public VeryficationToken(Long id) {
        this.id = id;
    }

    public VeryficationToken(Long id, String token, Date expiryDate) {
        this.id = id;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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
        if (!(object instanceof VeryficationToken)) {
            return false;
        }
        VeryficationToken other = (VeryficationToken) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2018.ssbd01.entities.VeryficationToken[ id=" + id + " ]";
    }
    
}
