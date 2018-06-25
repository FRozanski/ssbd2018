/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderStatus;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.BasicOrderStatusDto;

/**
 *
 * @author dlange
 */


@Mapper
public interface OrderStatusMapper {
    
     OrderStatusMapper INSTANCE = Mappers.getMapper(OrderStatusMapper.class);
     OrderStatus DtoToOrderStatus(BasicOrderStatusDto orderStatusDto);
    
}
