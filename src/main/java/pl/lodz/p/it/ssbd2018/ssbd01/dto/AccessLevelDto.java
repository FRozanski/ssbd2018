/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author agkan
 */
@XmlRootElement
public class AccessLevelDto implements Serializable {
    
    private Long id;
    private String level;
    private boolean active;

    public AccessLevelDto(Long id, String level, boolean active) {
        this.id = id;
        this.level = level;
        this.active = active;
    }
    
    public AccessLevelDto() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
}
