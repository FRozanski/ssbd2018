/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.dto;

/**
 *
 * @author michal
 */
public class ListOrderDto extends BasicOrderDto {
    private BuyerDto buyer;
    private SellerDto seller;
    private BasicOrderShippingMethodDto shipping;
    private BasicOrderStatusDto status;

    /**
     * @return the buyer
     */
    public BuyerDto getBuyer() {
        return buyer;
    }

    /**
     * @param buyer the buyer to set
     */
    public void setBuyer(BuyerDto buyer) {
        this.buyer = buyer;
    }

    /**
     * @return the seller
     */
    public SellerDto getSeller() {
        return seller;
    }

    /**
     * @param seller the seller to set
     */
    public void setSeller(SellerDto seller) {
        this.seller = seller;
    }

    /**
     * @return the shipping
     */
    public BasicOrderShippingMethodDto getShipping() {
        return shipping;
    }

    /**
     * @param shipping the shipping to set
     */
    public void setShipping(BasicOrderShippingMethodDto shipping) {
        this.shipping = shipping;
    }

    /**
     * @return the status
     */
    public BasicOrderStatusDto getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(BasicOrderStatusDto status) {
        this.status = status;
    }
    
}
