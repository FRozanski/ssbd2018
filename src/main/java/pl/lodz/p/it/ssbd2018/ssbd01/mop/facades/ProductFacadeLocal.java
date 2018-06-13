/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;

/**
 *
 * @author fifi
 */
@Local
public interface ProductFacadeLocal {

    void create(Product product) throws AppBaseException;

    void edit(Product product) throws AppBaseException;

    Product find(Object id) throws AppBaseException;

    List<Product> findAll();

    List<Product> findRange(int[] range);
    
    List<Product> findByOwnerLogin(String login) throws AppBaseException;

    int count();
    
}
