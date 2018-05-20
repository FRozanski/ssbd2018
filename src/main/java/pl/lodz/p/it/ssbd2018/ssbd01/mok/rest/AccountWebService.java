/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.AccessLevelDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.AccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.DtoMapper;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.managers.AccountManagerLocal;

/**
 *
 * @author dlange
 * @author agkan
 */

@Path("account")
public class AccountWebService {
  
    @EJB
    AccountManagerLocal accountManagerLocal;
    
//    @Resource
//    SessionContext context;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHello() {
        List<AccountDto> accounts = new ArrayList<>();
        
        for(Account account: accountManagerLocal.getAllAccounts()) 
        {
            accounts.add(DtoMapper.mapAccount(account));
        }
         
        return Response.ok(accounts).build();
    }
    
    @GET
    @Path("{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountToEdit(@PathParam("accountId") String accountId) {        
        try {
            Account accountToEdit = accountManagerLocal.getAccountById(Long.valueOf(accountId));
            AccountDto accountDto = DtoMapper.mapAccount(accountToEdit);    
            return Response.ok(accountDto).build();                
        } catch(NumberFormatException ex) {
            Logger.getLogger(AccountWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.noContent().build();
        }
    }
    
    @GET
    @Path("current")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentlyLoggedAccount() {
        String login = "admin";//context.getCallerPrincipal().getName();
        Account account = accountManagerLocal.getAccountByLogin(login);
        AccountDto accountDto = DtoMapper.mapAccount(account);    
        return Response.ok(accountDto).build();  
    }
    
    @PUT
    @Path("{accountId}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response updateAccount(@PathParam("accountId") String accountId, String textPlain) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            AccountDto accountDto = mapper.readValue(textPlain, AccountDto.class);
            Account accountToEdit = accountManagerLocal.getAccountById(Long.valueOf(accountId));
            Account account = DtoMapper.mapAccountDto(accountDto, accountToEdit);
            accountManagerLocal.saveAccountAfterEdit(account);
            return Response.ok(accountDto).build();
        } catch (IOException ex) {            
            Logger.getLogger(AccountWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.noContent().build();
        }
    }
    
    @GET
    @Path("accessLevel/{accessLevelId}")
    public Response getAccessLevel(@PathParam("accessLevelId") String accessLevelId) {
        try {
            AccessLevel accessLevel = accountManagerLocal.getAccessLevelById(Long.valueOf(accessLevelId));
            AccessLevelDto accessLevelDto = DtoMapper.mapAccessLevel(accessLevel);
            return Response.ok(accessLevelDto).build();
        } catch(NumberFormatException ex) {
            Logger.getLogger(AccountWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.noContent().build();
        }
    }
    
    @POST
    @Path("{accountId}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response createAccountAlevel(@PathParam("accountId") String accountId, 
            @QueryParam("alevelId") String alevelId) {
        try {
            AccessLevel accessLevel = accountManagerLocal.getAccessLevelById(Long.valueOf(alevelId));
            Account account = accountManagerLocal.getAccountById(Long.valueOf(accountId));
            accountManagerLocal.addAccessLevelToAccount(accessLevel, account);
            return Response.accepted().build();
        } catch(NumberFormatException ex) {
            Logger.getLogger(AccountWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.noContent().build();            
        }
    }
    
    @POST
    @Path("registerAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerAccount(Account newAccount, @Context ServletContext servletContext) {
        accountManagerLocal.registerAccount(newAccount, servletContext);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("{accountId}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response deleteAccountAlevel(@PathParam("accountId") String accountId, 
            @QueryParam("alevelId") String alevelId) {
        try {
            AccessLevel accessLevel = accountManagerLocal.getAccessLevelById(Long.valueOf(alevelId));
            Account account = accountManagerLocal.getAccountById(Long.valueOf(accountId));
            accountManagerLocal.dismissAccessLevelFromAccount(accessLevel, account);
            return Response.accepted().build();
        } catch(NumberFormatException ex) {
            Logger.getLogger(AccountWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.noContent().build();            
        }
    }    
}
