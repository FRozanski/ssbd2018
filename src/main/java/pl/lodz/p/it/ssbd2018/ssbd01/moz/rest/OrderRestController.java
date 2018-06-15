/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.moz.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.UserUnauthorized;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.UserAlreadyLogoutException;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicProductDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.mapper.ProductMapper;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.ListOrderDto;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.managers.OrderManagerLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.mapper.OrderMapper;

/**
 *
 * @author michal
 */
@Path("order")
public class OrderRestController {
    
    @EJB
    OrderManagerLocal orderManager;
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders() {
        List<ListOrderDto> allOrdersDto = OrderMapper.INSTANCE.orderToFullListDto(orderManager.getAllOrders());
        return Response.status(Response.Status.OK)
                .entity(allOrdersDto)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
    @GET
    @Path("myBought")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyBought(@Context HttpServletRequest servletRequest) {   
        try {
            String login = getUserLogin(servletRequest);
            List<ListOrderDto> allOrdersDto = OrderMapper.INSTANCE.orderToFullListDto(orderManager.getAllByBuyer(login));
            return Response.status(Response.Status.OK)
                .entity(allOrdersDto)
                .type(MediaType.APPLICATION_JSON)
                .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }   
    }
    
    @GET
    @Path("mySold")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMySold(@Context HttpServletRequest servletRequest) {   
        try {
            String login = getUserLogin(servletRequest);
            List<ListOrderDto> allOrdersDto = OrderMapper.INSTANCE.orderToFullListDto(orderManager.getAllBySeller(login));
            return Response.status(Response.Status.OK)
                .entity(allOrdersDto)
                .type(MediaType.APPLICATION_JSON)
                .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }   
    }
    
    private String getUserLogin(HttpServletRequest servletRequest) throws UserUnauthorized, UserAlreadyLogoutException {
        if (servletRequest.getUserPrincipal() == null) {
            throw new UserAlreadyLogoutException("user_already_logout_error");
        }
        return servletRequest.getUserPrincipal().getName();
    } 
    
}
