/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.mapper;

import java.util.List;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Order1;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderShipping;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderStatus;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.BasicOrderStatusDto;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.BasicOrderShippingMethodDto;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.BuyerDto;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.ListOrderDto;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.SellerDto;

/**
 *
 * @author michal
 */
@Mapper
@DecoratedWith(OrderMapperDecorator.class)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    
    List<ListOrderDto> orderToFullListDto(List<Order1> orders);
    List<BasicOrderStatusDto> statusToDto(List<OrderStatus> orderStatus);
    
    
    SellerDto accountToSellerDto(Account account);
    BuyerDto accountToBuyerDto(Account account);
    BasicOrderStatusDto statusToDto(OrderStatus orderStatus);
    BasicOrderShippingMethodDto shippingToDto(OrderShipping shippingMethod);
    
}
