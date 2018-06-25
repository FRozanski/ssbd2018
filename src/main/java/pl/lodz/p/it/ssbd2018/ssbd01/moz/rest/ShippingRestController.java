/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.rest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.UserUnauthorized;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz.ConstraintException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.moz.ShippingMethodPriceException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.UserAlreadyLogoutException;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.BasicShippingMethodDto;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.managers.ShippingMethodManagerLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.mapper.ShippingMethodMapper;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.SUCCESS;

/**
 * Klasa REST API, odpowiedzialna za zarządanie metodami przesyłki
 * @author Filip
 */
@Path("shipping")
public class ShippingRestController {
    
    @EJB
    ShippingMethodManagerLocal shippingManager;
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllShippingMethods() {
        List<BasicShippingMethodDto> allProductsDto = ShippingMethodMapper.INSTANCE.shippingMethodToBasicDto(shippingManager.getAllMethods());
        return Response.status(Response.Status.OK)
                .entity(allProductsDto)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
    /**
     * Dodawanie nowych metod wysyłki
     * @param shippingMethodDto obiekt dto metody przesyłki
     * @return odpowiedź w formacie JSON zawierającego rezultat wykonanej akcji
     */
    @POST
    @Path("addShippingMethod")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addShippingMethod(BasicShippingMethodDto shippingMethodDto, @Context HttpServletRequest servletRequest) {        
        try {
            String login = getUserLogin(servletRequest);
            validateShippingMethodPrice(shippingMethodDto);
            ShippingMethod shippingMethod = new ShippingMethod();
            ShippingMethodMapper.INSTANCE.basicShippingMethodDtoToShippingMethod(shippingMethodDto, shippingMethod);
            shippingMethod.setActive(true);
            shippingManager.addShippingMethod(shippingMethod, login);
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
    
    /**
     * Metoda REST API służąca do aktywacji metody wysyłki
     * @param shippingMethodId  identyfikator metody wysyłki w bazie danych
     * @return                  meta-dane informujące o sukcesie lub niepowodzeniu wykonania metody
     */
    @PUT
    @Path("activate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateShippingMethod(@QueryParam("methodId") long shippingMethodId) {
        try {
            shippingManager.activateShippingMethod(shippingMethodId);
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
    
    /**
     * Metoda REST API służąca do aktywacji metody wysyłki
     * @param shippingMethodId  identyfikator metody wysyłki w bazie danych
     * @return                  meta-dane informujące o sukcesie lub niepowodzeniu wykonania metody
     */
    @PUT
    @Path("deactivate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deactivateShippingMethod(@QueryParam("methodId") long shippingMethodId) {
        try {
            shippingManager.deactivateShippingMethod(shippingMethodId);
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
    
    private void validateShippingMethodPrice(BasicShippingMethodDto dto) throws AppBaseException {
        try {
            BigDecimal price = new BigDecimal(dto.getPrice());
        } catch(NumberFormatException e) {
            throw new ShippingMethodPriceException("shipping_method_price_exception");
        }
    }
    
    private String getUserLogin(HttpServletRequest servletRequest) throws UserUnauthorized, UserAlreadyLogoutException {
        if (servletRequest.getUserPrincipal() == null) {
            throw new UserAlreadyLogoutException("user_already_logout_error");
        }
        return servletRequest.getUserPrincipal().getName();
    }
}
