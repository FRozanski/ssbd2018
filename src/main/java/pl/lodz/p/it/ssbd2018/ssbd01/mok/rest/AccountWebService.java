/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
import org.json.simple.JSONObject;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.AccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.DtoMapper;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.EditAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.NewAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.PassDto;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccountException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccountNotFoundException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.AccountOptimisticException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.EmailNotUniqueException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.LoginNotUniqueException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.PasswordNotMatch;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.PhoneNotUniqueException;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.managers.AccountManagerLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.EntitiesErrorCodes;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.EntitiesErrorCodes.*;

/**
 *
 * @author dlange
 * @author agkan
 * @author michalmalec
 */
@Path("account")
public class AccountWebService {

    @EJB
    IdChanger idChanger;

    @EJB
    AccountManagerLocal accountManagerLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
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
            EditAccountDto accountDto = DtoMapper.mapToEditAccount(accountToEdit, idChanger);
            return Response.ok(accountDto).build();
        } catch (AccountException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @GET
    @Path("myAccountToEdit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyAccountToEdit(@Context HttpServletRequest servletRequest) {
        if (servletRequest.getUserPrincipal() == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        String login = servletRequest.getUserPrincipal().getName();
        try {
            Account accountToEdit = accountManagerLocal.getAccountToEdit(accountManagerLocal.getMyAccountByLogin(login));
            EditAccountDto accountDto = DtoMapper.mapToEditAccount(accountToEdit, idChanger);
            return Response.ok(accountDto).build();
        } catch (AccountException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("updateMyAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMyAccount(EditAccountDto accountDto, @Context HttpServletRequest servletRequest) {
        if (servletRequest.getUserPrincipal() == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        try {
            String login = servletRequest.getUserPrincipal().getName();
            Account accountToEdit = accountManagerLocal.getMyAccountByLogin(login);
            if (!idChanger.getId(accountDto.getId()).equals(accountToEdit.getId())) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNAUTHORIZED))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            }
            Account account = DtoMapper.mapEditAccountDto(accountDto, accountToEdit, idChanger);
            return valideAndEditAccount(account);
        } catch (Exception e) {
          return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("500", e.getMessage()))
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }
    

    @PUT
    @Path("updateAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAccount(EditAccountDto accountDto) {
        if (!idChanger.containsId(accountDto.getId()) || accountDto.getId() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        try {
            Account accountToEdit = accountManagerLocal.getAccountById(idChanger.getId(accountDto.getId()));
            Account account = DtoMapper.mapEditAccountDto(accountDto, accountToEdit, idChanger);
            return valideAndEditAccount(account);
        } catch (AccountOptimisticException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AccountException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @GET
    @Path("allAccessLevel")
    public Response getAccessLevel() {
        try {
            List<AccessLevel> accessLevel = accountManagerLocal.getAllAccessLevels();
            JSONObject json = new JSONObject();
            json.put("accessLevels", DtoMapper.mapAccessLevel(accessLevel, idChanger));
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(json)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @POST
    @Path("{accountId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccountAlevel(@PathParam("accountId") String accountId,
            @QueryParam("alevelId") String alevelId) {
        if (!idChanger.containsId(accountId) || !idChanger.containsId(alevelId)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new WebErrorInfo("401", UNAUTHORIZED))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        try {
            AccessLevel accessLevel = accountManagerLocal.getAccessLevelById(idChanger.getIdWithoutDelete(alevelId));
            Account account = accountManagerLocal.getAccountById(idChanger.getId(accountId));

            accountManagerLocal.addAccessLevelToAccount(accessLevel, account);
            return Response.accepted().build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

    }

    @POST
    @Path("registerAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerAccount(NewAccountDto newAccount, @Context ServletContext servletContext) {
        if (newAccount.getPassword().length() < 8 || newAccount.getPassword2().length() < 8) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", PASSWORD_LENGTH_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        if (!newAccount.getPassword().equals(newAccount.getPassword2())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", PASSWORD_DIFFERENT_ERROR))
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
            return valideAndCreateAccount(account, servletContext);
        }
    }

    @PUT
    @Path("changePassword")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changePassword(PassDto passObj, @Context HttpServletRequest servletRequest) {
        if (servletRequest.getUserPrincipal() == null) {
            return Response.status(Response.Status.NO_CONTENT)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        String login = servletRequest.getUserPrincipal().getName();

        if (passObj.getNewPassOne().length() < 8 || passObj.getNewPassTwo().length() < 8) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", PASSWORD_LENGTH_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        if (!passObj.getNewPassOne().equals(passObj.getNewPassTwo())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", PASSWORD_DIFFERENT_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        try {
            Account account = accountManagerLocal.getMyAccountByLogin(login);
            accountManagerLocal.changeYourPassword(account, passObj.getOldPass(), passObj.getNewPassOne(), passObj.getNewPassTwo());
        } catch (PasswordNotMatch ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AccountException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (NumberFormatException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        return Response.ok().build();
    }

    @PUT
    @Path("changeOthersPassword")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeOthersPassword(PassDto passObj) {
        if (!idChanger.containsId(passObj.getAccountId())) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new WebErrorInfo("401", UNAUTHORIZED))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        if (passObj.getNewPassOne().length() < 8 || passObj.getNewPassTwo().length() < 8) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", PASSWORD_LENGTH_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        if (!passObj.getNewPassOne().equals(passObj.getNewPassTwo())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", PASSWORD_DIFFERENT_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        try {
            Account account = accountManagerLocal.getAccountById(idChanger.getId(passObj.getAccountId()));
            accountManagerLocal.changeOthersPassword(account, passObj.getNewPassOne(), passObj.getNewPassTwo());
        } catch (AccountException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("lockAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response lockAccount(@QueryParam("accountId") String accountId) {
        if (!idChanger.containsId(accountId)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new WebErrorInfo("401", UNAUTHORIZED))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        try {
            accountManagerLocal.lockAccount(idChanger.getId(accountId));
        } catch (AccountNotFoundException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AccountOptimisticException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AccountException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("unlockAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response unlockAccount(@QueryParam("accountId") String accountId) {
        if (!idChanger.containsId(accountId)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new WebErrorInfo("401", UNAUTHORIZED))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        try {
            accountManagerLocal.unlockAccount(idChanger.getId(accountId));
        } catch (AccountNotFoundException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AccountOptimisticException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AccountException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("{accountId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAccountAlevel(@PathParam("accountId") String accountId,
            @QueryParam("alevelId") String alevelId) {
        if (!idChanger.containsId(accountId) || !idChanger.containsId(alevelId)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new WebErrorInfo("401", UNAUTHORIZED))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        try {
            AccessLevel accessLevel = accountManagerLocal.getAccessLevelById(idChanger.getIdWithoutDelete(alevelId));
            Account account = accountManagerLocal.getAccountById(idChanger.getId(accountId));
            accountManagerLocal.dismissAccessLevelFromAccount(accessLevel, account);
            return Response.accepted().build();
        } catch (AccountException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", UNKNOWN_ERROR))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
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

    private Response valideAndCreateAccount(Account account, ServletContext servletContext) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
        List<String> errors = new EntitiesErrorCodes().getAllErrors();

        if (constraintViolations.size() > 0) {
            for (int i = 0; i < errors.size(); i++) {
                for (ConstraintViolation<Account> temp : constraintViolations) {
                    if (errors.get(i).equals(temp.getMessage())) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(new WebErrorInfo("400", errors.get(i)))
                                .type(MediaType.APPLICATION_JSON)
                                .build();
                    }
                }
            }
        }

        try {
            accountManagerLocal.registerAccount(account, servletContext);
        } catch (LoginNotUniqueException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (EmailNotUniqueException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (PhoneNotUniqueException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (AccountException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        }
        return Response.ok().build();
    }

    private Response valideAndEditAccount(Account account) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
        List<String> errors = new EntitiesErrorCodes().getAllErrors();

        if (constraintViolations.size() > 0) {
            for (int i = 0; i < errors.size(); i++) {
                for (ConstraintViolation<Account> temp : constraintViolations) {
                    if (errors.get(i).equals(temp.getMessage())) {
                        return Response.status(Response.Status.BAD_REQUEST)
                                .entity(new WebErrorInfo("400", errors.get(i)))
                                .type(MediaType.APPLICATION_JSON)
                                .build();
                    }
                }
            }
        }

        try {
            accountManagerLocal.saveAccountAfterEdit(account);
        } catch (AccountOptimisticException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (LoginNotUniqueException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (EmailNotUniqueException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (PhoneNotUniqueException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (AccountException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        }
        return Response.ok().build();

    }

}
