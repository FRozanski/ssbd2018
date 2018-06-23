/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.dto;

/**
 *
 * @author dlange
 */
public class OrderStatusUpdateDto  {
    
    private long orderId;
    private long statusId;

    public void setOrderId(long value) {
        this.orderId = value;
    }

    public long getOrderId() {
        return this.orderId;
    }

    public void setStatusId(long value) {
        this.statusId = value;
    }

    public long getStatusId() {
        return this.statusId;
    }

}
