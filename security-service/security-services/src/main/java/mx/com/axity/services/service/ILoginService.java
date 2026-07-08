package mx.com.axity.services.service;

import mx.com.axity.commons.to.UserTO;
import mx.com.axity.commons.to.resetPasswordTO;
import mx.com.axity.model.RolesUserDO;
import mx.com.axity.model.UserDO;

public interface ILoginService {

    void updateUser(UserDO user);

    UserDO findUserByEmail(String email);

    UserDO findUserByEmailAndTenantId(String email, String tenantId);

    RolesUserDO findRolUserByEmail(String email,Long id);

    RolesUserDO findRolUserByEmailAndTenantId(String email, Long id, String tenantId);

    void updateRoleUser(RolesUserDO role);

    UserTO getUserById(Long idUser);

}
