/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author fifi
 */
@Entity
@Table(name = "access_level")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessLevel.findAll", query = "SELECT a FROM AccessLevel a")
    , @NamedQuery(name = "AccessLevel.findById", query = "SELECT a FROM AccessLevel a WHERE a.id = :id")
    , @NamedQuery(name = "AccessLevel.findByLevel", query = "SELECT a FROM AccessLevel a WHERE a.level = :level")
    , @NamedQuery(name = "AccessLevel.findByActive", query = "SELECT a FROM AccessLevel a WHERE a.active = :active")
    , @NamedQuery(name = "AccessLevel.findByVersion", query = "SELECT a FROM AccessLevel a WHERE a.version = :version")})
public class AccessLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name="ID_ACCESSLEVEL_SEQUENCE" ,sequenceName = "access_level_id_seq")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_ACCESSLEVEL_SEQUENCE")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "level")
    private String level;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @NotNull
    @Version
    @Column(name = "version")
    private long version;

    public AccessLevel(Long id) {
        this.id = id;
    }

    public AccessLevel(Long id, String level, boolean active, long version) {
        this.id = id;
        this.level = level;
        this.active = active;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
        if (!(object instanceof AccessLevel)) {
            return false;
        }
        AccessLevel other = (AccessLevel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2018.ssbd01.mok.entity.AccessLevel[ id=" + id + " ]";
    }
    
}
