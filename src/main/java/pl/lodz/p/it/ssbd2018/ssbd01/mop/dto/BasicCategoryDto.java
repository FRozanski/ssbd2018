/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.dto;

/**
 * Klasa realizująca wzorzec projektowy DTO - mapowanie surowych danych na obiekty typu {@link Category}
 * @author michal
 */
public class BasicCategoryDto {

    private Long id;
    private String categoryName;
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
