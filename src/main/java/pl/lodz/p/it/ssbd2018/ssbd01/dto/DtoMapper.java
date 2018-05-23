/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.dto;

import java.util.ArrayList;
import java.util.List;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccountOptimisticException;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.rest.IdChanger;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.CloneUtils;

/**
 *
 * @author dlange
 * @author agkan
 */
public class DtoMapper {

    public static AccountDto mapAccount(Account account) {
        return new AccountDto(
                account.getId(),
                account.getLogin(),
                account.getNumberOfProducts(),
                account.getNumberOfOrders(),
                account.getNumberOfLogins(),
                account.getName(),
                account.getSurname(),
                account.getEmail(),
                account.getPhone(),
                account.getStreet(),
                account.getStreetNumber(),
                account.getFlatNumber(),
                account.getPostalCode(),
                account.getCity(),
                account.getCountry(),
                account.getConfirm(),
                account.getActive(),
                account.isUsed(),
                account.getAccessLevelCollection()
        );
    }

    public static EditAccountDto mapToEditAccount(Account account, IdChanger idChanger) {
        EditAccountDto editableAccount = new EditAccountDto();
        editableAccount.setId(idChanger.addId(account.getId()));
        editableAccount.setCity(account.getCity());
        editableAccount.setName(account.getName());
        editableAccount.setSurname(account.getSurname());
        editableAccount.setEmail(account.getEmail());
        editableAccount.setPhone(account.getPhone());
        editableAccount.setStreet(account.getStreet());
        editableAccount.setSurname(account.getSurname());
        editableAccount.setStreetNumber(account.getStreetNumber());
        editableAccount.setFlatNumber(account.getFlatNumber());
        editableAccount.setPostalCode(account.getPostalCode());
        editableAccount.setCountry(account.getCountry());
        editableAccount.setVersion(account.getVersion());
        return editableAccount;
    }

    public static EditAccountDto mapToEditOwnAccount(Account account) {
        EditAccountDto editableAccount = new EditAccountDto();
        editableAccount.setCity(account.getCity());
        editableAccount.setName(account.getName());
        editableAccount.setSurname(account.getSurname());
        editableAccount.setEmail(account.getEmail());
        editableAccount.setPhone(account.getPhone());
        editableAccount.setStreet(account.getStreet());
        editableAccount.setSurname(account.getSurname());
        editableAccount.setStreetNumber(account.getStreetNumber());
        editableAccount.setFlatNumber(account.getFlatNumber());
        editableAccount.setPostalCode(account.getPostalCode());
        editableAccount.setCountry(account.getCountry());
        editableAccount.setVersion(account.getVersion());
        return editableAccount;
    }

    public static Account mapEditAccountDto(EditAccountDto accountDto, Account accountToEdit) throws AccountOptimisticException {
        accountToEdit.setName(accountDto.getName());
        accountToEdit.setSurname(accountDto.getSurname());
        accountToEdit.setEmail(accountDto.getEmail());
        accountToEdit.setPhone(accountDto.getPhone());
        accountToEdit.setStreet(accountDto.getStreet());
        accountToEdit.setStreetNumber(accountDto.getStreetNumber());
        accountToEdit.setFlatNumber(accountDto.getFlatNumber());
        accountToEdit.setPostalCode(accountDto.getPostalCode());
        accountToEdit.setCity(accountDto.getCity());
        accountToEdit.setCountry(accountDto.getCountry());
        accountToEdit.setVersion(accountDto.getVersion());
        return accountToEdit;
    }

    public static Account mapAccountDto(AccountDto accountDto, Account accountToEdit) {
        Account account = (Account) CloneUtils.deepCloneThroughSerialization(accountToEdit);
        account.setLogin(accountDto.getLogin());
        account.setNumberOfProducts(accountDto.getNumberOfProducts());
        account.setNumberOfOrders(accountDto.getNumberOfOrders());
        account.setNumberOfLogins(accountDto.getNumberOfLogins());
        account.setName(accountDto.getName());
        account.setSurname(accountDto.getSurname());
        account.setEmail(accountDto.getEmail());
        account.setPhone(accountDto.getPhone());
        account.setStreet(accountDto.getStreet());
        account.setStreetNumber(accountDto.getStreetNumber());
        account.setFlatNumber(accountDto.getFlatNumber());
        account.setPostalCode(accountDto.getPostalCode());
        account.setCity(accountDto.getCity());
        account.setCountry(accountDto.getCountry());
        account.setConfirm(accountDto.isConfirm());
        account.setActive(accountDto.isActive());
        account.setUsed(accountDto.isUsed());
        return account;
    }

    public static List<AccessLevelDto> mapAccessLevel(List<AccessLevel> accessLevel, IdChanger idChanger) {
        List<AccessLevelDto> dto = new ArrayList<>();
        for (int i = 0; i < accessLevel.size(); i++) {
            AccessLevelDto acDto = new AccessLevelDto();
            acDto.setId(idChanger.addId(accessLevel.get(i).getId()));
            acDto.setLevel(accessLevel.get(i).getLevel());
            dto.add(acDto);
        }
        return dto;
    }
}
