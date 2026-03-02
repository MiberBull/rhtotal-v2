package mx.com.axity.services.service.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.*;
import mx.com.axity.commons.util.AES;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.commons.util.SHA;
import mx.com.axity.model.CodeResetDO;
import mx.com.axity.model.ParameterDO;
import mx.com.axity.model.RolesUserDO;
import mx.com.axity.model.UserDO;
import mx.com.axity.persistence.*;
import mx.com.axity.services.service.IEmailService;
import mx.com.axity.services.service.IRolUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class RolUserServiceImpl implements IRolUserService {

    static final Logger LOG = LogManager.getLogger(RolUserServiceImpl.class);

    @Autowired
    RolesUserDAO rolesUserDAO;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CatalogoRolDAO catalogoRolDAO;

    @Autowired
    CodeResetDAO codeResetDAO;

    @Autowired
    ParameterDAO parameterDAO;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ConfigEmailDAO configEmailDAO;

    @Autowired
    IEmailService emailService;

    @Override
    public RoleCompoundTO<RolesUserTO> getRole(RoleCompoundTO<RolesUserTO> role) {
        var queryRol = this.rolesUserDAO.findById(role.getRoleList().get(0).getIdRolAssig());
        var rolCompoundResult = new RoleCompoundTO<RolesUserTO>();
        var toList = new ArrayList<RolesUserTO>();
        toList.add(this.modelMapper.map(queryRol.get(), RolesUserTO.class));
        rolCompoundResult.setRoleList(toList);
        return rolCompoundResult;
    }

    @Override
    public Boolean saveOrUpdateRole(RolesUserTO role) {
            this.rolesUserDAO.save(this.modelMapper.map(role, RolesUserDO.class));
            return Boolean.TRUE;
    }

    @Override
    public RoleCompoundTO<CatalogoRolTO> getAllCatalogue() {
        var catalogoRol = new RoleCompoundTO<CatalogoRolTO>();
        catalogoRol.setRoleList(this.modelMapper.map(this.catalogoRolDAO.findAll(), new TypeToken<List<CatalogoRolTO>>() {
        }.getType()));
        return catalogoRol;
    }

    @Override
    public List<RolesUserTO> getPagedRole(int page) {
        var pageUsers = rolesUserDAO.findAllByOrderByLastModificationDesc(PageRequest.of(page,10));
        return this.modelMapper.map(pageUsers.getContent(),new TypeToken<List<RolesUserTO>>(){}.getType());
    }

    @Override
    public RoleCountRowTO getNumberRow() {
        RoleCountRowTO rowTO = new RoleCountRowTO();
        rowTO.setFilas(this.rolesUserDAO.getNumberRow());
        return rowTO;
    }


    @Override
    public void createResetRequest(ResetRequestTO userData) {
        try {

            Optional<CodeResetDO> thereIsAToken = codeResetDAO.findOptionalByEmail(
                    userData.getUser()
            );

            Optional<RolesUserDO> user = rolesUserDAO.findByEmailReset(userData.getUser());

            if (!user.isPresent()) {
                LOG.info(String.format(
                        "Error dentro de service.createResetRequest: %s no esta en la base de datos",
                        userData.getUser()
                ));
                throw new Exception(Constants.EMAIL_NOT_EXIST);

            }

            if (thereIsAToken.isPresent()) {
                codeResetDAO.delete(thereIsAToken.get());
            }

            CodeResetDO resetToSave = new CodeResetDO();
            resetToSave.setEmail(userData.getUser());
            resetToSave.setToken(generateResetToken());

            codeResetDAO.save(resetToSave);

            String template = getEmailTemplate(Constants.TEMPLATE_CHANGE_PASSWORD_EMAIL);

            String templateReady = createResetBodyEmail(template, resetToSave);

            //CompletableFuture.runAsync(() -> sendEmail(resetToSave.getEmail(), templateReady));
            sendEmail(resetToSave.getEmail(), templateReady,Constants.TEMPLATE_CHANGE_PASSWORD_EMAIL);
        } catch (Exception e) {
            LOG.info(String.format("Error dentro de service.confirmUser: %s", e.getMessage()));
            throw new BusinessException(e.getMessage(), e);
        }

    }

    private String getEmailTemplate(String layout) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(Constants.SECURITY_SERVICE_ENDPOINT)
                .queryParam("parameter", layout);
      //  ResponseEntity<String> response = restTemplate.getForEntity(uri.toUriString(), String.class);
        String response = configEmailDAO.findByNameParemeter(layout);// restTemplate.getForEntity(uri.toUriString(), String.class);
        return response;
    }
    private String generateResetToken () {
        UUID token = UUID.randomUUID();
        return token.toString();
    }

    private String createResetBodyEmail(String template, CodeResetDO resetToSave) {
        String body;

        String parameter = parameterDAO.findByNameParameter("domainNameM");

        body = template
                .replace(":host", parameter)
                .replace(":token", resetToSave.getToken())
                .replace(":user", (resetToSave.getEmail()+"&type=1"));

        return body;
    }

    private void sendEmail(String email, String template,String nameTemplate) {
        try {
            EmailContentTO emailParams = new EmailContentTO(email,template,nameTemplate,"");
            UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(
                    Constants.EMAIL_SERVICE_ENDPOINT
            ).queryParam("shouldbeparse", true);

            this.emailService.sendMail(emailParams,true);


        } catch (Exception e) {
            LOG.info(String.format("Error dentro de SERVICE.sendEmail: %s", e.getMessage()));
            LOG.info(e.toString());
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public void confirmReset(ResetConfirmationTO userData) {
        try {
            String decryptedNewPass = AES.decrypt(userData.getNewPassword());
            String decryptedNewPoassConfirmation =
                    AES.decrypt(userData.getNewPasswordConfirmed());

            if (!decryptedNewPass.equals(decryptedNewPoassConfirmation)) {
                LOG.info("las contraseñas no coinciden");
                throw new Exception(Constants.NO_EQUAL_PASSWORD);
            }

            Optional<CodeResetDO> currentCodeRequest = codeResetDAO.findOptionalByToken(userData.getToken());

            if (!currentCodeRequest.isPresent()) {
                LOG.info("No existe el token");
                throw new Exception(Constants.INVALID_TOKEN);
            }

            if (!userData.getUser().equals(currentCodeRequest.get().getEmail())) {
                LOG.info("el token recibido no se encuentra asociado a este usuario");
                throw new Exception(Constants.USER_WITH_NO_TOKEN);
            }

            String encryptedPass = SHA.encrypt(decryptedNewPass);

            Optional<RolesUserDO> UserDAORol = rolesUserDAO.findByEmailReset(userData.getUser());
            UserDAORol.get().setPassword(encryptedPass);
            UserDAORol.get().setStatus("A");

            rolesUserDAO.save(UserDAORol.get());

            codeResetDAO.delete(currentCodeRequest.get());

            String template = getEmailTemplate(Constants.TEMPLATE_CONFIRM_EMAIL);

            String templateReady = createConfirmResetBodyEmail(template, UserDAORol.get());

            sendEmail(UserDAORol.get().getEmail(), templateReady,Constants.TEMPLATE_CONFIRM_EMAIL);
        } catch (Exception e) {
            LOG.info(String.format("Error dentro de service.confirmReset: %s", e.getMessage()));
            throw new BusinessException(e.getMessage(), e);
        }
    }

    private String createConfirmResetBodyEmail(String template, RolesUserDO user) {
        String body;

        String parameter = parameterDAO.findByNameParameter("telEmpHelpM");

        body = template
                    .replace(":nombre", String.format(
                            "%s %s %s",
                            user.getName(),
                            user.getLastName(),
                            user.getmLastName()
                    ))
                    .replace(":telefono", parameter);


        return body;
    }
}
