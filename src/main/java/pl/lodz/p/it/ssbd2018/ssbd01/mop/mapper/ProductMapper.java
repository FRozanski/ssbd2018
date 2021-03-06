/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.mapper;

import java.util.List;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Unit;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicCategoryDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicProductOwnerDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicUnitDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.ListProductDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.EditProductDto;

/**
 * Interfejs mapujący produkt
 * @author michal
 */
@Mapper
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    
    List<ListProductDto> productsToDTO(List<Product> products);
    
    EditProductDto editableProductToDto(Product product);
    BasicProductOwnerDto ownerToBasicOwnerDTO(Account account);
    BasicCategoryDto categoryToBasicCategoryDTO(Category category);
    BasicUnitDto unitToBasicUnitDto(Unit unit);
    
    @InheritInverseConfiguration
    Product editableProductToEntity(EditProductDto fullProductDto, @MappingTarget Product product);
}
