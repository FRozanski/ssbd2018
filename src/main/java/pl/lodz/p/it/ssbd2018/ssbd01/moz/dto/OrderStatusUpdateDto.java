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
public class OrderStatusUpdateDto extends BasicOrderDto {
    
    private BasicOrderStatusDto status;
    
    public BasicOrderStatusDto getStatus() {
        return status;
    }

    public void setStatus(BasicOrderStatusDto status) {
        this.status = status;
    }
}
