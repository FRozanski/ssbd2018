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
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.AccessLevelDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.AccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.DtoMapper;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.endpoints.MOKEndpointLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author dlange
 */

@Path("account")
public class AccountWebService {
  
    @EJB
    MOKEndpointLocal mOKEndpointLocal;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHello() {
        List<AccountDto> accounts = new ArrayList<AccountDto>();
        
        for(Account account: mOKEndpointLocal.getAllAccounts()) 
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
            Account accountToEdit = mOKEndpointLocal.getAccountToEdit(mOKEndpointLocal.getAccountById(Integer.valueOf(accountId)));
            AccountDto accountDto = DtoMapper.mapAccount(accountToEdit);    
            return Response.ok(accountDto).build();                
        } catch(NumberFormatException ex) {
            Logger.getLogger(AccountWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.noContent().build();
        }
    }  
    
    @GET
    @Path("accessLevel/{accessLevelId}")
    public Response getAccessLevel(@PathParam("accessLevelId") String accessLevelId) {
        try {
            AccessLevel accessLevel = mOKEndpointLocal.getAccessLevelById(Long.valueOf(accessLevelId));
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
            @QueryParam("alevelId") String alevelId, 
            String plainText) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String[] splittedTexts = plainText.split(";");
            String accountText = splittedTexts[0];
            String accessLevelText = splittedTexts[1];
            AccountDto accountDto = mapper.readValue(accountText, AccountDto.class);
            AccessLevelDto accessLevelDto = mapper.readValue(accessLevelText, AccessLevelDto.class);
            AccessLevel accessLevel = mOKEndpointLocal.getAccessLevelById(accessLevelDto.getId());
            Account account = mOKEndpointLocal.getAccountById(accountDto.getId());
            mOKEndpointLocal.addAccessLevelToAccount(accessLevel, account);
            return Response.accepted().build();
        } catch(NumberFormatException | IOException ex) {
            Logger.getLogger(AccountWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.noContent().build();            
        }
    }
}
