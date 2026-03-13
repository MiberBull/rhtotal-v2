package mx.com.axity.services.service;

import mx.com.axity.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserServiceTest extends BaseTest {

    @Test
    public void exampleTest() {
        var users = this.fintechService.getUserById(Long.parseLong("1"));
        Assertions.assertEquals("marialopez_89@hotmail.com",users.getEmail());
    }

    @Test
    public void getUserById() {
        var users = this.userService.getUserById(Long.parseLong("1"));
        Assertions.assertEquals("marialopez_89@hotmail.com",users.getEmail());
    }

    @Test
    public  void should_validate_length_data() {
        var data = this.userService.getDataForQuerySico(Long.parseLong("1"));
        Assertions.assertEquals(8,((Object[]) data[0]).length);
    }

    @Test
    public  void should_get_one_employee(){
        var data = this.userService.getEmployeByIdUser(Long.parseLong("1"));
        Assertions.assertNotNull(data);
    }

}
