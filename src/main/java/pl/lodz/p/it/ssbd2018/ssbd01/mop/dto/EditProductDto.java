/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.dto;

/**
 *
 * @author michal
 */
public class EditProductDto extends BasicProductDto {
    
    private long version;

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
    
}
