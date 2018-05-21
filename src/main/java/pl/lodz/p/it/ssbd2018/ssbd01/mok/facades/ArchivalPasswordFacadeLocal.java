/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ArchivalPassword;

/**
 *
 * @author michal
 */
@Local
public interface ArchivalPasswordFacadeLocal {

    void create(ArchivalPassword archivalPassword);

    ArchivalPassword find(Object id);

    List<ArchivalPassword> findAll();

    List<ArchivalPassword> findByAccountId(Long id);

    int count();
    
}
