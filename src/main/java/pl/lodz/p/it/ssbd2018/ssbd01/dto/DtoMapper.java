/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.dto;


import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author dlange
 * @author agkan
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

    public static AccessLevelDto mapAccessLevel(AccessLevel accessLevel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
