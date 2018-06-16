/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.mapper;

import java.util.List;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.mapper.AccountMapperDecorator;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.BasicShippingMethodDto;

/**
 * Interfejs służący do mapowania obiektów typu {@link BasicShippingMethodDto} oraz {@link ShippingMethod}
 * @author Filip
 */
@Mapper
public interface ShippingMethodMapper {
    
    ShippingMethodMapper INSTANCE = Mappers.getMapper(ShippingMethodMapper.class);
    
    /**
     * Mapowanie obiektów typu {@link BasicShippingMethodDto} na {@link ShippingMethod}
     * @param shippingMethodDto obiekt dto metody przesyłki
     * @param shippingMethod obiekt metody przesyłki
     * @return obiekt typu {@link ShippingMethod}
     */
    @InheritInverseConfiguration
    ShippingMethod basicShippingMethodDtoToShippingMethod(BasicShippingMethodDto shippingMethodDto, 
            @MappingTarget ShippingMethod shippingMethod);            

       
    List<BasicShippingMethodDto> shippingMethodToBasicDto(List<ShippingMethod> shippingMethods);
    
    BasicShippingMethodDto shippingMethodToBasicDto(ShippingMethod shippingMethods);
    
}
