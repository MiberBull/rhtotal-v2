package mx.com.axity.web.rest;

import mx.com.axity.web.BaseTest;
import org.junit.jupiter.api.Test;

public class GenericTaskControllerTest extends BaseTest {
    @Test
    public void getHeaderTest(){
        this.genericTasksFacadeTest.getHeader("headersBanners");
    }
}
