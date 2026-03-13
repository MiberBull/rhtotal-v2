package mx.com.axity.web.rest;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.AnswerLoginMobileTO;
import mx.com.axity.commons.to.AnswerLoginTO;
import mx.com.axity.commons.to.RolesUserTO;
import mx.com.axity.commons.to.UserTO;
import mx.com.axity.web.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class LoginControllerTest extends BaseTest {

    @Disabled("Se agrego el cifrado para el login")
    @Test
    public void login_Web_Correct_User_Email_Test() {
        AnswerLoginTO answerLoginTO = new AnswerLoginTO();
        answerLoginTO.setBlock(Boolean.FALSE);
        answerLoginTO.setMessage(null);
        answerLoginTO.setFlag(-1);
        RolesUserTO user = new RolesUserTO();
        user.setPassword("12345");
        user.setEmail("arturo.bravo.martinez05@gmail.com");
        answerLoginTO.setUser(user);
        var testLogin = this.loginFacade.loginWeb(answerLoginTO);
        Assertions.assertEquals(0, testLogin.getFlag());
    }

    @Disabled("Se agrego el cifrado para el login")
    @Test
    public void login_Web_Wrong_User_Email_Test() {
        AnswerLoginTO answerLoginTO = new AnswerLoginTO();
        answerLoginTO.setBlock(Boolean.FALSE);
        answerLoginTO.setMessage(null);
        answerLoginTO.setFlag(-1);
        RolesUserTO user = new RolesUserTO();
        user.setPassword("12345");
        user.setEmail("arturo.bravo.martinezTest05@gmail.com");
        answerLoginTO.setUser(user);
        var testLogin = this.loginFacade.loginWeb(answerLoginTO);
        Assertions.assertEquals(1, testLogin.getFlag());
    }

    @Disabled("Se agrego el cifrado para el login")
    @Test
    public void login_Web_Password_Wrong_Email_Test() {
        AnswerLoginTO answerLoginTO = new AnswerLoginTO();
        answerLoginTO.setBlock(Boolean.FALSE);
        answerLoginTO.setMessage(null);
        answerLoginTO.setFlag(-1);
        RolesUserTO user = new RolesUserTO();
        user.setPassword("1234534343");
        user.setEmail("arturo.bravo.martinez05@gmail.com");
        answerLoginTO.setUser(user);
        var testLogin = this.loginFacade.loginWeb(answerLoginTO);
        Assertions.assertEquals(2, testLogin.getFlag());
    }

    @Disabled("Se agrego el cifrado para el login")
    @Test
    public void login_Web_User_Blocked_Email_Test() {
        AnswerLoginTO answerLoginTO = new AnswerLoginTO();
        answerLoginTO.setBlock(Boolean.TRUE);
        answerLoginTO.setMessage(null);
        answerLoginTO.setFlag(-1);
        RolesUserTO user = new RolesUserTO();
        user.setPassword("12345");
        user.setEmail("arturo.bravo.martinez05@gmail.com");
        answerLoginTO.setUser(user);
        var testLogin = this.loginFacade.loginWeb(answerLoginTO);
        Assertions.assertEquals(3, testLogin.getFlag());
    }

    @Disabled("Se agrego el cifrado para el login")
    @Test
    public void login_Web_User_New_Email_Test() {
        AnswerLoginTO answerLoginTO = new AnswerLoginTO();
        answerLoginTO.setBlock(Boolean.FALSE);
        answerLoginTO.setMessage(null);
        answerLoginTO.setFlag(-1);
        RolesUserTO user = new RolesUserTO();
        user.setPassword("12345");
        user.setEmail("arturobravo.martinez05@gmail.com");
        answerLoginTO.setUser(user);
        var testLogin = this.loginFacade.loginWeb(answerLoginTO);
        Assertions.assertEquals(4, testLogin.getFlag());
    }

    @Disabled("Se agrego el cifrado para el login")
    @Test
    public void login_Web_Error_User_Email_Test() {
        this.loginFacade.loginWeb(null);
    }

    @Disabled("Se agrego el cifrado para el login")
    @Test
    public void login_Mobile_Correct_User_Email_Test() {
        AnswerLoginMobileTO answerLoginTO = new AnswerLoginMobileTO();
        answerLoginTO.setBlock(Boolean.FALSE);
        answerLoginTO.setMessage(null);
        answerLoginTO.setFlag(-1);
        UserTO user = new UserTO();
        user.setPassword("12345");
        user.setEmail("arturo.bravo.martinez05@gmail.com");
        answerLoginTO.setUser(user);
        var testLogin = this.loginFacade.loginMobile(answerLoginTO);
        Assertions.assertEquals(0, testLogin.getFlag());
    }

    @Disabled("Se agrego el cifrado para el login")
    @Test
    public void login_Mobile_Wrong_User_Email_Test() {
        AnswerLoginMobileTO answerLoginTO = new AnswerLoginMobileTO();
        answerLoginTO.setBlock(Boolean.FALSE);
        answerLoginTO.setMessage(null);
        answerLoginTO.setFlag(-1);
        UserTO user = new UserTO();
        user.setPassword("12345");
        user.setEmail("arturo.bravo.martinezTest05@gmail.com");
        answerLoginTO.setUser(user);
        var testLogin = this.loginFacade.loginMobile(answerLoginTO);
        Assertions.assertEquals(1, testLogin.getFlag());
    }

    @Disabled("Se agrego el cifrado para el login")
    @Test
    public void login_Mobile_Password_Wrong_Email_Test() {
        AnswerLoginMobileTO answerLoginTO = new AnswerLoginMobileTO();
        answerLoginTO.setBlock(Boolean.FALSE);
        answerLoginTO.setMessage(null);
        answerLoginTO.setFlag(-1);
        UserTO user = new UserTO();
        user.setPassword("1234534343");
        user.setEmail("arturo.bravo.martinez05@gmail.com");
        answerLoginTO.setUser(user);
        var testLogin = this.loginFacade.loginMobile(answerLoginTO);
        Assertions.assertEquals(2, testLogin.getFlag());
    }

    @Disabled("Se agrego el cifrado para el login")
    @Test
    public void login_Web_Mobile_Blocked_Email_Test() {
        AnswerLoginMobileTO answerLoginTO = new AnswerLoginMobileTO();
        answerLoginTO.setBlock(Boolean.TRUE);
        answerLoginTO.setMessage(null);
        answerLoginTO.setFlag(-1);
        UserTO user = new UserTO();
        user.setPassword("12345");
        user.setEmail("arturo.bravo.martinez05@gmail.com");
        answerLoginTO.setUser(user);
        var testLogin = this.loginFacade.loginMobile(answerLoginTO);
        Assertions.assertEquals(3, testLogin.getFlag());
    }

    @Disabled("Se agrego el cifrado para el login")
    @Test
    public void login_Mobile_User_New_Email_Test() {
        AnswerLoginMobileTO answerLoginTO = new AnswerLoginMobileTO();
        answerLoginTO.setBlock(Boolean.FALSE);
        answerLoginTO.setMessage(null);
        answerLoginTO.setFlag(-1);
        UserTO user = new UserTO();
        user.setPassword("12345");
        user.setEmail("arturobravo.martinez05@gmail.com");
        answerLoginTO.setUser(user);
        var testLogin = this.loginFacade.loginMobile(answerLoginTO);
        Assertions.assertEquals(4, testLogin.getFlag());
    }

    @Disabled("Se agrego el cifrado para el login")
    @Test
    public void login_Mobile_Error_User_Email_Test() {
        this.loginFacade.loginMobile(null);
    }


}
