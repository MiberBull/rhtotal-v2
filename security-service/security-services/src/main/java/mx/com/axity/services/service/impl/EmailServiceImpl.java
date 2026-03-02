package mx.com.axity.services.service.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.EmailContentTO;
import mx.com.axity.commons.to.EmployeeTO;
import mx.com.axity.commons.to.EmployeeUserTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.EmployeeComplementaryDO;
import mx.com.axity.persistence.ConfigEmailDAO;
import mx.com.axity.persistence.EmployeeComplementaryDAO;
import mx.com.axity.persistence.UserLoginDAO;
import mx.com.axity.services.service.IEmailService;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jakarta.activation.DataHandler;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static mx.com.axity.commons.util.Constants.*;

@Service
public class EmailServiceImpl implements IEmailService {

    static final Logger LOG = LogManager.getLogger(EmailServiceImpl.class);

    @Autowired
    ConfigEmailDAO configEmailDAO;

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    UserLoginDAO loginDAO;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EmployeeComplementaryDAO complementaryDAO;


    @Override
    public void sendMail(EmailContentTO emailContentTO, Boolean shouldbeparse)  {
        String nameUser = this.configEmailDAO.findByNameParemeter(Constants.NAME_USER);
        String bodyNewUser = this.configEmailDAO.findByNameParemeter(emailContentTO.getNameTemplate());
        String nameHost = this.configEmailDAO.findByNameParemeter(Constants.NAME_HOST);
        bodyNewUser = generateTemplate(emailContentTO, shouldbeparse, bodyNewUser);
        emailSender = getPropertiesEmail(nameHost, this.configEmailDAO.findByNameParemeter(Constants.NAME_PORT));
        try {
            emailSender.send(getMessageEmail(emailContentTO , nameUser,this.configEmailDAO.findByNameParemeter(IMG_EMAIL_UNBOUND),!shouldbeparse ? bodyNewUser: emailContentTO.getTemplate(), emailSender));
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private String generateTemplate(EmailContentTO emailContentTO, Boolean shouldbeparse, String bodyNewUser) {
        if (!shouldbeparse) {
            if (emailContentTO.getNameTemplate().equalsIgnoreCase(LAYOUT_NEW_USER)) {
                bodyNewUser = bodyNewUser.replaceFirst(PARAM_USER, emailContentTO.getEmail()).replaceFirst(PARAM_PASS, emailContentTO.getEmail());
            }
            if(emailContentTO.getNameTemplate().equalsIgnoreCase("layoutSeguro")){
                var employeeByIdUser = getEmployeeByIdUser(this.loginDAO.findByEmail(emailContentTO.getEmail()).getId());
                var employeeComByIdUser =  this.complementaryDAO.getEmployeeComByIdUser(this.loginDAO.findByEmail(emailContentTO.getEmail()).getId());

                if (null == employeeByIdUser.getName()){
                    bodyNewUser = bodyNewUser.trim().replace(" ","#")
                            .replaceFirst("<br>Nombre:#:nombre<br>Apellido#Paterno:#:apellidoPat<br>Apellido#Materno:#:apellidoMat<br>Tel&#233;fono:#:telefono<br></b><br></p>"," ")
                            .replace("#"," ")
                            .replaceFirst(":usuario",emailContentTO.getEmail());
                }else {
                    bodyNewUser =  bodyNewUser.replaceFirst(":usuario",emailContentTO.getEmail())
                            .replaceFirst(":nombreEmail",employeeByIdUser.getName())
                            .replaceFirst(":nombre",employeeByIdUser.getName())
                            .replaceFirst(":apellidoPat",employeeByIdUser.getLastName())
                            .replaceFirst(":apellidoMat",employeeByIdUser.getmLastName() == null ? "" :employeeByIdUser.getmLastName())
                            .replaceFirst(":telefono",employeeComByIdUser.getPhone());
                }

            }
        }
        return bodyNewUser;
    }

    private EmployeeUserTO getEmployeeByIdUser(Long idUser){
        try {
            return restTemplate.getForObject(HTTP_URI_GET_EMPLOYEE_BY_USER_ID + idUser, EmployeeUserTO.class);
        } catch (Exception e) {
            LOG.info(String.format("Error dentro de SERVICE.getEmployeeByIdUser: %s", e.getMessage()));
            LOG.info(e.toString());
            throw new BusinessException(e.getMessage(), e);
        }
    }


    @Override
    public MimeMessage getMessageEmail(EmailContentTO emailContentTO,String emailCompany, String imagen,String htmlContent, JavaMailSender mailSession){
       try {
           htmlContent = htmlContent.replace(":url",imagen);
           var msg = mailSession.createMimeMessage();
           msg.setFrom(new InternetAddress(emailCompany));
           if(emailContentTO.getNameTemplate().equalsIgnoreCase("layoutNotification")){
               msg.setRecipient(Message.RecipientType.TO,new InternetAddress(emailCompany));

               List<String> items = Arrays.asList(emailContentTO.getEmail().split(","));
               for(String email:items){
                   msg.addRecipients(Message.RecipientType.BCC,InternetAddress.parse(email));
               }
           }else {
               msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailContentTO.getNameTemplate().equalsIgnoreCase("layoutSeguro") ? this.configEmailDAO.findByNameParemeter("emailSeguro") : emailContentTO.getEmail()));
           }
           msg.setSubject(this.validaNameTitle(emailContentTO.getNameTemplate()));
           msg.setSentDate(new Date());
           BodyPart bodyPart = new MimeBodyPart();
           Multipart multipart = new MimeMultipart(Constants.RELATED);
           bodyPart.setContent(htmlContent, Constants.HTML_TEXT);
           multipart.addBodyPart(bodyPart);
           msg.setContent(multipart);
           return msg;
       }catch (Exception e){
           LOG.info("error en getMessageEmail" + e.getMessage());
           throw new BusinessException(e.getMessage(),e);
       }
    }

