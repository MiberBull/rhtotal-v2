package mx.com.axity.web.rest;

import mx.com.axity.commons.to.AnswerLoginMobileTO;
import mx.com.axity.commons.to.AnswerLoginTO;
import mx.com.axity.commons.to.resetPasswordTO;
import mx.com.axity.services.facade.impl.LoginFacadeImpl;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("login")
public class LoginController {

    final static Logger LOG = LogManager.getLogger(LoginController.class);

    @Autowired
    LoginFacadeImpl loginFacadeImpl;

    @RequestMapping(value = "/loginWeb" , method = RequestMethod.POST , produces = "application/json")
    public ResponseEntity<AnswerLoginTO> loginWeb(@RequestBody  AnswerLoginTO answerLoginTO){
        LOG.info("init loginWeb " + answerLoginTO.toString());
        var login = loginFacadeImpl.loginWeb(answerLoginTO);
        LOG.info("loginWeb finalizado correctamente");
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @RequestMapping(value = "/loginMobile" , method = RequestMethod.POST , produces = "application/json")
    public ResponseEntity<AnswerLoginMobileTO> loginMobile(@RequestBody  AnswerLoginMobileTO answerLoginMobileTO){
        LOG.info("init loginMobile " + answerLoginMobileTO.toString());
        var login = loginFacadeImpl.loginMobile(answerLoginMobileTO);
        LOG.info("loginMobile finalizado correctamente");
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @RequestMapping(value = "/getUserById", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Boolean> getUserById(@RequestBody resetPasswordTO reset){
        LOG.info("Init getUserById");
        var userById = this.loginFacadeImpl.getUserById(reset);
        return new ResponseEntity<>(userById,HttpStatus.OK);
    }
}
