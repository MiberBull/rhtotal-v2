package mx.com.axity.services.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.exceptions.SicoConnectionException;
import mx.com.axity.commons.to.*;
import mx.com.axity.commons.util.AES;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.commons.util.SHA;
import mx.com.axity.model.*;
import mx.com.axity.persistence.*;
import mx.com.axity.services.service.IuserService;
import org.apache.juli.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.IntStream;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class userServiceImpl implements IuserService {

    static final Logger LOG = LogManager.getLogger(userServiceImpl.class);
    public static final long LEVEL = 0L;

    @Autowired
    UserDAO userDAO;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CodeUserDAO codeUserDAO;

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    EmployeeComplementaryDAO employeeComplementaryDAO;

    @Autowired
    EmployeeAddressDAO employeeAddressDAO;

    @Autowired
    CodeResetDAO codeResetDAO;

    @Autowired
    ParameterDAO parameterDAO;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public ConfirmationTO createUser(UserDataTO userData) {
        ConfirmationTO confirmation = new ConfirmationTO(
                Constants.CONFIRMATION_TITLE,
                Constants.CONFIRMATION_DESCRIPTION
        );

        try {

            String passwordDecoded = AES.decrypt(userData.getPassword());
            String passwordConfirmedDecoded = AES.decrypt(userData.getPasswordConfirmed());

            if (!validateEmail(userData.getUser())) {
                LOG.info(String.format(
                        "Error al validar correo %s no se cumple con el formato",
                        userData.getUser()
                ));
                throw new Exception(Constants.INVALID_EMAIL_MESSAGE);
            }

            Optional<UserDO> currentUser = userDAO.findOptionalByEmail((userData.getUser()));

            if (currentUser.isPresent()) {
                LOG.info(String.format(
                        "Error al verificar que no exista %s en la base de datos",
                        userData.getUser()
                ));
                throw new Exception(Constants.EMAIL_EXIST_MESSAGE);
            }

            if (!passwordDecoded.equals(passwordConfirmedDecoded)) {
                LOG.info(
                        String.format("Error al verificar la igualdad de las contraseñas")
                );
                throw new Exception(Constants.NO_EQUAL_PASSWORD);
            }

            if (userData.getPassword().length() < 8) {
                LOG.info(
                        String.format("Error al verificar la especificación de la contraseña")
                );
                throw new Exception(Constants.INVALID_PASSWORD_LENGTH);
            }

            Optional<CodeUserDO> isAlreadyACode = codeUserDAO.findOptionalByUser(userData.getUser());

            if (!isAlreadyACode.isPresent()){
                // creando el codigo de seguridad

                CodeUserDO newUserMeta = new CodeUserDO();

                newUserMeta.setCode(this.createCode());
                newUserMeta.setUser(userData.getUser());
                newUserMeta.setLastUserModifier(Constants.DEFAULT_SYSTEM_USER);
                newUserMeta.setLastModification(LocalDateTime.now());
                newUserMeta.setCreationUser(Constants.DEFAULT_SYSTEM_USER);
                newUserMeta.setCreationDate(LocalDateTime.now());
                newUserMeta.setActive(true);
                newUserMeta.setStatusCode("A");
                newUserMeta.setUserPassword(SHA.encrypt(passwordDecoded));

                codeUserDAO.save(newUserMeta);

                String template = getEmailTemplate(Constants.TEMPLATE_CONFIRM_ACCOUNT_EMIAL);

                String templateReady = createConfirmAccountBodyEmail(template, newUserMeta);

                sendEmail(userData.getUser(), templateReady,Constants.TEMPLATE_CONFIRM_ACCOUNT_EMIAL);
            } else {
                String template = getEmailTemplate(Constants.TEMPLATE_CONFIRM_ACCOUNT_EMIAL);

                String templateReady = createConfirmAccountBodyEmail(template, isAlreadyACode.get());

                sendEmail(userData.getUser(), templateReady,Constants.TEMPLATE_CONFIRM_ACCOUNT_EMIAL);
            }

        } catch (Exception e) {
            LOG.info(String.format("Error dentro de SERVICE.createUser: %s", e.getMessage()));
            LOG.info(e.toString());
            throw new BusinessException(e.getMessage(), e);
        }

        return confirmation;

    }

    @Override
    @Transactional
    public void confirmUser(UserConfirmationDataTO userData) {

        UserDO userToSave = new UserDO();

        try {

            Optional<CodeUserDO> currentUser = codeUserDAO.findOptionalByUser(userData.getUser());

            if (!currentUser.isPresent()) {
                LOG.info(String.format(
                        "Error dentro de service.confirmUser: %s no esta en la base de datos",
                        userData.getUser()
                ));
                throw new Exception(Constants.EMAIL_NOT_EXIST);
            }

            CodeUserDO nextUser = currentUser.get();

            if (!userData.getCode().equals(nextUser.getCode())) {
                LOG.info(String.format(
                        "Error dentro de service.confirmUser: %s el código es incorrecto",
                        userData.getCode()
                ));
                throw new Exception(Constants.INVALID_CODE);
            }

            if (!nextUser.getStatusCode().equals("A")) {
                LOG.info(String.format(
                        "Error dentro de service.confirmUser: %s se intentó utilizar un código anterior",
                        currentUser.get().getCode()
                ));
            }


            SicoResponseWrapperTO sicoData = getDataFromSico(userData.getUser());
            if (sicoData.getStatus().equals(HttpStatus.NOT_FOUND) || sicoData.getResponse() == null) {

                userToSave.setActive(true);
                userToSave.setCreationDate(LocalDateTime.now());
                userToSave.setCreationUser(Constants.DEFAULT_SYSTEM_USER);
                userToSave.setPassword(nextUser.getUserPassword());
                userToSave.setEmail(nextUser.getUser());
                userToSave.setUserType("EX");
                userToSave.setUserStatus("A");
                userToSave.setLevel(LEVEL);
                userToSave.setLastUserModifier(Constants.DEFAULT_SYSTEM_USER);
                userToSave.setLastModification(LocalDateTime.now());

                userDAO.save(userToSave);

                nextUser.setStatusCode("I");

                codeUserDAO.save(nextUser);

            } else if (sicoData.getStatus().equals(HttpStatus.OK)) {

                userToSave.setActive(true);
                userToSave.setCreationDate(LocalDateTime.now());
                userToSave.setCreationUser(Constants.DEFAULT_SYSTEM_USER);
                userToSave.setPassword(nextUser.getUserPassword());
                userToSave.setEmail(nextUser.getUser());
                userToSave.setUserType("IN");
                userToSave.setUserStatus("A");
                userToSave.setLevel(LEVEL);
                userToSave.setLastUserModifier(Constants.DEFAULT_SYSTEM_USER);
                userToSave.setLastModification(LocalDateTime.now());

                userDAO.save(userToSave);

                parseEmployee(sicoData.getResponse(), userToSave);

                nextUser.setStatusCode("I");

                codeUserDAO.save(nextUser);
            }

        } catch (Exception e) {
            LOG.info(String.format("Error dentro de SERVICE.confirmUser: %s", e.getMessage()));
            LOG.info(e.toString());
            throw new BusinessException(e.getMessage(), e);
        }

    }

    private String decodeCharacter(String data){
        try {
            return URLDecoder.decode(data, "UTF-8");
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public void createResetRequest(ResetRequestTO userData) {
        try {

            Optional<CodeResetDO> thereIsAToken = codeResetDAO.findOptionalByEmail(
                    userData.getUser()
            );

            //Optional<UserDO> user = userDAO.findOptionalByEmail(userData.getUser());
            Optional<UserDO> user = userDAO.findOptionalByEmailActive(userData.getUser());
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

            sendEmail(resetToSave.getEmail(), templateReady,Constants.TEMPLATE_CHANGE_PASSWORD_EMAIL);

        } catch (Exception e) {
            LOG.info(String.format("Error dentro de service.confirmUser: %s", e.getMessage()));
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

            Optional<UserDO> currentUser = userDAO.findOptionalByEmail(currentCodeRequest.get().getEmail());

            currentUser.get().setPassword(encryptedPass);

            userDAO.save(currentUser.get());

            codeResetDAO.delete(currentCodeRequest.get());

            String template = getEmailTemplate(Constants.TEMPLATE_CONFIRM_EMAIL);

            String templateReady = createConfirmResetBodyEmail(template, currentUser.get());


            sendEmail(currentUser.get().getEmail(), templateReady,Constants.TEMPLATE_CONFIRM_EMAIL);
        } catch (Exception e) {
            LOG.info(String.format("Error dentro de service.confirmReset: %s", e.getMessage()));
            throw new BusinessException(e.getMessage(), e);
        }
    }



    private boolean validateEmail (String userEmail) {
        Matcher matcher;
        matcher = Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(userEmail);
        return matcher.find();
    }

    private void sendEmail(String email, String template, String nameTemplate) {
        try {
            EmailTO emailParams = new EmailTO(email, template);
            emailParams.setNameTemplate(nameTemplate);

            UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(
                    Constants.EMAIL_SERVICE_ENDPOINT
            ).queryParam("shouldbeparse", true);
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<EmailTO> entity = new HttpEntity<>(emailParams, headers);


            ResponseEntity<String> response = restTemplate.exchange(
                    uri.toUriString(), HttpMethod.POST, entity, String.class, emailParams
            );


        } catch (Exception e) {
            LOG.info(String.format("Error dentro de SERVICE.sendEmail: %s", e.getMessage()));
            LOG.info(e.toString());
            throw new BusinessException(e.getMessage(), e);
        }
    }

    private String getEmailTemplate(String layout) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(Constants.SECURITY_SERVICE_ENDPOINT)
                .queryParam("parameter", layout);
        ResponseEntity<String> response = restTemplate.getForEntity(uri.toUriString(), String.class);
        return response.getBody();
    }

    private String createCode () {

        List<Integer> ints = IntStream.range(1, 9).boxed().collect(toList());
        Collections.shuffle(ints);
        List<Integer> rawCode = ints.subList(0, 4);

        String code = "";

        for (Integer number: rawCode) {
            code = code + number.toString();
        }

        return code;
    }

    private ResponseEntity<String> makeSicoCall(String uri) {

        return restTemplate.getForEntity(
                uri, String.class
        );

    }

    private SicoResponseWrapperTO serializeSicoCall(String rawJson, HttpStatus code) throws IOException {
        SicoResponseWrapperTO responseStatus = new SicoResponseWrapperTO();


        ObjectMapper objectMapper = new ObjectMapper();

        SicoResponseRawTO data = objectMapper.readValue(rawJson, SicoResponseRawTO.class);


        if (data.getData().size() > 0) {
            responseStatus.setResponse(data.getData().get(0));
        } else {
            responseStatus.setResponse(null);
        }


        responseStatus.setStatus(code);

        return responseStatus;
    }



    private SicoResponseWrapperTO getDataFromSico(String email) {
        SicoResponseWrapperTO responseStatus = new SicoResponseWrapperTO();

        try {
            UriComponentsBuilder uri = UriComponentsBuilder
                    .fromUriString(Constants.SICO_DATA_ENDPOINT)
                    .queryParam("email", email);
            ResponseEntity<String> response = makeSicoCall(uri.toUriString());

            String responseBody =  decodeCharacter(response.getBody());

            responseStatus = serializeSicoCall(responseBody, response.getStatusCode());
            return responseStatus;
        } catch (HttpClientErrorException e) {
            LOG.info(String.format("Error dentro de SERVICE.getDataFromSico: %s", e.getMessage()));
            LOG.info(e.toString());
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                responseStatus.setStatus(HttpStatus.NOT_FOUND);
                responseStatus.setResponse(null);
                return responseStatus;
            }
            throw new SicoConnectionException(Constants.SICO_FAIL_ERROR, e);

        } catch (Exception e) {
            LOG.info(String.format("Error dentro de SERVICE.getDataFromSico: %s", e.getMessage()));
            LOG.info(e.toString());
            throw new SicoConnectionException(Constants.SICO_FAIL_ERROR, e);
        }
    }

    private void parseEmployee(SicoResponseTO response, UserDO user)  {
        EmployeeDO employeeDO = new EmployeeDO();
        EmployeeComplementaryDO complementaryDO = new EmployeeComplementaryDO();
        EmployeeAddressDO addressDO = new EmployeeAddressDO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String date = response.getPersonal().getFechaNacimiento();
        LocalDateTime parsedBirthDay = LocalDateTime.parse(date + " 00:00:00", formatter);
        employeeDO.setActive(true);
        employeeDO.setCreationDate(LocalDateTime.now());
        employeeDO.setLastModification(LocalDateTime.now());
        employeeDO.setCreationUser(Constants.DEFAULT_SYSTEM_USER);
        employeeDO.setName(response.getPersonal().getNombres());
        employeeDO.setLastName(response.getPersonal().getApellidoPaterno());
        employeeDO.setLastMName(response.getPersonal().getApellidoMaterno());
        if (response.getPersonal().getEstadoCivil() == null) {
            employeeDO.setCvilStatus("");
        } else {
            employeeDO.setCvilStatus(response.getPersonal().getEstadoCivil());
        }

        employeeDO.setGender(response.getPersonal().getGenero());
        employeeDO.setLastUserModifier(Constants.DEFAULT_SYSTEM_USER);
        employeeDO.setUser(user);


        complementaryDO.setActive(true);
        complementaryDO.setCreationDate(LocalDateTime.now());
        complementaryDO.setCurp(response.getPersonal().getCurp());


        if (response.getPersonal().getPaisNacimiento() == null) {
            complementaryDO.setBirthCountry("");
        } else {
            complementaryDO.setBirthCountry(response.getPersonal().getPaisNacimiento());
        }

        complementaryDO.setRfc(response.getPersonal().getRfc());
        complementaryDO.setNss(response.getPersonal().getNss());

        if (response.getPersonal().getEmailPersonal() == null) {
            complementaryDO.setEmail("");
        } else {
            complementaryDO.setEmail(response.getPersonal().getEmailPersonal());
        }

        if(response.getPersonal().getEmailEmpresa() == null) {
            complementaryDO.setEmailClient("");
        } else {
            complementaryDO.setEmailClient(response.getPersonal().getEmailEmpresa());
        }

        if (response.getPersonal().getNumCelular() == null) {
            complementaryDO.setPhone("");
        } else {
            complementaryDO.setPhone(response.getPersonal().getNumCelular());
        }

        if (response.getPersonal().getPermisoTrabajo() == null) {
            complementaryDO.setWorkPermit("");
        } else {
            complementaryDO.setWorkPermit(response.getPersonal().getPermisoTrabajo());
        }

        if (response.getPersonal().getNumPasaporte() == null) {
            complementaryDO.setPassportNumber("");
        } else {
            complementaryDO.setPassportNumber(response.getPersonal().getNumPasaporte());
        }


        complementaryDO.setPhotography("");


        complementaryDO.setBirthDate(parsedBirthDay);
        complementaryDO.setBirthState(response.getPersonal().getEstadoNacimiento());
        complementaryDO.setLastUserModifier(Constants.DEFAULT_SYSTEM_USER);
        complementaryDO.setLastModification(LocalDateTime.now());
        complementaryDO.setCreationUser(Constants.DEFAULT_SYSTEM_USER);
        complementaryDO.setCreationDate(LocalDateTime.now());


        addressDO.setStreet(response.getDireccion().getCalle());
        addressDO.setInteriorNumber(response.getDireccion().getNumeroInterior());
        addressDO.setOutDoorNumber(response.getDireccion().getNumeroExterior());
        addressDO.setColony(response.getDireccion().getColonia());
        addressDO.setPostalCode(response.getDireccion().getCp());
        addressDO.setCity(response.getDireccion().getDelegacionMunicipio());
        addressDO.setState(response.getDireccion().getEstado());
        addressDO.setLastUserModifier(Constants.DEFAULT_SYSTEM_USER);
        addressDO.setCreationUser(Constants.DEFAULT_SYSTEM_USER);
        addressDO.setLastModification(LocalDateTime.now());
        addressDO.setCreationDate(LocalDateTime.now());
        addressDO.setActive(true);


        employeeDAO.save(employeeDO);

        complementaryDO.setEmployee(employeeDO);
        addressDO.setEmployee(employeeDO);

        employeeComplementaryDAO.save(complementaryDO);
        employeeAddressDAO.save(addressDO);

    }

    private String generateResetToken () {
        UUID token = UUID.randomUUID();
        return token.toString();
    }

    private String createResetBodyEmail(String template, CodeResetDO resetToSave) {
        String body;

        ParameterDO parameter = parameterDAO.findByName("domainNameM");

        body = template
                .replace(":host", parameter.getValue())
                .replace(":token", resetToSave.getToken())
                .replace(":user", (resetToSave.getEmail()+"&type=2"));

        return body;
    }

    private String createConfirmResetBodyEmail(String template, UserDO user) {
        String body;

        ParameterDO parameter = parameterDAO.findByName("telEmpHelpM");

        if (user.getEmployee() == null) {
            body = template
                    .replace(":nombre", user.getEmail());
        } else {
            body = template
                    .replace(":nombre", String.format(
                            "%s %s %s",
                            user.getEmployee().getName(),
                            user.getEmployee().getLastName(),
                            user.getEmployee().getLastMName()
                    ))
                    .replace(":telefono", parameter.getValue());
        }

        return body;
    }

    private String createConfirmAccountBodyEmail(String template, CodeUserDO newUserMeta) {
        String body;

        body = template.replace(":codigo", newUserMeta.getCode());

        return body;
    }

    @Override
    public Map<String,Object> getCredentialInfo(Long idUser) {
        try {
            var credentialInfo = this.userDAO.getInfoForCredential(idUser);
            if ( !credentialInfo.isPresent() ) {
                throw new Exception(Constants.RESPONSE_EMPTY);
            }
            Map<String,Object> response = new HashMap<>();
            response.put("numberEmployee",((Object[])credentialInfo.get())[0]);
            response.put("since",((Object[]) credentialInfo.get())[1]);
            response.put("name",((Object[]) credentialInfo.get())[2]);
            response.put("lastName",((Object[]) credentialInfo.get())[3]);
            response.put("mLastName",((Object[]) credentialInfo.get())[4]);
            response.put("imageBase64",((Object[]) credentialInfo.get())[5]);
            return response;
        }catch (Exception e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

}
