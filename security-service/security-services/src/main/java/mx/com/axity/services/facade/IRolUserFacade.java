package mx.com.axity.services.facade;

import mx.com.axity.commons.to.*;

import java.util.List;


public interface IRolUserFacade {

    RoleCompoundTO<RolesUserTO> getRole(RoleCompoundTO<RolesUserTO> role);

    Boolean saveOrUpdateRole(RoleCompoundTO<RolesUserTO> role);

    RoleCompoundTO<CatalogoRolTO> getAllCatalogue();

    ExcelFormatExportTO sendExcel();

    List<RolesUserTO> getPagedRole(int page);

    RoleCountRowTO getNumberRow();

    void createRequestReset(ResetRequestTO userData);

    void confirmReset(ResetConfirmationTO userData);

}
