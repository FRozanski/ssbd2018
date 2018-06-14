/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.BasicShippingMethodDto;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.managers.OrderManagerLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.mapper.ShippingMethodMapper;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.SUCCESS;

/**
 *
 * @author Filip
 */
@Path("order")
public class OrderRestController {
    
    @EJB
    OrderManagerLocal orderManager;
  
    
    @POST
    @Path("addShippingMethod")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addShippingMethod(BasicShippingMethodDto shippingMethodDto) {
        try {
            ShippingMethod shippingMethod = new ShippingMethod();
            ShippingMethodMapper.INSTANCE.basicShippingMethodDtoToShippingMethod(shippingMethodDto, shippingMethod);
            shippingMethod.setActive(true);
            //turaj sprawdzenie czy price >= 0
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
}
