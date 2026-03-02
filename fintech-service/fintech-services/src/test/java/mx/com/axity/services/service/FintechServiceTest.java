package mx.com.axity.services.service;

import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FintechServiceTest extends BaseTest {

    @Test
    public void exampleTest() {
        var users = this.fintechService.getUserById(Long.parseLong("1"));
        Assert.assertEquals("marialopez_89@hotmail.com",users.getEmail());
    }
}
