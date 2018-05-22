/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.rest;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.managers.AccountManagerLocal;

/**
 *
 * @author agkan
 */
@Path("registrationConfirm")
public class ConfirmAccountWebService {

    @EJB
    AccountManagerLocal accountManagerLocal;

    @GET
    public Response confirmAccount(@QueryParam("token") String token) {
        try {
            Account account = accountManagerLocal.getAccountByToken(token); //@PermitAll ?
            accountManagerLocal.confirmAccount(account);            
            return Response.ok().build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("404", ex.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
    
}
