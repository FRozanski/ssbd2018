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
 * Interfejs komunikujący się z biblioteką mapstruct, odpowiadający za mapowanie obiektów DTO na obiekty encji i odwrotnie, dedykowany nowym produktom
 * @author agkan
 */
@Mapper
public interface NewProductMapper {
    
    /**
     * Instancja interfejsu {@link NewProductMapper}
     */
    NewProductMapper INSTANCE = Mappers.getMapper(NewProductMapper.class);
    
    /**
     * Mapuje obiekt encji klasy {@link Product} na obiekt DTO klasy {@link NewProductDto}
     * @param product   obiekt encji
     * @return          obiekt DTO
     */
    @Mappings({
        @Mapping(target = "unitId", ignore = true),
        @Mapping(target = "categoryId", ignore = true)})
    NewProductDto productToNewProductDto(Product product);
    
    /**
     * Mapuje obiekt DTO klasy {@link NewProductDto} na obiekt encji klasy {@link Product}
     * @param newProduct    obiekt DTO
     * @param product       obiekt encji
     * @return              obiekt encji
     */
    @InheritInverseConfiguration(name = "productToNewProductDto")
    @Mappings({
        @Mapping(target = "unitId", ignore = true),
        @Mapping(target = "categoryId", ignore = true)
        })        
    Product newProductDtoToProduct(NewProductDto newProduct, @MappingTarget Product product);
}
