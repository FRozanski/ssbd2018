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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.UserUnauthorized;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.UserAlreadyLogoutException;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.ListProductDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.EditProductDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.NewProductDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.managers.ProductManagerLocal;
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

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts() {
        List<ListProductDto> allProductsDto = ProductMapper.INSTANCE.productsToDTO(productManager.getAllProducts());
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
            List<ListProductDto> allProductsDto = ProductMapper.INSTANCE.productsToDTO(productManager.getMyProducts(login));
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
            List<ListProductDto> allProductsDto = ProductMapper.INSTANCE.productsToDTO(productManager.getAllActiveProducts());
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
    
    /**
     * Metoda REST API służąca do pobierania produktu do edycji
     * @param productId         id produktu
     * @param servletRequest    obiekt przechowujący informacje o żądaniu wysłanym przez klienta
     * @return                  meta-dane informujące o sukcesie lub niepowodzeniu wykonania metody
     */
    @GET
    @Path("productToEdit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response productToEdit(@QueryParam("productId") long productId, @Context HttpServletRequest servletRequest) {
        try {
            String login = getUserLogin(servletRequest);
            EditProductDto editProductDto = ProductMapper.INSTANCE.editableProductToDto(productManager.getProductByIdAndLogin(login, productId));
            return Response.status(Response.Status.OK)
                    .entity(editProductDto)
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
     * Metoda REST API służąca do edycji produktu przez użytkownika
     * @param editProductDto    obiekt DTO (produkt)
     * @param servletRequest    obiekt przechowujący informacje o żądaniu wysłanym przez klienta
     * @return                  meta-dane informujące o sukcesie lub niepowodzeniu wykonania metody
     */
    @PUT
    @Path("editProduct")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editProduct(EditProductDto editProductDto, @Context HttpServletRequest servletRequest) {
        try {
            String login = getUserLogin(servletRequest);
            productManager.editProduct(editProductDto, login);
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
     * Metoda REST API służąca do dodawania nowego produktu przez użytkownika
     * @param newProduct        obiekt DTO
     * @param servletRequest    obiekt przechowujący informacje o żądaniu wysłanym przez klienta
     * @return                  meta-dane informujące o sukcesie lub niepowodzeniu wykonania metody
     */
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
                .entity(new WebErrorInfo("201", SUCCESS))
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
     * Metoda udostępniająca endpoint REST dla klienta w celu zdezaktywowania produktu o podanym numerze ID.
     * @param productId
     * @return status operacji
     */
    @PUT
    @Path("activeProduct")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response activeProduct(@QueryParam("productId") long productId, @Context HttpServletRequest servletRequest) {
        try {
            String login = getUserLogin(servletRequest);
            productManager.activeProduct(productId, login);
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
     * Metoda udostępniająca endpoint REST dla klienta w celu dezaktywowania produktu o podanym numerze ID.
     * @param productId
     * @return status operacji
     */
    @PUT
    @Path("deactiveProduct")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deactiveProduct(@QueryParam("productId") long productId, @Context HttpServletRequest servletRequest) {
        try {
            String login = getUserLogin(servletRequest);
            productManager.deactiveProduct(productId, login);
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
     * Metoda udostępniająca endpoint REST dla klienta w celu usunięcia produktu o podanym numerze ID.
     * @param productId
     * @param servletRequest
     * @return
     */
    @DELETE
    @Path("deleteProduct")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@QueryParam("productId") long productId, @Context HttpServletRequest servletRequest) {
        try {
            String login = getUserLogin(servletRequest);
            productManager.deleteProductByAccount(productId, login);
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

    private String getUserLogin(HttpServletRequest servletRequest) throws UserUnauthorized, UserAlreadyLogoutException {
        if (servletRequest.getUserPrincipal() == null) {
            throw new UserAlreadyLogoutException("user_already_logout_error");
        }
        return servletRequest.getUserPrincipal().getName();
    }
}
