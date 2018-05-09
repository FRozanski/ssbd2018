package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;

/**
 *
 * @author agkan
 */
@Named
@RequestScoped
public class TokenBean {
    
    @Inject
    private AccountController accountController;
    
    private String confirmationMessage;
    
    public String createVeryficationLink(Account account) {
        String token = accountController.getToken(account);
        return "/regitrationConfirm.html?token=" + token; //TODO
    }
    
    //TODO: @RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
    @GET
    @Path("/regitrationConfirm.html?token={token}")
    public String confirmRegistration(@PathParam("token") String token,
            @Context Request request) {
//        VeryficationToken veryficationToken = accountController.getVeryficationToken(token);
//        
//        if (veryficationToken == null) {
//            confirmationMessage = "Invalid Token";
//            //TODO return "redirect:/badUser.html"
//        }
//        
//        Calendar calendar = Calendar.getInstance();
//        Date expiryDate = veryficationToken.getExpiryDate();
//        if (expiryDate != null) {
//            long timeDiff = expiryDate.getTime() - calendar.getTime().getTime();
//            if (timeDiff <= 0) {
//                confirmationMessage = "Token Expired";
//                //TODO return "redirect:/badUser.html"
//            }            
//        } else {
//            confirmationMessage = "Invalid Token";
//            //TODO return "redirect:/badUser.html"
//        }
//        
//        Account account = veryficationToken.getIdAccount();
//        if (account == null) {
//            confirmationMessage = "Wrong Account";
//            //TODO return "redirect:/badUser.html"
//        }
//        accountController.confirmAccount(account);
        
        //TODO: return "redirect:/login.html
        return null;
    }    
}
