/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.NewProductDto;

/**
 *
 * @author agkan
 */
@Mapper
public interface NewProductMapper {
    
    NewProductMapper INSTANCE = Mappers.getMapper(NewProductMapper.class);
    
    @Mappings({
        @Mapping(target = "unitId", ignore = true),
        @Mapping(target = "ownerId", ignore = true),
        @Mapping(target = "categoryId", ignore = true)})
    NewProductDto productToNewProductDto(Product product);
    
    @InheritInverseConfiguration(name = "productToNewProductDto")
    @Mappings({
        @Mapping(target = "unitId", ignore = true),
        @Mapping(target = "ownerId", ignore = true),
        @Mapping(target = "categoryId", ignore = true)})
    Product newProductDtoToProduct(NewProductDto newProduct, @MappingTarget Product product);
}
