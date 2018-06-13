/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author michal
 */
public class BasicAccountDto {
    private long id;
    private List<String> accessLevels = new ArrayList<>();

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the accessLevels
     */
    public List<String> getAccessLevels() {
        return accessLevels;
    }

    /**
     * @param accessLevels the accessLevels to set
     */
    public void setAccessLevels(List<String> accessLevels) {
        this.accessLevels = accessLevels;
    }
}
