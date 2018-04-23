/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.facades;

import java.util.List;
import javax.ejb.Local;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;

/**
 *
 * @author fifi
 */
@Local
public interface CategoryFacadeLocal {

    void create(Category category);

    void edit(Category category);

    Category find(Object id);

    List<Category> findAll();

    List<Category> findRange(int[] range);

    int count();
    
}
