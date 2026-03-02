package mx.com.axity.services.service;

import mx.com.axity.commons.to.EmailContentTO;
import org.springframework.mail.javamail.JavaMailSender;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

public interface IEmailService {
    MimeMessage getMessageEmail(EmailContentTO emailContentTO, String emailCompany, String imagen, String htmlContent, JavaMailSender mailSession) throws MessagingException, IOException;
    void sendMail(EmailContentTO emailContentTO, Boolean shouldbeparse) ;
    JavaMailSender getPropertiesEmail(String host, String port);
    String getParameterEmailConfig(String parameter);
}
