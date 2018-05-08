/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.VeryficationToken;

/**
 *
 * @author agkan
 */
@Local
public interface VeryficationTokenFacadeLocal {

    void create(VeryficationToken veryficationToken);

    void edit(VeryficationToken veryficationToken);

    void remove(VeryficationToken veryficationToken);

    VeryficationToken find(Object id);

    List<VeryficationToken> findAll();

    List<VeryficationToken> findRange(int[] range);

    int count();
    
}