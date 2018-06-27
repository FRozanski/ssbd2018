/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.dto.AccessLevelDto;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;

/**
 * Interfejs umożliwiający mapowanie poziomów dostępu typu {@link AccessLevel} na obiekty typu {@link AccessLevelDto}
 * @author michal
 */
@Mapper
public interface AccessLevelMapper {
    
    AccessLevelMapper INSTANCE = Mappers.getMapper( AccessLevelMapper.class );
    
    /**
     * Metoda mapująca listę poziomów dostępu typu {@link AccessLevel} na listę obiektów typu {@link AccessLevelDto}.
     * @param accessLevels
     * @return listę obiektów typu {@link AccessLevelDto}
     */
    List<AccessLevelDto> accessLevelToDto(List<AccessLevel> accessLevels);
    
}
