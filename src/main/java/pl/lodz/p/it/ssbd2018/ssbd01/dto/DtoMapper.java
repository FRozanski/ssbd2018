/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.dto;


import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author dlange
 */
public class DtoMapper {
    
    public static AccountDto mapAccount(Account account) {
        return new AccountDto(
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
}