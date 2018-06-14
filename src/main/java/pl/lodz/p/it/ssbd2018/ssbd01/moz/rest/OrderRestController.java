/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.rest;

import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.ConstraintException;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.BasicShippingMethodDto;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.managers.OrderManagerLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.mapper.ShippingMethodMapper;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.SUCCESS;

/**
 * Klasa reprezentująca serwis RESTowy, odpowiedzialna za zarządanie zamówieniami i metodami przesyłki
 * @author Filip
 */
@Path("order")
public class OrderRestController {
    
    @EJB
    OrderManagerLocal orderManager;
  
    /**
     * Dodawanie nowych metod wysyłki
     * @param shippingMethodDto obiekt dto metody przesyłki
     * @return odpowiedź w formacie JSON zawierającego rezultat wykonanej akcji
     */
    @POST
    @Path("addShippingMethod")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addShippingMethod(BasicShippingMethodDto shippingMethodDto) {
        try {
            ShippingMethod shippingMethod = new ShippingMethod();
            ShippingMethodMapper.INSTANCE.basicShippingMethodDtoToShippingMethod(shippingMethodDto, shippingMethod);
            shippingMethod.setActive(true);
            validateConstraints(shippingMethod);
            orderManager.addShippingMethod(shippingMethod);
            return Response.status(Response.Status.OK)
                    .entity(new WebErrorInfo("200", SUCCESS))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
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
