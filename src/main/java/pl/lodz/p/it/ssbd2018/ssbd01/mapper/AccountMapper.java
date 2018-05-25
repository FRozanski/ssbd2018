/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mapper;

import java.util.List;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.EditableAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.FullAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.NewAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author michal
 */
@Mapper
@DecoratedWith(AccountMapperDecorator.class)
public interface AccountMapper {
    
    AccountMapper INSTANCE = Mappers.getMapper( AccountMapper.class );
    
    List<FullAccountDto> accountsToEditableDTO(List<Account> accounts);
    
    EditableAccountDto accountToEditableDto (Account account);
    
    @InheritInverseConfiguration
    Account accountDtoToAccount(EditableAccountDto accountDto, @MappingTarget Account account);
    
    @InheritInverseConfiguration
    @Mapping(source = "firstPassword", target = "password")
    Account newAccountDtoToAccount(NewAccountDto accountDto, @MappingTarget Account account); 
    
//    FullAccountDto accountToFullDTO (Account account);
    
}