    private String validaNameTitle(String emailContentTO){
        LOG.info("Name plantilla "+emailContentTO);
        String name="";
        if(emailContentTO.equalsIgnoreCase("layoutOlvidePass")) {
            name = "Restablecer contraseña RH Total";
        }
        if(emailContentTO.equalsIgnoreCase("layoutConfirmCambioPass")) {
            name = "Su contraseña de RH Total ha cambiado";
        }

        if(emailContentTO.equalsIgnoreCase("layoutNewUser")) {
            name = "Bienvenido a RH Total";
        }
        if(emailContentTO.equalsIgnoreCase("layoutCodigoRegistro")) {
            name = "Código de confirmación de cuenta RH Total";
        }
        if(emailContentTO.equalsIgnoreCase("layoutSeguro")) {
            name = "Usuario interesado en el seguro";
        }
        if(emailContentTO.equalsIgnoreCase("layoutNotification")) {
            name = "Nueva notificación de RH Total";
        }
        return name;
    }


    @Override
    public JavaMailSenderImpl getPropertiesEmail(String host, String port) {
        String nameUser = this.configEmailDAO.findByNameParemeter(Constants.NAME_USER);
        String namePassword  = this.configEmailDAO.findByNameParemeter(Constants.NAME_PASSWORD);
        int portMail = Integer.valueOf(port);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(portMail);
        mailSender.setUsername(nameUser);
        mailSender.setPassword(namePassword);
        Properties props = mailSender.getJavaMailProperties();
        props.put(Constants.PROTOCOL, Constants.SMTP_DEFAULT_PROTOCOL);
        props.put(Constants.AUTH, Constants.SMTP_DEFAULT_TRUE);
        props.put(Constants.STARTTLS, Constants.SMTP_DEFAULT_TRUE);
        props.put(Constants.DEBUG, Constants.SMTP_DEFAULT_TRUE);

        return mailSender;
    }
    @Override
    public String getParameterEmailConfig(String parameter) {
        return this.configEmailDAO.findByNameParemeter(parameter);
    }
}
