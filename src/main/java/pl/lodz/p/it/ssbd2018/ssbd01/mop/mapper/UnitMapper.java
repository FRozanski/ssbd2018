/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.mapper;

import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Unit;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicUnitDto;

/**
 *
 * @author agkan
 */
@Mapper
public interface UnitMapper {
    
    UnitMapper INSTANCE = Mappers.getMapper(UnitMapper.class);

    public List<BasicUnitDto> unitsToDto(List<Unit> units);
}
