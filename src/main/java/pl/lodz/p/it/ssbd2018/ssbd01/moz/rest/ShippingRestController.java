/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.BasicOrderShippingMethodDto;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.managers.ShippingMethodManagerLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.mapper.ShippingMethodMapper;

/**
 *
 * @author michal
 */
@Path("shipping")
public class ShippingRestController {
    
    @EJB
    ShippingMethodManagerLocal shippingManager;
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllShippingMethods() {
        List<BasicOrderShippingMethodDto> allProductsDto = ShippingMethodMapper.INSTANCE.shippingMethodToBasicDto(shippingManager.getAllMethods());
        return Response.status(Response.Status.OK)
                .entity(allProductsDto)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
