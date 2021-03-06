/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.rest;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.dto.LoginDto;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.*;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.LoginException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.LogoutException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.PasswordOrUsernameException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.UserAlreadyLoginException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.UserAlreadyLogoutException;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.managers.AccountManagerLocal;

/**
 * Klasa definiująca operacje możliwe do wykonania w celu uwierzytelnienia się i zamknięcia sesji.
 * @author michal
 */
@PermitAll
@Path("/auth")
public class AuthRestController {

    @EJB
    AccountManagerLocal accountManagerLocal;

    /**
     * Metoda udostępniająca endpoint REST w celu uwierzytelnienia się w serwisie.
     * @param loginDto
     * @param httpRequest
     * @return status operacji
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginDto loginDto, @Context HttpServletRequest httpRequest) {
        try {
            httpRequest.getSession(true);
            this.validateAndLogin(loginDto, httpRequest);
            accountManagerLocal.updateLoginDateAndIp(loginDto.getUsername(), httpRequest.getRemoteAddr());
            return Response.status(Response.Status.OK)
                    .entity(new WebErrorInfo("200", SUCCESS))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new WebErrorInfo("401", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    /**
     * Metoda umostępniająca endpoint REST w celu zamknięcia sesji użytkownika.
     * @param httpRequest
     * @return status operacji
     */
    @POST
    @Path("/logout")
    public Response logout(@Context HttpServletRequest httpRequest) {
        try {
            this.validateAndLogout(httpRequest);
            return Response.status(Response.Status.OK)
                    .entity(new WebErrorInfo("200", SUCCESS))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new WebErrorInfo("401", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    /**
     * Metoda walidująca podane dane i sprawdzająca czy sesja nie jest już otwarta.
     * @param loginDto
     * @param httpRequest
     * @throws AppBaseException 
     */
    private void validateAndLogin(LoginDto loginDto, @Context HttpServletRequest httpRequest) throws AppBaseException {
        if (httpRequest.getUserPrincipal() != null) {
            throw new UserAlreadyLoginException("user_already_login");
        }
        if ("".equals(loginDto.getPassword()) || "".equals(loginDto.getUsername())) {
            throw new PasswordOrUsernameException("null_password_or_username_exception");
        }
        try {
            httpRequest.login(loginDto.getUsername(), loginDto.getPassword());
            httpRequest.getSession(true);
        } catch (ServletException ex) {
            throw new LoginException("login_error");
        }
    }

    /**
     * Metoda walidująca czy podana sesja nie została już zamknięta.
     * @param httpRequest
     * @throws AppBaseException 
     */
    private void validateAndLogout(@Context HttpServletRequest httpRequest) throws AppBaseException {
        if (httpRequest.getUserPrincipal() == null) {
            throw new UserAlreadyLogoutException("user_already_logout_error");
        }
        try {
            httpRequest.logout();
        } catch (ServletException ex) {
            throw new LogoutException("logout_error");
        }
    }
}
