/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.rest;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Product;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Unit;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.UserUnauthorized;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.UserAlreadyLogoutException;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.managers.AccountManagerLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicProductDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.NewProductDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.managers.ProductManagerLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.mapper.NewProductMapper;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.mapper.ProductMapper;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.SUCCESS;

/**
 *
 * @author michal
 */
@Path("product")
public class ProductRestController {

    @EJB
    ProductManagerLocal productManager;
    
    @EJB
    AccountManagerLocal accountManager;

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
    public Response addProduct(NewProductDto newProduct, @Context HttpServletRequest servletRequest) {
        try {
        Product product = new Product();
        NewProductMapper.INSTANCE.newProductDtoToProduct(newProduct, product);
        Category category = productManager.getCategoryById(newProduct.getCategoryId());
        Account owner = accountManager.getAccountById(newProduct.getOwnerId());
        Unit unit = productManager.getUnitById(newProduct.getUnitId());
        product.setCategoryId(category);
//        product.setOwnerId(owner);
        product.setUnitId(unit);
//            productManager.addMyNewProduct(product);
        return Response.status(Response.Status.CREATED)
                .entity(product).type(MediaType.APPLICATION_JSON)
                .build();
//            return Response.status(Response.Status.OK)
//                    .entity(new WebErrorInfo("200", SUCCESS))
//                    .type(MediaType.APPLICATION_JSON)
//                    .build();
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
