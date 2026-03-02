package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.UserTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.RolesUserDO;
import mx.com.axity.model.UserDO;
import mx.com.axity.persistence.*;
import mx.com.axity.services.service.ILoginService;
import org.apache.commons.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

import static mx.com.axity.commons.util.Constants.LAYOUT_NEW_USER;
import static mx.com.axity.commons.util.Constants.PARAM_PASS;
import static mx.com.axity.commons.util.Constants.PARAM_USER;

@Service
public class LoginServiceImpl implements ILoginService {



    @Autowired
    UserLoginDAO userLoginDAO;

    @Autowired
    RolesUserDAO rolesUserDAO;

    @Autowired
    ConfigEmailDAO configEmailDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void updateUser(UserDO user) {
        this.userLoginDAO.save(user);
    }

    @Override
    public UserDO findUserByEmail(String email) {
        return this.userLoginDAO.findByEmail(email);
    }

    @Override
    public RolesUserDO findRolUserByEmail(String email,Long id) {
        return this.rolesUserDAO.findByEmail(email,id).size() > 0  ?this.rolesUserDAO.findByEmail(email,id).get(0) :null;
    }

    @Override
    public void updateRoleUser(RolesUserDO role) {
        this.rolesUserDAO.save(role);
    }

    @Override
    public UserTO getUserById(Long idUser) {
        return this.modelMapper.map(this.userLoginDAO.findById(idUser).get(),UserTO.class);
    }
}
