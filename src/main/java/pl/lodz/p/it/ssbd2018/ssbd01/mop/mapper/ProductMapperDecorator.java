/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.mapper;

import java.util.List;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicProductDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.EditProductDto;

/**
 *
 * @author michal
 */
public abstract class ProductMapperDecorator implements ProductMapper {

    private final ProductMapper delegate;

    public ProductMapperDecorator(ProductMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<BasicProductDto> productsToDTO(List<Product> products) {
        List<BasicProductDto> dto = delegate.productsToDTO(products);
        for (int i = 0; i < products.size(); i++) {
            dto.get(i).setUnit(delegate.unitToBasicUnitDto(products.get(i).getUnitId()));
            dto.get(i).setOwner(delegate.ownerToBasicOwnerDTO(products.get(i).getOwnerId()));
            dto.get(i).setCategory(delegate.categoryToBasicCategoryDTO(products.get(i).getCategoryId()));
        }
        return dto;
    }

    @Override
    public EditProductDto editableProductToDto(Product product) {
        EditProductDto dto = delegate.editableProductToDto(product);
        dto.setUnitName(product.getUnitId().getUnitName());
        dto.setCategoryName(product.getCategoryId().getCategoryName());
        return dto;
    }
}
