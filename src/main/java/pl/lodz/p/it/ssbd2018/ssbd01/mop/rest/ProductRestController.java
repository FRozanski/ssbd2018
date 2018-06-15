/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.NewProductDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.managers.ProductManagerLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.mapper.ProductMapper;

/**
 *
 * @author michal
 */
@Path("product")
public class ProductRestController {

    @EJB
    ProductManagerLocal productManager;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts() {
        List<BasicProductDto> allProductsDto = ProductMapper.INSTANCE.productsToDTO(productManager.getAllProducts());
        return Response.status(Response.Status.OK)
                .entity(allProductsDto)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @GET
    @Path("myProducts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyProducts(@Context HttpServletRequest servletRequest) {
        try {
            String login = getUserLogin(servletRequest);
            List<BasicProductDto> allProductsDto = ProductMapper.INSTANCE.productsToDTO(productManager.getMyProducts(login));
            return Response.status(Response.Status.OK)
                    .entity(allProductsDto)
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
    @Path("activeProducts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllActiveProductWithActiveCategory() {
        try {
            List<BasicProductDto> allProductsDto = ProductMapper.INSTANCE.productsToDTO(productManager.getAllActiveProducts());
            return Response.status(Response.Status.OK)
                    .entity(allProductsDto)
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
    @Path("addProduct")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Response addProduct(NewProductDto newProduct, @Context HttpServletRequest servletRequest) {
        try {
        String login = getUserLogin(servletRequest);
        productManager.addProductByAccountLogin(newProduct, login);
        return Response.status(Response.Status.CREATED)
                .entity(new WebErrorInfo("400", "Super")).type(MediaType.APPLICATION_JSON)
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
