package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.*;
import mx.com.axity.commons.util.AES;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.commons.util.SHA;
import mx.com.axity.services.facade.IRolUserFacade;
import mx.com.axity.services.service.IEmailService;
import mx.com.axity.services.service.ILoginService;
import mx.com.axity.services.service.IRoleExcelService;
import mx.com.axity.services.service.IRolUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class RolUserFacadeImpl implements IRolUserFacade {

    static final Logger LOG = LogManager.getLogger(ParameterFacadeImpl.class);

    @Autowired
    IRolUserService rolUserServise;

    @Autowired
    IRoleExcelService<RolesUserTO> genericExcel;

    @Autowired
    ILoginService loginService;

    @Autowired
    IEmailService emailService;

    @Override
    public RoleCompoundTO<RolesUserTO> getRole(RoleCompoundTO<RolesUserTO> role) {
        try {
            Optional.ofNullable(role).map(RoleCompoundTO::getRoleList).map(t -> t.get(0).getIdRolAssig() > 0).orElseThrow();
            return this.rolUserServise.getRole(role);
        } catch (Exception e) {
            LOG.info("Error al obtener rol[" + e.getMessage() + "]");
            throw new BusinessException(Constants.CONTROLLED_MENSSAGE, e);

        }

    }

    @Override
    public Boolean saveOrUpdateRole(RoleCompoundTO<RolesUserTO> role) {
        try {
            Optional.ofNullable(role).map(RoleCompoundTO::getRoleList).orElseThrow();
            if (role.getRoleList().get(0).getIdRolAssig() == null) {
                if (this.loginService.findRolUserByEmail(role.getRoleList().get(0).getEmail(),null) != null) {
                    return Boolean.FALSE;
                }
                var pwd_decrypt = AES.decrypt( role.getRoleList().get(0).getPassword() );
                var pwd_encrypt_sha = SHA.encrypt( pwd_decrypt );

                role.getRoleList().get(0).setLastModification(LocalDateTime.now());
                role.getRoleList().get(0).setActive(Boolean.TRUE);
                role.getRoleList().get(0).setCreationDate(LocalDateTime.now());
                role.getRoleList().get(0).setPassword(pwd_encrypt_sha);
                role.setHtmlRole("layoutNewUser");
//                CompletableFuture.runAsync(() -> emailService.sendMail( new EmailContentTO(role.getRoleList().get(0).getEmail(),"", role.getHtmlRole(),""),false));
                emailService.sendMail( new EmailContentTO(role.getRoleList().get(0).getEmail(),"", role.getHtmlRole(),""),false);
                return this.rolUserServise.saveOrUpdateRole(role.getRoleList().get(0));
            }

            if ((this.loginService.findRolUserByEmail(role.getRoleList().get(0).getEmail(),role.getRoleList().get(0).getIdRolAssig())) != null) {
                return Boolean.FALSE;
            }
            role.getRoleList().get(0).setLastModification(LocalDateTime.now());
            role.getRoleList().get(0).setActive(Boolean.TRUE);
            //role.getRoleList().get(0).setStatus("A");
            return this.rolUserServise.saveOrUpdateRole(role.getRoleList().get(0));
        } catch (Exception e) {
            LOG.info("Error al guardar rol[" + e.getMessage() + "]");
            throw new BusinessException(Constants.CONTROLLED_MENSSAGE, e);
        }
    }

    @Override
    public RoleCompoundTO<CatalogoRolTO> getAllCatalogue() {
        try {
            return this.rolUserServise.getAllCatalogue();
        } catch (Exception e) {
            LOG.info("Error al obtener rol[" + e.getMessage() + "]");
            throw new BusinessException(Constants.CONTROLLED_MENSSAGE, e);
        }
    }

    @Override
    public ExcelFormatExportTO sendExcel() {
        try {
            return this.genericExcel.sendExcel();
        } catch (Exception e) {
            LOG.info("Error al enviar excel[" + e.getMessage() + "]");
            throw new BusinessException(Constants.CONTROLLED_MENSSAGE, e);
        }
    }

    @Override
    public List<RolesUserTO> getPagedRole(int page) {
        try {
            Optional.of(page).map(t -> t > 0).orElseThrow();
            return rolUserServise.getPagedRole(page);
        } catch (Exception e) {
            LOG.info("Error al paginar rol[" + e.getMessage() + "]");
            throw new BusinessException(Constants.CONTROLLED_MENSSAGE, e);
        }
    }

    @Override
    public RoleCountRowTO getNumberRow() {
        try {
           return this.rolUserServise.getNumberRow();
        }catch (Exception e){
            LOG.info("Error al obtener el numero de registros [" + e.getMessage() + "]");
            throw  new BusinessException(Constants.CONTROLLED_MENSSAGE, e);
        }
    }

    @Override
    public void createRequestReset(ResetRequestTO userData) {
        this.rolUserServise.createResetRequest(userData);
    }

    @Override
    public void confirmReset(ResetConfirmationTO userData) {
        this.rolUserServise.confirmReset(userData);
    }

}
