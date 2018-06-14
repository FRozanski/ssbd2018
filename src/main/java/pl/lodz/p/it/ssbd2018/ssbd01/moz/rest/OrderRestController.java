/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ShippingMethod;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.BasicShippingMethodDto;

/**
 *
 * @author Filip
 */
@Path("order")
public class OrderRestController {
    
    @POST
    @Path("addShippingMethod")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addShippingMethod(BasicShippingMethodDto shippingMethodDto) {
        try {
            ShippingMethod shippingMethod = new ShippingMethod();
        }
        
    }
    
    
}
