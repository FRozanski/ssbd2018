/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.rest;

import java.util.ArrayList;
import java.util.List;
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
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.UserAlreadyLogoutException;

/**
 *
 * @author michal
 */
@Path("session")
public class SessionRestController {

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
        }
        return Response.status(Response.Status.OK)
                    .entity(json)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
    }

    protected String getUserLogin(HttpServletRequest servletRequest) throws UserAlreadyLogoutException {
        if (servletRequest.getUserPrincipal() == null) {
            throw new UserAlreadyLogoutException("user_already_logout_error");
        }
        return servletRequest.getUserPrincipal().getName();
    }

    private List<String> getUserRoles(HttpServletRequest servletRequest) {
        List<String> levels = new ArrayList<>();
        if (servletRequest.isUserInRole("ADMIN")) {
            levels.add("ADMIN");
        }
        if (servletRequest.isUserInRole("MANAGER")) {
            levels.add("MANAGER");
        }
        if (servletRequest.isUserInRole("USER")) {
            levels.add("USER");
        }
        if (servletRequest.isUserInRole("VIRTUAL")) {
            levels.add("VIRTUAL");
        }
        return levels;
    }
}
