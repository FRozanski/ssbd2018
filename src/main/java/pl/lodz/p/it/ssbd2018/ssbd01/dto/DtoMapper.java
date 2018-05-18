/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.dto;


import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccountAlevel;

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
                account.getNumberOfLogins(),
                account.getNumberOfOrders(),
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
                account.getConfirm()
                
        );
    }
    
    // TODO: sensowniej zimplementować tę metodę
    public static Account mapAccountDto(AccountDto accountDto) {
        Account account = new Account();
        account.setLogin(accountDto.getLogin());
        account.setNumberOfProducts(accountDto.getNumberOfProducts());
        account.setNumberOfLogins(accountDto.getNumberOfLogins());
        account.setNumberOfOrders(accountDto.getNumberOfOrders());
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
        return account;
    }
    
    public static AccountAlevelDto mapAccountAlevel(AccountAlevel accountAlevel) {
        return new AccountAlevelDto(
                accountAlevel.getId(), 
                accountAlevel.getIdAccount(), 
                accountAlevel.getIdAlevel());
    } 
    
    public static AccessLevelDto mapAccessLevel(AccessLevel accessLevel) {
        return new AccessLevelDto(
                accessLevel.getId(),
                accessLevel.getLevel(),
                accessLevel.getActive()
        );
    }

    public static AccessLevel mapAccessLevelDto(AccessLevelDto accessLevelDto) {
        AccessLevel accessLevel = new AccessLevel();
        accessLevel.setLevel(accessLevelDto.getLevel());
        accessLevel.setActive(accessLevelDto.isActive());
        return accessLevel;
    }
}
