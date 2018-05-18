/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.dto;


import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
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
                account.getCountry()//,
//                account.getConfirm(),
//                account.getActive(),
//                account.getOrder1Collection(),
//                account.getOrder1Collection1(),
//                account.getProductCollection(),
//                account.getToken(),
//                account.getExpiryDate(),
//                account.isUsed()                
        );
    }
    
    public static Account mapAccountDto(AccountDto accountDto, Account accountToEdit) {
        Account account = (Account) CloneUtils.deepCloneThroughSerialization(accountToEdit);
//        account.setLogin(accountDto.getLogin());
//        account.setNumberOfProducts(accountDto.getNumberOfProducts());
//        account.setNumberOfOrders(accountDto.getNumberOfOrders());
//        account.setNumberOfLogins(accountDto.getNumberOfLogins());
//        account.setName(accountDto.getName());
//        account.setSurname(accountDto.getSurname());
//        account.setEmail(accountDto.getEmail());
//        account.setPhone(accountDto.getPhone());
//        account.setStreet(accountDto.getStreet());
//        account.setStreetNumber(accountDto.getStreetNumber());
//        account.setFlatNumber(accountDto.getFlatNumber());
//        account.setPostalCode(accountDto.getPostalCode());
//        account.setCity(accountDto.getCity());
        account.setCountry(accountDto.getCountry());
//        account.setConfirm(accountDto.isConfirm());   
//        account.setActive(accountDto.isActive());
//        account.setOrder1Collection(accountDto.getOrder1Collection());
//        account.setOrder1Collection1(accountDto.getOrder1Collection1());
//        account.setProductCollection(accountDto.getProductCollection());
//        account.setUsed(accountDto.isUsed());
        return account;
    }
    
    public static AccessLevelDto mapAccessLevel(AccessLevel accessLevel) {
        return new AccessLevelDto(
                accessLevel.getId(),
                accessLevel.getLevel(),
                accessLevel.getActive()
        );
    }
}
