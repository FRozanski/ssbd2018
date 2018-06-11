/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.mok.rest;

import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
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
import pl.lodz.p.it.ssbd2018.ssbd01.dto.BasicAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.BasicNewAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.EditableAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.FullAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.MyPasswordDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.NewAccountDto;
import pl.lodz.p.it.ssbd2018.ssbd01.dto.OtherPasswordDto;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.AccessLevel;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.ArchivalPassword;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.UserUnauthorized;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.WebErrorInfo;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.ConstraintException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.PasswordNotMatch;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.PasswordSameAsArchivalPasswordException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.PasswordTooShortException;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.mok.WrongTokenException;
import pl.lodz.p.it.ssbd2018.ssbd01.mapper.AccessLevelMapper;
import pl.lodz.p.it.ssbd2018.ssbd01.mapper.AccountMapper;
import pl.lodz.p.it.ssbd2018.ssbd01.mapper.PasswordMapper;
import pl.lodz.p.it.ssbd2018.ssbd01.mok.managers.AccountManagerLocal;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes;
import static pl.lodz.p.it.ssbd2018.ssbd01.tools.ErrorCodes.*;
import pl.lodz.p.it.ssbd2018.ssbd01.tools.HashUtils;
import pl.lodz.p.it.ssbd2018.ssbd01.exceptions.web.UserAlreadyLogoutException;

/**
 *
 * @author dlange
 * @author agkan
 * @author michalmalec
 */
@Path("account")
public class AccountRestController {

