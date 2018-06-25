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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pl.lodz.p.it.ssbd2018.ssbd01.entities.Order1;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.OrderStatus;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.UserUnauthorized;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.UserAlreadyLogoutException;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.BasicOrderStatusDto;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.ListOrderDto;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.OrderStatusUpdateDto;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.dto.MakeOrderDto;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.managers.OrderManagerLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.moz.mapper.OrderMapper;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.SUCCESS;

/**
 * Klasa reprezentująca serwis RESTowy, odpowiedzialna za zarządanie
 * zamówieniami i metodami przesyłki
 *
 * @author Filip
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
    @Path("orderStatus")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrderStatus() {
        List<BasicOrderStatusDto> allOrderStatusDto = OrderMapper.INSTANCE.statusToDto(orderManager.getAllOrderStatus());
        return Response.status(Response.Status.OK)
                .entity(allOrderStatusDto)
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
    
    @POST
    @Path("makeOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeOrder(MakeOrderDto orderDto, @Context HttpServletRequest servletRequest) {
        try {
            String login = getUserLogin(servletRequest);
            orderManager.makeOrder(orderDto.getProductId(), orderDto.getQty(), orderDto.getShippingId(), login);
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
     * Metoda umożliwiająca edycję stanu zamówienia.
     * @param orderStatusUpdateDto typu {@Link OrderStatusUpdateDto}
     * @param servletRequest typu {@Link HttpServletRequest} 
     * @throws AppBaseException 
     */
    
    @PUT
    @Path("updateOrderStatus")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrderStatus(OrderStatusUpdateDto orderStatusUpdateDto, @Context HttpServletRequest servletRequest) {
        try {
                        
            Order1 order = orderManager.getOrder1ById(orderStatusUpdateDto.getOrderId());
            OrderStatus orderStatus = orderManager.getOrderStatusById(orderStatusUpdateDto.getStatusId());

            checkIfUserIsSeller(order, servletRequest);
             
            orderManager.setOrderStatus(order, orderStatus);

            return Response.status(Response.Status.OK)
                    .entity(new WebErrorInfo("200", ErrorCodes.SUCCESS))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            
        } catch (AppBaseException e) {
            
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", e.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            
        } 

    }

    private String getUserLogin(HttpServletRequest servletRequest) throws AppBaseException {
        if (servletRequest.getUserPrincipal() == null) {
            throw new UserAlreadyLogoutException("user_already_logout_error");
        }
        return servletRequest.getUserPrincipal().getName();
    }

    private void checkIfUserIsSeller(Order1 order, HttpServletRequest servletRequest) throws AppBaseException {
        if (!orderManager.getAllBySeller(getUserLogin(servletRequest)).contains(order)) {
            throw new UserUnauthorized("user_is_not_a_seller");
        }
    }
}
