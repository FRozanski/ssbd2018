/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.mapper;

import java.util.ArrayList;
import java.util.List;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.dto.FullAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccountAlevel;

/**
 * KLasa abstrakcyjna umożliwiająca mapowanie obiektów związanych z typem {@link Account}
 * @author michal
 */
public abstract class AccountMapperDecorator implements AccountMapper {

    private final AccountMapper delegate;

    public AccountMapperDecorator(AccountMapper delegate) {
        this.delegate = delegate;
    }

    /**
     * Metoda umożliwiająca mapowanie listy obiektów typu {@link Account} na listę obiektów typu {@link FullAccountDto}
     * @param accounts
     * @return lista obiektów typu {@link FullAccountDto}
     */
    @Override
    public List<FullAccountDto> accountsToEditableDTO(List<Account> accounts) {
        List<FullAccountDto> dto = delegate.accountsToEditableDTO(accounts);
        for (int i = 0; i < dto.size(); i++) {
            List<AccountAlevel> levels = new ArrayList(accounts.get(i).getAccessLevelCollection());
            for (AccountAlevel level : levels) {
                dto.get(i).getAccessLevels().add(level.getIdAlevel().getLevel());
            }
        }
        return dto;
    }

}
