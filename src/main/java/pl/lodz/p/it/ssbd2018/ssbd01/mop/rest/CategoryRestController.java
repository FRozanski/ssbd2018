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
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicCategoryDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.managers.CategoryManagerLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.mapper.CategoryMapper;

/**
 * Klasa REST API, odpowiedzialna za zarządanie kategoriami produktów
 * @author Filip
 */
@Path("category")
public class CategoryRestController {
    
    @EJB
    CategoryManagerLocal categoryManager;
    
    /**
     * Metoda REST API służąca do pobierania wszystkich kategorii produktów
     * @return JSON zawierający listę produktów typu {@link BasicCategoryDto}
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategories() {
        List<BasicCategoryDto> allCategoriesDto = CategoryMapper.INSTANCE.categoriesToDTO(categoryManager.getAllCategories());
        return Response.status(Response.Status.OK)
                .entity(allCategoriesDto)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
