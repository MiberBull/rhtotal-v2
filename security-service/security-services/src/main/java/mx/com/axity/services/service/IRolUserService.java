package mx.com.axity.services.service;

import mx.com.axity.commons.to.*;

import java.util.List;


public interface IRolUserService {

    RoleCompoundTO<RolesUserTO> getRole(RoleCompoundTO<RolesUserTO> role);

    Boolean saveOrUpdateRole(RolesUserTO role);

    RoleCompoundTO<CatalogoRolTO> getAllCatalogue();

    List<RolesUserTO> getPagedRole(int page);

    RoleCountRowTO getNumberRow();

    void createResetRequest(ResetRequestTO userData);

    void confirmReset(ResetConfirmationTO userData);
}
