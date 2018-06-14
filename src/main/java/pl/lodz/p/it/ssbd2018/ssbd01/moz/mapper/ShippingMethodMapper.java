/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.BasicShippingMethodDto;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.FullShippingMethodDto;

/**
 *
 * @author michal
 */
@Mapper
public interface ShippingMethodMapper {
    ShippingMethodMapper INSTANCE = Mappers.getMapper(ShippingMethodMapper.class);
    
    List<BasicShippingMethodDto> shippingMethodToBasicDto(List<ShippingMethod> shippingMethods);
    FullShippingMethodDto shippingMethodToFullDto(ShippingMethod shippingMethod);
}
