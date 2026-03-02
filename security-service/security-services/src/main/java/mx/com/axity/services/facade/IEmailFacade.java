package mx.com.axity.services.facade;

import mx.com.axity.commons.to.EmailContentTO;

public interface IEmailFacade  {
    void sendMail(EmailContentTO emailContentTO, Boolean shouldbeparse);
    String getParameterEmailConfig(String parameter);
}
