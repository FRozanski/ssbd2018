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
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Unit;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.dto.EditableAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicCategoryDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicProductOwnerDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicUnitDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicProductDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.NewProductDto;

/**
 *
 * @author michal
 */
@Mapper
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    List<BasicProductDto> productsToDTO(List<Product> products);

    BasicProductOwnerDto ownerToBasicOwnerDTO(Account account);

    BasicCategoryDto categoryToBasicCategoryDTO(Category category);

    BasicUnitDto unitToBasicUnitDto(Unit unit);

//    @Mappings({
//        @Mapping(target = "unitId", ignore = true),
//        @Mapping(target = "ownerId", ignore = true),
//        @Mapping(target = "categoryId", ignore = true)
//      })
    @Mapping(target = "unitId", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "categoryId", ignore = true)
    NewProductDto productToNewProductDto(Product product);
    
    @InheritInverseConfiguration(name = "productToNewProductDto")
//    @Mappings({
//        @Mapping(target = "unitId", ignore = true),
//        @Mapping(target = "ownerId", ignore = true),
//        @Mapping(target = "categoryId", ignore = true)   
//      })
    Product newProductDtoToProduct(NewProductDto newProduct, @MappingTarget Product product);
}
