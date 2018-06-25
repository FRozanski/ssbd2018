/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.managers;

import java.util.List;
import java.util.Set;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz.ConstraintException;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.facades.AccountFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.facades.ShippingMethodFacadeLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes;

/**
 * Klasa obsługująca zarządzanie obiektami typu {@link ShippingMethod}
 * @author michal
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class ShippingMethodManager implements ShippingMethodManagerLocal {
    
    @EJB
    ShippingMethodFacadeLocal shippingFacade;
    
    @EJB(beanName = "AccountMOZ")
    private AccountFacadeLocal accountFacade;
    
    @Override
    @RolesAllowed("getAllMethods")
    public List<ShippingMethod> getAllMethods() {
        return shippingFacade.findAll();
    }
    
    @Override
    @RolesAllowed("addShippingMethod")
    public void addShippingMethod(ShippingMethod shippingMethod, String login) throws AppBaseException{
        Account account = accountFacade.findByLogin(login);
        shippingMethod.setCreatedBy(account);
        validateConstraints(shippingMethod);
        shippingFacade.create(shippingMethod);
    }

    @Override
    @RolesAllowed("activateShippingMethod")
    public void activateShippingMethod(long shippingMethodId) throws AppBaseException {
        ShippingMethod shippingMethod = shippingFacade.find(shippingMethodId);
        shippingMethod.setActive(true);
        shippingFacade.edit(shippingMethod);
    }

    @Override
    @RolesAllowed("deactivateShippingMethod")
    public void deactivateShippingMethod(long shippingMethodId) throws AppBaseException {
        ShippingMethod shippingMethod = shippingFacade.find(shippingMethodId);
        shippingMethod.setActive(false);
        shippingFacade.edit(shippingMethod);
    }
    
    private static void validateConstraints(ShippingMethod shippingMethod) throws AppBaseException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ShippingMethod>> constraintViolations = validator.validate(shippingMethod);
        List<String> errors = new ErrorCodes().getAllErrors();

        if (constraintViolations.size() > 0) {
            for (int i = 0; i < errors.size(); i++) {
                for (ConstraintViolation<ShippingMethod> temp : constraintViolations) {
                    if (errors.get(i).equals(temp.getMessage())) {
                        throw new ConstraintException("constraint_error", errors.get(i));
                    }
                }
            }
        }
    }
    
}
