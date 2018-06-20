/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.dto;

import java.math.BigDecimal;
/**
 *
 * @author michal
 */
public class ListProductDto extends BasicProductDto {
    
    private Long id;
    private boolean active;
    private BasicCategoryDto category;
    private BasicUnitDto unit;
    private BasicProductOwnerDto owner;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the category
     */
    public BasicCategoryDto getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(BasicCategoryDto category) {
        this.category = category;
    }

    /**
     * @return the unit
     */
    public BasicUnitDto getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(BasicUnitDto unit) {
        this.unit = unit;
    }

    /**
     * @return the owner
     */
    public BasicProductOwnerDto getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(BasicProductOwnerDto owner) {
        this.owner = owner;
    }
    
}
