/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Unit;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicUnitDto;

/**
 * Interfejs komunikujący się z biblioteką mapstruct, odpowiadający za mapowanie obiektów DTO na obiekty encji i odwrotnie, dedykowany jednostkom miar
 * @author agkan
 */
@Mapper
public interface UnitMapper {

    /**
     * Instancja interfejsu {@link UnitMapper}
     */
    UnitMapper INSTANCE = Mappers.getMapper(UnitMapper.class);
    
    /**
     * Mapuje listę obiektów encji klasy {@link Unit} na listę obiektów DTO klasy {@link BasicUnitDto}
     * @param units     lista obiektów encji
     * @return          lista obiektów DTO
     */
    List<BasicUnitDto> unitsToDto(List<Unit> units);
}
