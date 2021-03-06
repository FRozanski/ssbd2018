/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mop.rest;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pl.lodz.p.it.ssbd2018.ssbd01.entities.Category;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mop.CategoryNameLengthException;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.BasicCategoryDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.dto.CategoryNameDto;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.managers.CategoryManagerLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.mop.mapper.CategoryMapper;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes;

import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.SUCCESS;

/**
 * Klasa REST API, odpowiedzialna za zarządanie kategoriami produktów
 *
 * @author Filip
 */
@Path("category")
public class CategoryRestController {

    @EJB
    CategoryManagerLocal categoryManager;

    /**
     * Metoda REST API służąca do pobierania wszystkich kategorii produktów
     *
     * @return JSON zawierający listę kategorii typu {@link BasicCategoryDto}
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategories() {
        List<BasicCategoryDto> allCategoriesDto = CategoryMapper.INSTANCE
                .categoriesToDTO(categoryManager.getAllCategories());
        return Response.status(Response.Status.OK).entity(allCategoriesDto).type(MediaType.APPLICATION_JSON).build();
    }

    /**
     * Metoda REST API służąca do pobierania aktywnych kategorii produktów
     *
     * @return JSON zawierający listę kategorii typu {@link BasicCategoryDto}
     */
    @GET
    @Path("active")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActiveCategories() {
        try {
            List<BasicCategoryDto> activeCategoriesDto = CategoryMapper.INSTANCE
                    .categoriesToDTO(categoryManager.getActiveCategories());
            return Response.status(Response.Status.OK).entity(activeCategoriesDto).type(MediaType.APPLICATION_JSON).build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    /**
     * Metoda REST API służąca do aktywowania kategorii produktów podanym
     * numerze ID.
     *
     * @param categoryId identyfikator obiektu
     * @return status operacji
     */
    @PUT
    @Path("activateCategory")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateCategory(@QueryParam("categoryId") long categoryId) {
        try {
            categoryManager.activateCategory(categoryId);
            return Response.status(Response.Status.OK).entity(new WebErrorInfo("200", SUCCESS))
                    .type(MediaType.APPLICATION_JSON).build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    /**
     * Metoda REST API służąca do deaktywowania kategorii produktów podanym
     * numerze ID.
     *
     * @param categoryId identyfikator obiektu
     * @return status operacji
     */
    @PUT
    @Path("deactivateCategory")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deactivateCategory(@QueryParam("categoryId") long categoryId) {
        try {
            categoryManager.deactivateCategory(categoryId);
            return Response.status(Response.Status.OK).entity(new WebErrorInfo("200", SUCCESS))
                    .type(MediaType.APPLICATION_JSON).build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

    /**
     * Metoda REST API służąca do dodawania nowych kategorii produktów
     *
     * @param categoryNameDto
     * @return status operacji
     */
    @PUT
    @Path("addCategory")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategory(CategoryNameDto categoryNameDto) {
        try {

            Category category = new Category();
            category.setCategoryName(categoryNameDto.getCategoryName());

            validateCategoryNameLength(category);

            categoryManager.addCategory(category);

            return Response.status(Response.Status.OK).entity(new WebErrorInfo("201", ErrorCodes.SUCCESS))
                    .type(MediaType.APPLICATION_JSON).build();

        } catch (AppBaseException e) {

            return Response.status(Response.Status.BAD_REQUEST).entity(new WebErrorInfo("400", e.getCode()))
                    .type(MediaType.APPLICATION_JSON).build();

        }
    }

    /**
     * Metoda Rpomocnicza, służy do walidacji nazwy kategorii produktów przy
     * pomocy BeanValidation.
     *
     * @param category
     */
    private void validateCategoryNameLength(Category category) throws CategoryNameLengthException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Category>> constraintViolations = validator.validate(category);

        for (ConstraintViolation<Category> cv : constraintViolations) {
            if (cv.getMessage().equals(ErrorCodes.CATEGORY_LENGTH_ERROR)) {
                throw new CategoryNameLengthException(ErrorCodes.CATEGORY_LENGTH_ERROR);
            }
        }
    }
}
