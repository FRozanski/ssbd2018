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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.AccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.DtoMapper;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.endpoints.MOKEndpointLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author dlange
 * @author agkan
 */

@Path("account")
public class AccountWebService {
  
    @EJB
    MOKEndpointLocal mokEndpointLocal;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHello() {
        List<AccountDto> accounts = new ArrayList<AccountDto>();
        
        for(Account account: mokEndpointLocal.getAllAccounts()) 
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
            AccountDto accountDto = DtoMapper.mapAccount(mokEndpointLocal.getAccountById(Integer.valueOf(accountId)));    
            return Response.ok(accountDto).build();                
        } catch(NumberFormatException ex) {
            Logger.getLogger(AccountWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.noContent().build();
        }
    }    
    
    @PUT
    @Path("{accountId}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response updateAccount(@PathParam("accountId") String accountId, String textPlain) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            AccountDto accountDto = mapper.readValue(textPlain, AccountDto.class);
            Account account = DtoMapper.mapAccountDto(accountDto);
            mokEndpointLocal.saveAccountAfterEdit(account);
            return Response.ok(accountDto).build();
        } catch (IOException ex) {            
            Logger.getLogger(AccountWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.noContent().build();
        }
    }
}