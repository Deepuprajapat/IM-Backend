package com.realestate.invest.Service;

import org.springframework.stereotype.Service;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailVerificationService 
{
    
    /**
     * @Sends an email with the specified subject, message, and recipient email address.
     *
     * @param subject The subject of the email.
     * @param message The content of the email message.
     * @param to      The recipient's email address.
     * @return true if the email is sent successfully, false otherwise.
     */
    public boolean sendEmail(String subject, String message, String to)
    {
            boolean f =  false;
            String form = "imhrconsole@gmail.com";
            String host = "smtp.gmail.com";
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");
            Session session = Session.getInstance(properties, new Authenticator() 
            {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() 
                {
                    return new PasswordAuthentication("imhrconsole@gmail.com", "ltfgohliepgvygke");
                }
            });
            session.setDebug(true);
            MimeMessage m = new MimeMessage(session);
        try
        {
            m.setFrom(form);
            System.out.println("Forms : "+form);
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            m.setSubject(subject);
            System.out.println("Subject : "+subject);
            m.setContent(message,"text/html");
            Transport.send(m);
            System.out.println("Email sent successfully");
            f = true;
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return f;
    }
    
}
