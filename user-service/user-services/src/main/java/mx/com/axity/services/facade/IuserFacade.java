package mx.com.axity.services.facade;

import mx.com.axity.commons.to.*;

import java.util.Map;

public interface IuserFacade {
    ConfirmationTO createUser(UserDataTO userData);

    void confirmUser(UserConfirmationDataTO userData);

    void createRequestReset(ResetRequestTO userData);

    void confirmReset(ResetConfirmationTO userData);

    Map<String,Object> getCredentialInfo(Long idUser );

}
