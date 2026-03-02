package mx.com.axity.services.service;

import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Test;

public class UserServiceTest extends BaseTest {

    @Test
    public void exampleTest() {
        var users = this.fintechService.getUserById(Long.parseLong("1"));
        Assert.assertEquals("marialopez_89@hotmail.com",users.getEmail());
    }

    @Test
    public void getUserById() {
        var users = this.userService.getUserById(Long.parseLong("1"));
        Assert.assertEquals("marialopez_89@hotmail.com",users.getEmail());
    }

    @Test
    public  void should_validate_length_data() {
        var data = this.userService.getDataForQuerySico(Long.parseLong("1"));
        Assert.assertEquals(8,((Object[]) data[0]).length);
    }

    @Test
    public  void should_get_one_employee(){
        var data = this.userService.getEmployeByIdUser(Long.parseLong("1"));
        Assert.assertNotNull(data);
    }

}