    @EJB
    AccountManagerLocal accountManagerLocal;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
        List<FullAccountDto> fullAccountDto = AccountMapper.INSTANCE.accountsToEditableDTO(accountManagerLocal.getAllAccounts());
        return Response.status(Response.Status.OK)
                .entity(fullAccountDto)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @GET
    @Path("{accountId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountToEdit(@PathParam("accountId") long accountId) {
        try {
            Account accountToEdit = accountManagerLocal.getAccountById(accountId);
            EditableAccountDto accountDto = AccountMapper.INSTANCE.accountToEditableDto(accountToEdit);
            return Response.status(Response.Status.OK)
                    .entity(accountDto)
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
    @Path("myAccountToEdit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyAccountToEdit(@Context HttpServletRequest servletRequest) {
        try {
            String login = getUserLogin(servletRequest);
            Account accountToEdit = accountManagerLocal.getMyAccountByLogin(login);
            EditableAccountDto accountDto = AccountMapper.INSTANCE.accountToEditableDto(accountToEdit);
            return Response.status(Response.Status.OK)
                    .entity(accountDto)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("updateMyAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMyAccount(EditableAccountDto accountDto, @Context HttpServletRequest servletRequest) {
        try {
            String login = getUserLogin(servletRequest);
            Account accountToEdit = accountManagerLocal.getMyAccountByLogin(login);
            AccountMapper.INSTANCE.accountDtoToAccount(accountDto, accountToEdit);
            this.validateConstraints(accountToEdit);
            accountManagerLocal.saveMyAccountAfterEdit(accountToEdit);
            return Response.status(Response.Status.OK)
                    .entity(new WebErrorInfo("200", SUCCESS))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("updateAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccount(EditableAccountDto accountDto) {
        try {
            Account accountToEdit = accountManagerLocal.getAccountById(accountDto.getId());
            AccountMapper.INSTANCE.accountDtoToAccount(accountDto, accountToEdit);
            this.validateConstraints(accountToEdit);
            accountManagerLocal.saveAccountAfterEdit(accountToEdit);
            return Response.status(Response.Status.OK)
                    .entity(new WebErrorInfo("200", SUCCESS))
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
    @Path("allAccessLevel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccessLevel() {
        List<AccessLevel> accessLevel = accountManagerLocal.getAllAccessLevels();
        JSONObject json = new JSONObject();
        json.put("accessLevels", AccessLevelMapper.INSTANCE.accessLevelToDto(accessLevel));
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(json)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @POST
    @Path("registerAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerAccount(NewAccountDto newAccount, @Context ServletContext servletContext) {
        try {
            validatePassword(newAccount);
            Account account = new Account();
            AccountMapper.INSTANCE.newAccountDtoToAccount(newAccount, account);
            this.validateConstraints(account);
            accountManagerLocal.registerAccount(account, servletContext);
            return Response.status(Response.Status.OK)
                    .entity(new WebErrorInfo("200", SUCCESS))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("changeMyPassword")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeMyPassword(MyPasswordDto passDto, @Context HttpServletRequest servletRequest) {
        try {
            String login = getUserLogin(servletRequest);
            this.validatePassword(passDto);
            Account accountToEdit = accountManagerLocal.getMyAccountByLogin(login);
            this.verifyPasswords(passDto, accountToEdit);
            this.verifyArchivalPaswords(accountToEdit, passDto);            
            PasswordMapper.INSTANCE.newPasswordDtoToAccount(passDto, accountToEdit);
            accountManagerLocal.changeMyPassword(accountToEdit);
            return Response.status(Response.Status.OK)
                    .entity(new WebErrorInfo("200", SUCCESS))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("changeOthersPassword")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeOthersPassword(OtherPasswordDto passDto) {
        try {
            this.validatePassword(passDto);
            Account accountToEdit = accountManagerLocal.getAccountById(passDto.getId());
            this.verifyArchivalPaswords(accountToEdit, passDto); 
            PasswordMapper.INSTANCE.newPasswordDtoToAccount(passDto, accountToEdit);
            accountManagerLocal.changeOthersPassword(accountToEdit);
            return Response.status(Response.Status.OK)
                    .entity(new WebErrorInfo("200", SUCCESS))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("lockAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response lockAccount(@QueryParam("accountId") long accountId) {
        try {
            accountManagerLocal.lockAccount(accountId);
            return Response.status(Response.Status.OK)
                    .entity(new WebErrorInfo("200", SUCCESS))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("unlockAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response unlockAccount(@QueryParam("accountId") long accountId) {
        try {
            accountManagerLocal.unlockAccount(accountId);
            return Response.status(Response.Status.OK)
                    .entity(new WebErrorInfo("200", SUCCESS))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("alterAccountAccessLevel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterAccountAccessLevel(BasicAccountDto accountDto) {
        try {
            Account account = accountManagerLocal.getAccountById(accountDto.getId());
            AccountMapper.INSTANCE.basicAccountDtoToAccount(accountDto, account);
            accountManagerLocal.alterAccountAccessLevel(account, accountDto.getAccessLevels());
            return Response.status(Response.Status.OK)
                    .entity(new WebErrorInfo("200", SUCCESS))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("confirmAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response confirmAccount(@QueryParam("accountId") long accountId) {
        try {
            accountManagerLocal.confirmAccount(accountId);
            return Response.status(Response.Status.OK)
                    .entity(new WebErrorInfo("200", SUCCESS))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @PUT
    @Path("confirmAccountByToken")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response confirmAccountByToken(@QueryParam("token") String token) {
        try {
            this.validateToken(token);
            accountManagerLocal.confirmAccountByToken(token);
            return Response.status(Response.Status.OK)
                    .entity(new WebErrorInfo("200", SUCCESS))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (AppBaseException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new WebErrorInfo("400", ex.getCode()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    private void validateConstraints(Account account) throws AppBaseException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
        List<String> errors = new ErrorCodes().getAllErrors();

        if (constraintViolations.size() > 0) {
            for (int i = 0; i < errors.size(); i++) {
                for (ConstraintViolation<Account> temp : constraintViolations) {
                    if (errors.get(i).equals(temp.getMessage())) {
                        throw new ConstraintException("constraint_error", errors.get(i));
                    }
                }
            }
        }
    }

    private void validatePassword(BasicNewAccountDto account) throws AppBaseException {
        if (account.getFirstPassword().length() < 8 || account.getSecondPassword().length() < 8) {
            throw new PasswordTooShortException("password_to_short");
        }
        if (!account.getFirstPassword().equals(account.getSecondPassword())) {
            throw new PasswordNotMatch("password_different_error");
        }                        
    }

    private void verifyPasswords(MyPasswordDto passDto, Account account) throws AppBaseException {
        if (!account.getPassword().contentEquals(HashUtils.sha256(passDto.getOldPassword()))) {
            throw new PasswordNotMatch("password_not_match_error");
        }
    }
    
    private void verifyArchivalPaswords(Account account, BasicNewAccountDto passDto) throws AppBaseException {
        for(ArchivalPassword archivalPassword : accountManagerLocal.getAllArchivalPasswordsByAccount(account.getId()))
            if(archivalPassword.getPassword().contentEquals(HashUtils.sha256(passDto.getFirstPassword())))
                throw new PasswordSameAsArchivalPasswordException("password_same_as_archival_error");
    }

    private String getUserLogin(HttpServletRequest servletRequest) throws UserUnauthorized, UserAlreadyLogoutException {
        if (servletRequest.getUserPrincipal() == null) {
            throw new UserAlreadyLogoutException("user_already_logout_error");
        }
        return servletRequest.getUserPrincipal().getName();
    }
    
    private void validateToken(String token) throws WrongTokenException {
        if (token == null || token.length() < 32) {
            throw new WrongTokenException("wrong_token");
        }
    }

}
