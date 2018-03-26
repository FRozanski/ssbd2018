/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.entity.PersonalData;

/**
 *
 * @author Dominik Lange
 */
@Stateless
public class PersonalDataFacade extends AbstractFacade<PersonalData> {

    @PersistenceContext(unitName = "ssbd01mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonalDataFacade() {
        super(PersonalData.class);
    }
    
}
