package mx.com.axity.services.service;

import mx.com.axity.commons.to.*;

import java.util.List;
import java.util.Map;

public interface IuserService {

    ConfirmationTO createUser(UserDataTO userData);

    void confirmUser(UserConfirmationDataTO userData);

    void createResetRequest(ResetRequestTO userData);

    void confirmReset(ResetConfirmationTO userData);

    Map<String,Object> getCredentialInfo(Long idUser);

}
