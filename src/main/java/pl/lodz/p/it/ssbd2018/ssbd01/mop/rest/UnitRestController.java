/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicUnitDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.managers.ProductManagerLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.mapper.UnitMapper;

/**
 *
 * @author agkan
 */
@Path("unit")
public class UnitRestController {

    @EJB
    ProductManagerLocal productManager;
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUnits() {
        List<BasicUnitDto> allUnitDtos = UnitMapper.INSTANCE.unitsToDto(productManager.getAllUnits());
        return Response.status(Response.Status.OK)
                .entity(allUnitDtos)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
