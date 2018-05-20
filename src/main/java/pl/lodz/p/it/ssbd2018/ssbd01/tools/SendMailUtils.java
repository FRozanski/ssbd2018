package pl.lodz.p.it.ssbd2018.ssbd01.tools;

import java.util.Date;
import java.util.Properties;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author piotrek
 */
@Stateful
@LocalBean
public class SendMailUtils {

    private int port = 465;
    private String host = "smtp.gmail.com";
    private String from = "ssbd01team@gmail.com";
    private boolean auth = true;
    private String username = "ssbd01team@gmail.com";
    private String password = "fajniebedziejaksieuda";
    private String protocol = "SMTPS";
    private boolean debug = true;
    
    private Session initializeEmailSettings() {    
        
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.enable", true);
        
        Authenticator authenticator = null;
        if (auth) {
            props.put("mail.smtp.auth", true);
            authenticator = new Authenticator() {
                private PasswordAuthentication passwdAuth = new PasswordAuthentication(username, password);

                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return passwdAuth;
                }
            };
        }

        Session session = Session.getInstance(props, authenticator);
        session.setDebug(debug);
        
        return session;
    }

    public void sendVerificationEmail(String to, String activationLink) {
        
        MimeMessage message = new MimeMessage(initializeEmailSettings());
        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject("Welcome at Co-oper Service");
            message.setSentDate(new Date());
            message.setText("Witaj w gronie kooperantów!!! \n\n"
                    + "Aby potwierdzić chęć uczestnictwa, kliknij w link poniżej \n"
                    + "\t Link aktywacyjny: " + activationLink + "\n\n" 
                    + "Pozdrawiamy! \n"
                    + "KOOPERANT TEAM :D");
            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
    
    public void sendMailAfterActivation(String to) {
        
     MimeMessage message = new MimeMessage(initializeEmailSettings());
        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject("Welcome at Co-oper Service");
            message.setSentDate(new Date());
            message.setText("Witaj w gronie kooperantów!!! \n\n"
                    + "Twoje konto jest już aktywne! Zapraszamy do korzystania z serwisu. \\n\\n" 
                    + "Pozdrawiamy! \n"
                    + "KOOPERANT TEAM :D");
            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }   
    }
    
    public void sendMailAfterAccountLock(String to) {
        
     MimeMessage message = new MimeMessage(initializeEmailSettings());
        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject("Welcome at Co-oper Service");
            message.setSentDate(new Date());
            message.setText("Witaj w gronie kooperantów!!! \n\n"
                    + "Twoje konto zostało zablokowane. \\n\\n" 
                    + "Pozdrawiamy! \n"
                    + "KOOPERANT TEAM :D");
            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }   
    }
}