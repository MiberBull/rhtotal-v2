package mx.com.axity.services.facade;

import mx.com.axity.commons.to.AnswerLoginMobileTO;
import mx.com.axity.commons.to.AnswerLoginTO;
import mx.com.axity.commons.to.resetPasswordTO;

public interface ILoginFacade {
    AnswerLoginMobileTO loginMobile(AnswerLoginMobileTO login);
    AnswerLoginTO loginWeb(AnswerLoginTO login);
    Boolean getUserById(resetPasswordTO reset);
}
