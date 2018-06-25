/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.InternalException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.UserAlreadyLogoutException;

/**
 * Klasa definiująca operacje możliwe do wykonania w celu pozyskania tożsamości w ramach danej sesji użytkownika.
 * @author michal
 */
@Path("session")
public class SessionRestController {
    
    private static final String CONFIG_FILE = "roles.properties";

    /**
     * Metoda udostępniająca endpoint REST w celu pozyskania loginu w danej sesji.
     * @param servletRequest
     * @return status operacji
     */
    @GET
    @Path("myLogin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMyLogin(@Context HttpServletRequest servletRequest) {
        try {
            String login = this.getUserLogin(servletRequest);
            JSONObject json = new JSONObject();
            json.put("login", login);

            return Response.status(Response.Status.OK)
                    .entity(json)
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
     * Metoda udostępniająca endpoint REST w celu pozyskania roli posiadanej w danej sesji.
     * @param servletRequest
     * @return status operacji
     */
    @GET
    @Path("myIdentity")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMyIdentity(@Context HttpServletRequest servletRequest) {
        JSONObject json = new JSONObject();
        try {
            String login = getUserLogin(servletRequest);
            List<String> levels = getUserRoles(servletRequest);
            json.put("roles", levels);
            json.put("login", login);
        } catch (UserAlreadyLogoutException ex) {
            json.put("roles", "GUEST");
            json.put("login", "GUEST");      
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return Response.status(Response.Status.OK)
                    .entity(json)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
    }

    /**
     * Metoda zwracająca login użytkownika danej sesji.
     * @param servletRequest
     * @return login typu {@link String}
     * @throws UserAlreadyLogoutException 
     */
    protected String getUserLogin(HttpServletRequest servletRequest) throws UserAlreadyLogoutException {
        if (servletRequest.getUserPrincipal() == null) {
            throw new UserAlreadyLogoutException("user_already_logout_error");
        }
        return servletRequest.getUserPrincipal().getName();
    }

    /**
     * Metoda zwracająca listę ról posiadanych przez użytkownika w danej sesji.
     * @param servletRequest
     * @return listę ról w danej sesji. 
     */
    private List<String> getUserRoles(HttpServletRequest servletRequest) throws AppBaseException {
        List<String> levels = new ArrayList<>();
        Properties properties = new Properties();
        try {
            InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream(CONFIG_FILE);
            System.out.println("InputStream is: " + inputStream);
            properties.load(inputStream);   
        } catch (FileNotFoundException ex) {
            throw new InternalException("file_not_find");
        } catch (IOException ex) {
            throw new InternalException("io_exception");
        }
        if (servletRequest.isUserInRole(properties.getProperty("role.admin"))) {
            levels.add(properties.getProperty("role.admin"));
        }
        if (servletRequest.isUserInRole(properties.getProperty("role.manager"))) {
            levels.add(properties.getProperty("role.manager"));
        }
        if (servletRequest.isUserInRole(properties.getProperty("role.user"))) {
            levels.add(properties.getProperty("role.user"));
        }
        if (servletRequest.isUserInRole(properties.getProperty("role.virtual"))) {
            levels.add(properties.getProperty("role.virtual"));
        }
        return levels;
    }
}
