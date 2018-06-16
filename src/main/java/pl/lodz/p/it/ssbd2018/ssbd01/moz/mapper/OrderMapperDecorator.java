/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.mapper;

import java.util.List;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Order1;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.ListOrderDto;

/**
 *
 * @author michal
 */
public abstract class OrderMapperDecorator implements OrderMapper {

    private final OrderMapper delegate;

    public OrderMapperDecorator(OrderMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<ListOrderDto> orderToFullListDto(List<Order1> orders) {
        List<ListOrderDto> dto = delegate.orderToFullListDto(orders);
        for (int i=0; i<orders.size(); i++) {
            dto.get(i).setBuyer(delegate.accountToBuyerDto(orders.get(i).getBuyerId()));
            dto.get(i).setSeller(delegate.accountToSellerDto(orders.get(i).getSellerId()));
            dto.get(i).setStatus(delegate.statusToDto(orders.get(i).getStatusId()));
            dto.get(i).setShipping(delegate.shippingToDto(orders.get(i).getShippingId()));
           
        }
        return dto;
    }
}
