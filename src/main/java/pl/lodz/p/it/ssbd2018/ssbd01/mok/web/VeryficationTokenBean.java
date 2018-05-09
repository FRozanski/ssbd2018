package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.Account;
import pl.lodz.p.it.ssbd2018.ssbd01.entities.VeryficationToken;

/**
 *
 * @author agkan
 */
@Named
@RequestScoped
public class VeryficationTokenBean {
    
    @Inject
    private AccountController accountController;
    
    private String confirmationMessage;
    
    public String createVeryficationLink(Account idAccount) {
        String token = ""; //veryficationToken.getToken();
        return "/regitrationConfirm.html?token=" + token; //TODO
    }
    
    //TODO: @RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
    @GET
    @Path("/regitrationConfirm.html?token={token}")
    public String confirmRegistration(@PathParam("token") String token,
            @Context Request request) {
        VeryficationToken veryficationToken = accountController.getVeryficationToken(token);
        
        if (veryficationToken == null) {
            confirmationMessage = "Invalid Token";
            //TODO return "redirect:/badUser.html"
        }
        
        Calendar calendar = Calendar.getInstance();
        Date expiryDate = veryficationToken.getExpiryDate();
        if (expiryDate != null) {
            long timeDiff = expiryDate.getTime() - calendar.getTime().getTime();
            if (timeDiff <= 0) {
                confirmationMessage = "Token Expired";
                //TODO return "redirect:/badUser.html"
            }            
        } else {
            confirmationMessage = "Invalid Token";
            //TODO return "redirect:/badUser.html"
        }
        
        Account account = veryficationToken.getIdAccount();
        if (account == null) {
            confirmationMessage = "Wrong Account";
            //TODO return "redirect:/badUser.html"
        }
        accountController.confirmAccount(account);
        
        //TODO: return "redirect:/login.html
        return null;
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private Date generateExpiryDate() {
        int minutesInHour = 60;
        int hoursInDay = 24;
        int expiryTimeInMinutes = minutesInHour * hoursInDay;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }
    
}
