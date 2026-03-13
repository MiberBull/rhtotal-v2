package mx.com.axity.web.rest;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.web.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.mail.SendFailedException;

public class EmailControllerTest extends BaseTest {
    @Test
    public void send_mail_correct_Test() {
  //      var testCorrectSendEmail = this.emailFacade.sendMail("arturo.bravo.martinez05@gmail.com","layoutNewUser");
   //     Assertions.assertTrue(testCorrectSendEmail);
    }

    @Test
    public void failure_to_send_mail() {
   //     Boolean testErrorSendEmail = this.emailFacade.sendMail("maropez_8tmail.com", "layoutNewUserpppp");
     //   Assertions.assertFalse(testErrorSendEmail);
    }

}
