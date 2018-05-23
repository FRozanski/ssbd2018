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
import javax.ejb.EJBAccessException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import org.json.simple.JSONObject;
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
import org.json.simple.JSONArray;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.AccessLevelDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.AccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.DtoMapper;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.NewAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.PassDto;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHello() {
        List<AccountDto> accounts = new ArrayList<>();

        for (Account account : accountManagerLocal.getAllAccounts()) {
            accounts.add(DtoMapper.mapAccount(account));
        }

        return Response.ok(accounts).build();
    }

    @GET
    @Path("{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountToEdit(@PathParam("accountId") String accountId) {
        try {
            Account accountToEdit = accountManagerLocal.getAccountToEdit(accountManagerLocal.getAccountById(Integer.valueOf(accountId)));
            AccountDto accountDto = DtoMapper.mapAccount(accountToEdit);
            return Response.ok(accountDto).build();
        } catch (NumberFormatException ex) {
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
        } catch (NumberFormatException ex) {
            Logger.getLogger(AccountWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.noContent().build();
        }
    }

    @GET
    @Path("myLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyLogin(@Context HttpServletRequest servletRequest) {
        if (servletRequest.getUserPrincipal() == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            JSONObject json = new JSONObject();
            json.put("login", servletRequest.getUserPrincipal().getName());

            return Response.status(Response.Status.OK)
                    .entity(json)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @GET
    @Path("myRoles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyRole(@Context HttpServletRequest servletRequest) {
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
        if (levels.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            JSONObject json = new JSONObject();
            json.put("roles", levels);

            return Response.status(Response.Status.OK)
                    .entity(json)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @GET
    @Path("myIdentity")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyIdentity(@Context HttpServletRequest servletRequest) {
        List<String> levels = new ArrayList<>();
        if (servletRequest.getUserPrincipal() == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
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
        if (levels.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            JSONObject json = new JSONObject();
            json.put("roles", levels);
            json.put("login", servletRequest.getUserPrincipal().getName());

            return Response.status(Response.Status.OK)
                    .entity(json)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
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
        } catch (NumberFormatException ex) {
            Logger.getLogger(AccountWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.noContent().build();
        }
    }

    @POST
    @Path("registerAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerAccount(NewAccountDto newAccount, @Context ServletContext servletContext) {
        if (newAccount.getPassword().length() < 8 && newAccount.getPassword2().length() < 8) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", "password_length_error"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        if (!newAccount.getPassword().equals(newAccount.getPassword2())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", "password_different_error"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            Account account = new Account();
            account.setLogin(newAccount.getLogin());
            account.setPassword(newAccount.getPassword());
            account.setName(newAccount.getName());
            account.setSurname(newAccount.getSurname());
            account.setEmail(newAccount.getEmail());
            account.setPhone(newAccount.getPhone());
            account.setStreet(newAccount.getStreet());
            account.setSurname(newAccount.getSurname());
            account.setStreetNumber(newAccount.getStreetNumber());
            account.setFlatNumber(newAccount.getFlatNumber());
            account.setPostalCode(newAccount.getPostalCode());
            account.setCity(newAccount.getCity());
            account.setCountry(newAccount.getCountry());
            accountManagerLocal.registerAccount(account, servletContext);
            return Response.ok().build();
        }
    }

    @PUT
    @Path("changePassword")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changePassword(PassDto passObj) {
        try {
            Account account = accountManagerLocal.getAccountByLogin(passObj.getLogin());
            accountManagerLocal.changeYourPassword(account, passObj.getOldPass(), passObj.getNewPassOne(), passObj.getNewPassTwo());
        } catch (Exception ex) {
            Logger.getLogger(AccountWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.noContent().build();
        }
        return Response.ok().build();
    }

    @PUT
    @Path("changeOthersPassword")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeOthersPassword(PassDto passObj) {
        try {
            Account account = accountManagerLocal.getAccountByLogin(passObj.getLogin());
            accountManagerLocal.changeOthersPassword(account, passObj.getNewPassOne(), passObj.getNewPassTwo());
        } catch (Exception ex) {
            Logger.getLogger(AccountWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.noContent().build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("lockAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response lockAccount(@QueryParam("accountId") long accountId) {
        try {
            accountManagerLocal.lockAccount(accountId);
            //FIXME - dodac podzial na wyjatki
        } catch (EJBAccessException ae) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new WebErrorInfo("401", "unauthorized_error"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AccountException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("404", e.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("unlockAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response unlockAccount(@QueryParam("accountId") long accountId) {
        try {
            accountManagerLocal.unlockAccount(accountId);
            //FIXME - dodac podzial na wyjatki
        } catch (EJBAccessException ae) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new WebErrorInfo("401", "unauthorized_error"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AccountException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("404", e.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
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
        } catch (NumberFormatException ex) {
            Logger.getLogger(AccountWebService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.noContent().build();
        }
    }
}
