package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.to.*;
import mx.com.axity.services.facade.IuserFacade;
import mx.com.axity.services.service.IuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class userFacade implements IuserFacade {

    @Autowired
    private IuserService userService;

    public ConfirmationTO createUser(UserDataTO userData) {
        return this.userService.createUser(userData);
    }

    @Override
    public void confirmUser(UserConfirmationDataTO userData) {
        this.userService.confirmUser(userData);
    }

    @Override
    public void createRequestReset(ResetRequestTO userData) {
        this.userService.createResetRequest(userData);
    }

    @Override
    public void confirmReset(ResetConfirmationTO userData) {
        this.userService.confirmReset(userData);
    }

    @Override
    public Map<String,Object> getCredentialInfo(Long idUser) {
        return this.userService.getCredentialInfo(idUser);
    }
}
