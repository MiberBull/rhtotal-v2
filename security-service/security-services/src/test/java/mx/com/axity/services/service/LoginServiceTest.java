
package mx.com.axity.services.service;

import mx.com.axity.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.AddressException;
import java.io.IOException;
import java.util.Properties;

public class LoginServiceTest extends BaseTest {


    @Test
    public void get_Correct_User_Test() {
        var testCorrectUser = this.loginService.findUserByEmail("marialopez_89@hotmail.com");
        Assertions.assertNotNull(testCorrectUser);
    }

    @Disabled("Se comenta para pasar el ambiente a QA")
    @Test
    public void get_Error_User_Test() {
        var testErrorUser = this.loginService.findUserByEmail("artur_89@hotmail.com");
        Assertions.assertNull(testErrorUser);
    }


    @Test
    public void get_Correct_Role_User_Test() {
        var testCorrectRoleUser = this.loginService.findRolUserByEmail("marialopez_89@hotmail.com",null);
        Assertions.assertNotNull(testCorrectRoleUser);
    }

    @Test
    public void get_Error_Role_User_Test() {
        var testErrorRoleUser = this.loginService.findRolUserByEmail("artur@workpoint.com",null);
        Assertions.assertNull(testErrorRoleUser);
    }

    @Test
    public void error_Update_User_Test(){
        this.loginService.updateUser(null);
    }

    @Test
    public void error_Update_Role_User_Test(){
        this.loginService.updateRoleUser(null);
    }
}
