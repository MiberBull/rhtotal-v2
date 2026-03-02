package mx.com.axity.web.rest;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.web.BaseTest;
import org.junit.Assert;
import org.junit.Test;

import javax.mail.SendFailedException;

public class EmailControllerTest extends BaseTest {
    @Test
    public void send_mail_correct_Test() {
  //      var testCorrectSendEmail = this.emailFacade.sendMail("arturo.bravo.martinez05@gmail.com","layoutNewUser");
   //     Assert.assertTrue(testCorrectSendEmail);
    }

    @Test
    public void failure_to_send_mail() {
   //     Boolean testErrorSendEmail = this.emailFacade.sendMail("maropez_8tmail.com", "layoutNewUserpppp");
     //   Assert.assertFalse(testErrorSendEmail);
    }

}
