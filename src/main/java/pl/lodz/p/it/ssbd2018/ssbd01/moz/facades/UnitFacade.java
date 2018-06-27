/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.facades;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Unit;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop.UnitNotFoundException;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.facades.UnitFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.shared_facades.AbstractFacadeBase;

/**
 * Klasa zapewnia możliwość operowania na obiektach encji typu {@link Unit} 
 * @author fifi
 */
@Stateless(name = "UnitMOZ")
public class UnitFacade extends AbstractFacadeBase<Unit> implements UnitFacadeLocal {

    @PersistenceContext(unitName = "ssbd01mozDS")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UnitFacade() {
        super(Unit.class);
    }
    
    @Override
    @RolesAllowed("findUnit")
    public Unit find(Object id) throws AppBaseException {
        Unit unit = super.find(id);
        if (unit == null) {
            throw new UnitNotFoundException("unit_not_found");
        }
        return unit;
    }
}
