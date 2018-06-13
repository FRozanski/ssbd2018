/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.dto;

import java.math.BigDecimal;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Unit;

/**
 *
 * @author michal
 */
public class ProductDto {
    
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal qty;
    private boolean active;
    private long version;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the qty
     */
    public BigDecimal getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(BigDecimal qty) {
        this.qty = qty;
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
