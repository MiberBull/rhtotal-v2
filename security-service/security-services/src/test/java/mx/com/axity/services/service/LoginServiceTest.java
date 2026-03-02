
package mx.com.axity.services.service;

import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.Properties;

public class LoginServiceTest extends BaseTest {


    @Test
    public void get_Correct_User_Test() {
        var testCorrectUser = this.loginService.findUserByEmail("marialopez_89@hotmail.com");
        Assert.assertNotNull(testCorrectUser);
    }

    @Ignore("Se comenta para pasar el ambiente a QA")
    @Test
    public void get_Error_User_Test() {
        var testErrorUser = this.loginService.findUserByEmail("artur_89@hotmail.com");
        Assert.assertNull(testErrorUser);
    }


    @Test
    public void get_Correct_Role_User_Test() {
        var testCorrectRoleUser = this.loginService.findRolUserByEmail("marialopez_89@hotmail.com",null);
        Assert.assertNotNull(testCorrectRoleUser);
    }

    @Test
    public void get_Error_Role_User_Test() {
        var testErrorRoleUser = this.loginService.findRolUserByEmail("artur@workpoint.com",null);
        Assert.assertNull(testErrorRoleUser);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void error_Update_User_Test(){
        this.loginService.updateUser(null);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void error_Update_Role_User_Test(){
        this.loginService.updateRoleUser(null);
    }
}
