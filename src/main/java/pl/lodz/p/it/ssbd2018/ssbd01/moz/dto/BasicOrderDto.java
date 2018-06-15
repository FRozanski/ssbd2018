/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.dto;

import java.math.BigDecimal;

/**
 *
 * @author michal
 */
public class BasicOrderDto {
    private Long id;
    private String orderPlacedDate;
    private BigDecimal totalPrice;
    private boolean isClosed;

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
     * @return the orderPlacedDate
     */
    public String getOrderPlacedDate() {
        return orderPlacedDate;
    }

    /**
     * @param orderPlacedDate the orderPlacedDate to set
     */
    public void setOrderPlacedDate(String orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }

    /**
     * @return the totalPrice
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    /**
     * @return the isClosed
     */
    public boolean isIsClosed() {
        return isClosed;
    }

    /**
     * @param isClosed the isClosed to set
     */
    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }
    
}
