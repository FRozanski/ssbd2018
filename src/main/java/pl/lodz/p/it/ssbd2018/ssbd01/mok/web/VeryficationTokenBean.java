package pl.lodz.p.it.ssbd2018.ssbd01.mok.web;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
    
    public void createVeryficationToken(Account idAccount) {
        VeryficationToken veryficationToken = new VeryficationToken();
        veryficationToken.setToken(generateToken());
        veryficationToken.setExpiryDate(generateExpiryDate());
        veryficationToken.setIdAccount(idAccount);
        accountController.createVeryficationToken(veryficationToken);
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
