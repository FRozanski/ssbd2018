/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.mapper;

import java.util.List;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.dto.BasicAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.dto.EditableAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.dto.FullAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.dto.NewAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 * Interfejs umożliwiająca mapowanie obiektów związanych typem {@link Account}.
 * @author michal
 */
@Mapper
@DecoratedWith(AccountMapperDecorator.class)
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    /**
     * Metoda umożliwiająca mapowanie listy obiektów typu {@link Account} na listę obiektów typu {@link FullAccountDto}
     * @param accounts
     * @return lista obiektów typu {@link FullAccountDto}
     */
    List<FullAccountDto> accountsToEditableDTO(List<Account> accounts);

    /**
     * Metoda umożliwiająca mapowanie obiektu typu {@link Account} na obiekt typu {@link EditableAccountDto}
     * @param account
     * @return obiekt typu {@link EditableAccountDto}
     */
    EditableAccountDto accountToEditableDto(Account account);

    /**
     * Metoda umożliwiająca mapowanie obiektu typu {@link EditableAccountDto} na obiekt typu {@link Account}
     * @param accountDto
     * @param account
     * @return obiekt typu {@link Account}
     */
    @InheritInverseConfiguration
    Account accountDtoToAccount(EditableAccountDto accountDto, @MappingTarget Account account);

    /**
     * Metoda umożliwiająca mapowanie obiektu typu {@link BasicAccountDto} na obiekt typu {@link Account}
     * @param accountDto
     * @param account
     * @return obiekt typu {@link Account}
     */
    @InheritInverseConfiguration
    Account basicAccountDtoToAccount(BasicAccountDto accountDto, @MappingTarget Account account);
    
    /**
     * Metoda umożliwiająca mapowanie obiektu typu {@link NewAccountDto} na obiekt typu {@link Account}
     * @param accountDto
     * @param account
     * @return obiekt typu {@link Account}
     */
    @InheritInverseConfiguration
    @Mapping(source = "firstPassword", target = "password")
    @IterableMapping(dateFormat = "dd.MM.yyyy hh:mm:ss")
    Account newAccountDtoToAccount(NewAccountDto accountDto, @MappingTarget Account account);

}
