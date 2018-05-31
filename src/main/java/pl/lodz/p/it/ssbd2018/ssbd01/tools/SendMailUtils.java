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
import pl.lodz.p.it.ssbd2018.ssbd01.mok.constants.SendMailUtilsConstants;

/**
 *
 * @author piotrek
 */
@Stateful
@LocalBean
public class SendMailUtils {

    //private static final SendMailUtilsConstants constants = (SendMailUtilsConstants)ConstantsParser.deserialize(SendMailUtils.class.getSimpleName());
    
    private int PORT = 465;
    private String HOST = "smtp.gmail.com";
    private String FROM = "ssbd01team@gmail.com";
    private boolean AUTH = true;
    private String USERNAME = "ssbd01team@gmail.com";
    private String PASSWORD = "fajniebedziejaksieuda";
    private String PROTOCOL = "SMTPS";
    private boolean DEBUG = true;
    private String SUBJECT = "Welcome at Co-oper Service";
    private String WELCOME_MESSAGE = "Witaj w gronie kooperantów!!! \n\n";
    private String VERYFICATION_MESSAGE = "Aby potwierdzić chęć uczestnictwa, kliknij w link poniżej \n"
                    + "\t Link aktywacyjny: ";
    private String ACTIVATION_MESSAGE = "Twoje konto jest już aktywne! Zapraszamy do korzystania z serwisu.";
    private String LOCK_MESSAGE = "Twoje konto zostało zablokowane.";
    private String GREETINGS = "\n\n" 
                    + "Pozdrawiamy! \n"
                    + "KOOPERANT TEAM :D";
    private Session initializeEmailSettings() {    
        
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.ssl.enable", true);
        
        Authenticator authenticator = null;
        if (AUTH) {
            props.put("mail.smtp.auth", true);
            authenticator = new Authenticator() {
                private PasswordAuthentication passwdAuth = new PasswordAuthentication(USERNAME, PASSWORD);

                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return passwdAuth;
                }
            };
        }

        Session session = Session.getInstance(props, authenticator);
        session.setDebug(DEBUG);
        
        return session;
    }

    public void sendVerificationEmail(String to, String activationLink) {
        
        MimeMessage message = new MimeMessage(initializeEmailSettings());
        try {
            message.setFrom(new InternetAddress(FROM));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(SUBJECT);
            message.setSentDate(new Date());
            message.setText(WELCOME_MESSAGE + VERYFICATION_MESSAGE + activationLink + GREETINGS);                    
            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
    
    public void sendMailAfterActivation(String to) {
        
     MimeMessage message = new MimeMessage(initializeEmailSettings());
        try {
            message.setFrom(new InternetAddress(FROM));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(SUBJECT);
            message.setSentDate(new Date());
            message.setText(WELCOME_MESSAGE + ACTIVATION_MESSAGE + GREETINGS);
            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }   
    }
    
    public void sendMailAfterAccountLock(String to) {
        
     MimeMessage message = new MimeMessage(initializeEmailSettings());
        try {
            message.setFrom(new InternetAddress(FROM));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(SUBJECT);
            message.setSentDate(new Date());
            message.setText(WELCOME_MESSAGE + LOCK_MESSAGE + GREETINGS);
            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }   
    }
}