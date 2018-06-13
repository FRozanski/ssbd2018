/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.dto.OtherPasswordDto;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author michal
 */
@Mapper
public interface PasswordMapper {

    PasswordMapper INSTANCE = Mappers.getMapper(PasswordMapper.class);

    @InheritInverseConfiguration
    @Mapping(source = "firstPassword", target = "password")
    Account newPasswordDtoToAccount(OtherPasswordDto otherPasswordDto, @MappingTarget Account account);
}
