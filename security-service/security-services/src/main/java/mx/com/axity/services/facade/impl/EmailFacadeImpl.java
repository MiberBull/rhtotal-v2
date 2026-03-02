package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.EmailContentTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.services.facade.IEmailFacade;
import mx.com.axity.services.service.IEmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailFacadeImpl implements IEmailFacade {

    static final Logger LOG = LogManager.getLogger(EmailFacadeImpl.class);

    @Autowired
    IEmailService emailService;

    @Override
    public void sendMail(EmailContentTO emailContentTO, Boolean shouldbeparse) {
        try {
            this.emailService.sendMail(emailContentTO,shouldbeparse);
        } catch (Exception e) {
            LOG.info("Error al enviar email[" + e.getMessage() + "]");
            throw new BusinessException(Constants.CONTROLLED_MENSSAGE ,e);
        }
    }

    @Override
    public String getParameterEmailConfig(String parameter) {
        try {
            return this.emailService.getParameterEmailConfig(parameter);
        } catch (Exception e) {
            LOG.info("Error al obtener el parametro para email[" + e.getMessage() + "]");
            throw new BusinessException(Constants.CONTROLLED_MENSSAGE ,e);
        }
    }


}
