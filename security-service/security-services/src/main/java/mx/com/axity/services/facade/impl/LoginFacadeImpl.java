package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.*;
import mx.com.axity.commons.util.AES;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.commons.util.SHA;
import mx.com.axity.commons.util.UtilAnswerLogin;
import mx.com.axity.commons.util.enumerators.EnumMessageSystem;
import mx.com.axity.commons.util.enumerators.EnumUserStatus;
import mx.com.axity.model.RolesUserDO;
import mx.com.axity.model.UserDO;
import mx.com.axity.persistence.ParameterDAO;
import mx.com.axity.services.facade.ILoginFacade;
import mx.com.axity.services.service.IEmailService;
import mx.com.axity.services.service.ILoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class LoginFacadeImpl implements ILoginFacade {

    static final Logger LOG = LogManager.getLogger(LoginFacadeImpl.class);

    @Autowired
    ILoginService loginService;

    @Autowired
    ModelMapper modelMapper;


    @Autowired
    ParameterDAO parameterDao;

    @Override
    public AnswerLoginMobileTO loginMobile(AnswerLoginMobileTO login) {
        AnswerLoginMobileTO answerLoginMobileTO = new AnswerLoginMobileTO();
        UserDO user;

        try {
            Optional.ofNullable(login).map(AnswerLoginMobileTO::getUser).map(UserTO::getEmail).orElseThrow();
            Optional.of(login).map(AnswerLoginMobileTO::getUser).map(UserTO::getPassword).orElseThrow();

            if ((user = loginService.findUserByEmail(login.getUser().getEmail())) == null) {
                return UtilAnswerLogin.loginMobileResult(EnumMessageSystem.WRONG_USER, answerLoginMobileTO);
            }

            if (EnumUserStatus.NEW_STATUS.getStatus().equalsIgnoreCase(user.getUserStatus())) {
                return  UtilAnswerLogin.loginMobileResult(EnumMessageSystem.USER_NEW, answerLoginMobileTO);
            }

            if (EnumUserStatus.BLOCKED_STATUS.getStatus().equalsIgnoreCase(user.getUserStatus())) {
                var intents = validatorNumIntentosBlock();
                var respuestaBlock = UtilAnswerLogin.loginMobileResult(EnumMessageSystem.USER_BLOCKED, answerLoginMobileTO);
                respuestaBlock.setMessage(respuestaBlock.getMessage().replace(":N",intents));
                return respuestaBlock;
            }

            if (login.getBlock() && EnumUserStatus.ACTIVE_STATUS.getStatus().equalsIgnoreCase(user.getUserStatus())) {

                user.setUserStatus(EnumUserStatus.BLOCKED_STATUS.getStatus());
                user.setLastModification(LocalDateTime.now());
                this.loginService.updateUser(user);
                var response = UtilAnswerLogin.loginMobileResult(EnumMessageSystem.USER_BLOCKED, answerLoginMobileTO);
                var nunInten= this.validatorNumIntentosBlock();
                response.setMessage(response.getMessage().replace(":N",nunInten));
                return response;

            }

            var pwd_decrypt = AES.decrypt( login.getUser().getPassword() );
            var pwd_encrypt_sha = SHA.encrypt( pwd_decrypt );

            if (!user.getPassword().equals( pwd_encrypt_sha ) ) {
                return  UtilAnswerLogin.loginMobileResult(EnumMessageSystem.WRONG_PASSWORD, answerLoginMobileTO);
            }

            answerLoginMobileTO.setUser(this.modelMapper.map(user, UserTO.class));

            return UtilAnswerLogin.loginMobileResult(EnumMessageSystem.CORRECT_USER, answerLoginMobileTO);

        } catch (Exception e) {
            LOG.info("Error al hacer login [" + e.getMessage() + "]");
            throw new BusinessException(Constants.CONTROLLED_MENSSAGE, e);
        }
    }

    @Override
    public AnswerLoginTO loginWeb(AnswerLoginTO login) {
        AnswerLoginTO answerLoginTO = new AnswerLoginTO();
        RolesUserDO role;
        try {
            Optional.ofNullable(login).map(AnswerLoginTO::getUser).map(RolesUserTO::getEmail).orElseThrow();
            Optional.of(login).map(AnswerLoginTO::getUser).map(RolesUserTO::getPassword).orElseThrow();

            if ((role = loginService.findRolUserByEmail(login.getUser().getEmail(),null)) == null) {
                return UtilAnswerLogin.loginWebResult(EnumMessageSystem.WRONG_USER, answerLoginTO);
            }

            if (EnumUserStatus.INACTIVE_STATUS.getStatus().equalsIgnoreCase(role.getStatus())) {
                return  UtilAnswerLogin.loginWebResult(EnumMessageSystem.WRONG_USER, answerLoginTO);
            }

            if (EnumUserStatus.NEW_STATUS.getStatus().equalsIgnoreCase(role.getStatus())) {
                return  UtilAnswerLogin.loginWebResult(EnumMessageSystem.USER_NEW, answerLoginTO);
            }

            if (EnumUserStatus.BLOCKED_STATUS.getStatus().equalsIgnoreCase(role.getStatus())) {
                var intents = validatorNumIntentosBlock();
                var respuestaBlock =UtilAnswerLogin.loginWebResult(EnumMessageSystem.USER_BLOCKED, answerLoginTO);
                respuestaBlock.setMessage(respuestaBlock.getMessage().replace(":N",intents));
                return respuestaBlock;
            }

            if (login.getBlock() && EnumUserStatus.ACTIVE_STATUS.getStatus().equalsIgnoreCase(role.getStatus())) {
                role.setStatus(EnumUserStatus.BLOCKED_STATUS.getStatus());
                role.setLastModification(LocalDateTime.now());
                this.loginService.updateRoleUser(role);
                var respuesta = UtilAnswerLogin.loginWebResult(EnumMessageSystem.USER_BLOCKED, answerLoginTO);
                var nunInten= this.validatorNumIntentosBlock();
                respuesta.setMessage(respuesta.getMessage().replace(":N",nunInten));
                return respuesta;
            }

            var pwd_decrypt = AES.decrypt( login.getUser().getPassword() );
            var pwd_encrypt_sha = SHA.encrypt( pwd_decrypt );

            if (!role.getPassword().equals( pwd_encrypt_sha )) {
                return UtilAnswerLogin.loginWebResult(EnumMessageSystem.WRONG_PASSWORD, answerLoginTO);
            }

            answerLoginTO.setUser(this.modelMapper.map(role, RolesUserTO.class));

            return UtilAnswerLogin.loginWebResult(EnumMessageSystem.CORRECT_USER, answerLoginTO);

        } catch (Exception e) {
            LOG.info("Error al hacer login [" + e.getMessage() + "]");
            throw new BusinessException(Constants.CONTROLLED_MENSSAGE, e);
        }
    }

    private String validatorNumIntentosBlock(){
        var time = parameterDao.findByNameParameter("timeUnLock").toString();
       return time;
    }


    @Override
    public Boolean getUserById(resetPasswordTO reset) {
        try{

            var userById = this.loginService.getUserById(reset.getIdUser());
            String decrypt = AES.decrypt(reset.getPassOld());
            String encrypt = SHA.encrypt(decrypt);

            if (encrypt.equals(userById.getPassword())){
                userById.setPassword(SHA.encrypt(AES.decrypt(reset.getPassNew())));
                this.loginService.updateUser(this.modelMapper.map(userById,UserDO.class));
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }
    }


}
