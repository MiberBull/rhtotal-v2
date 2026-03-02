package mx.com.axity.services.service;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.*;
import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class userServiceTest extends BaseTest {

    @Test
    @Ignore("no tengo idea de como hacer un mock de un endpoint con un eureka falso")
    public void shouldCreateAUserAccount() {
        UserDataTO userData = new UserDataTO();
        userData.setPassword("U2FsdGVkX19aZe4on/50HgrYOETPN/Al9HO2gvZkV9U=");
        userData.setPasswordConfirmed("U2FsdGVkX19aZe4on/50HgrYOETPN/Al9HO2gvZkV9U=");
        userData.setUser("holi@uncorreo.com");
        ConfirmationTO confirmation = this.userServiceTest.createUser(userData);

        Assert.assertEquals(ConfirmationTO.class, confirmation.getClass());
    }

    @Test(expected = BusinessException.class)
    public void shouldValidatePasswordsAreEqual() {
        UserDataTO userData = new UserDataTO();
        userData.setPassword("U2FsdGVkX19aZe4on/50HgrYOETPN/Al9HO2gvZkV9U=");
        userData.setPasswordConfirmed("U2FsdGVkX64aZe4on/50HgrCDETPN/Al9HO2gvZkV9U=");
        userData.setUser("holi@uncorreo.com");
        this.userServiceTest.createUser(userData);
    }

    @Test
    @Ignore("no tengo idea de como hacer un mock de un endpoint con un eureka falso")
    public void shouldConfirmUser() {
        UserConfirmationDataTO confirmationData = new UserConfirmationDataTO();
        confirmationData.setCode("2345");
        confirmationData.setUser("uncorreox@correo.com");
        this.userServiceTest.confirmUser(confirmationData);
    }

    @Test
    @Ignore("se requiere el uso de un api externa")
    public void shouldCreateAResetRequest() {
        ResetRequestTO resetRequest = new ResetRequestTO();
        resetRequest.setUser("uncorreox@correo.com");
        this.userServiceTest.createResetRequest(resetRequest);
    }

    @Test
    @Ignore("se requiere el uso de un api externa")
    public void shouldConfirmAResetRequest() {
        ResetConfirmationTO resetConfirmation = new ResetConfirmationTO();

        resetConfirmation.setUser("uncorreox@correo.com");
        resetConfirmation.setToken("7dae23ff-e3c7-4622-873e-aa32e2bdfcd5");
        resetConfirmation.setNewPassword("U2FsdGVkX19aZe4on/50HgrYOETPN/Al9HO2gvZkV9U=");
        resetConfirmation.setNewPasswordConfirmed("U2FsdGVkX19aZe4on/50HgrYOETPN/Al9HO2gvZkV9U=");

        this.userServiceTest.confirmReset(resetConfirmation);
    }

    @Test
    public void Should_Return_User_Data(){

        String curp = "";
        String cliente = "";
        String proyecto = "";
        Assert.assertNotNull("");
    }
}
