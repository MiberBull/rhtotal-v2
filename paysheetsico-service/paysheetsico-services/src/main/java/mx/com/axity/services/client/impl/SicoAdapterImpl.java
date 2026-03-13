package mx.com.axity.services.client.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.AnswerErrorTO;
import mx.com.axity.commons.to.AnswerSICOTO;
import mx.com.axity.commons.to.NotificaTransferenciaTO;
import mx.com.axity.commons.to.SicoAuthResponseTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.ParameterDO;
import mx.com.axity.persistence.ParameterDAO;
import mx.com.axity.services.client.SicoAdapter;
import mx.com.axity.services.service.impl.PaysheetSicoServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.google.gson.Gson;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Base64;

public class SicoAdapterImpl implements SicoAdapter {

    private RestTemplate sicoRestTemplate;

    {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(1500);
        factory.setReadTimeout(1500);
        sicoRestTemplate = new RestTemplate(factory);
    }
    private String apiUser = "";
    private String apiPassword = "";
    private String apiAuthEndpoint = Constants.SICO_AUTH_ENDPOINT;
    private String completedApiAuthEndpoint = "";
    private HttpHeaders headers = new HttpHeaders();
    private String currentToken = "";
    private String decryptKey = "";
    static final Logger LOG = LogManager.getLogger(PaysheetSicoServiceImpl.class);

    @Autowired
    private ParameterDAO parameterDAO;

    public String get(String url) {
        String payload = "";
        try {
            getAuthToken();

            ResponseEntity<String> encryptedPayload = makeApiCall(url);

            payload = decryptData(encryptedPayload.getBody());
        } catch (Exception e) {
            LOG.info(String.format(
                "Ocurrio un error en SICOADAPTER.get: %s: %s",
                e.getMessage(),
                e.toString()
            ));
            throw new BusinessException(e.getMessage(), e);
        }



        return decodeCharacter(payload);
    }



    protected ResponseEntity<String> makeApiCall(String url) {

        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(url);

        HttpEntity entity = new HttpEntity(this.headers);

        return sicoRestTemplate.exchange(uri.toUriString(), HttpMethod.GET, entity, String.class);
    }

    @Override
    public AnswerSICOTO employeeNotification(NotificaTransferenciaTO notificationTranferen) {
        getAuthToken();

        HttpHeaders headerFintech = new HttpHeaders();
        headerFintech.set("Content-Type", "application/json");
        headerFintech.set("Authorization", String.format("Bearer %s", this.currentToken));

        String url = "https://nominaenlanube.com:8085/api-nomen/v1/notifica_transferencia/";

        try {

            var request = new HttpEntity<>(notificationTranferen, headerFintech);

            var as = sicoRestTemplate.exchange(url, HttpMethod.POST, request, String.class);
            String answerCadena = as.getBody();
            //String cadena = "{\"idSolicitud\":1234,\"folioConfirmacio\":\"34567\",\"estatus\":\"aprobado\",\"descripcion\":\"correcto\"}";
            Gson g = new Gson();
            JsonObject respuesta = g.fromJson(decryptData(answerCadena), JsonObject.class);
            var jsonResponse =  respuesta.get("respuesta");
            AnswerSICOTO answerTO = g.fromJson(jsonResponse,AnswerSICOTO.class);

            return answerTO;


        } catch (HttpClientErrorException e) {
            e.getMessage();
            String as = e.getResponseBodyAsString();
            AnswerErrorTO answerErrorTO = new AnswerErrorTO();

            Gson g = new Gson();
            //String cadenaError = as.replace("\""," ");
            answerErrorTO = g.fromJson(as, AnswerErrorTO.class);

            AnswerSICOTO answerSicoTO = new AnswerSICOTO();
            answerSicoTO.setIdSolicitud("");
            answerSicoTO.setDescripcion(answerErrorTO.getError());
            answerSicoTO.setEstatus("conflig");
            answerSicoTO.setFolioConfirmacion("409");

            LOG.info("error ", e.getResponseBodyAsString());
            return answerSicoTO;
        } catch (Exception e) {
            LOG.error("Error ", e);
            return null;
        }
    }

    protected SicoAuthResponseTO makeAuthCall() {

        ResponseEntity<SicoAuthResponseTO> response = sicoRestTemplate
                .getForEntity(completedApiAuthEndpoint, SicoAuthResponseTO.class);

        return response.getBody();
    }

    private void getAuthToken () {
        if (this.completedApiAuthEndpoint.length() <= 0) {
            getAuthEndpoint();
        }

        try {
            SicoAuthResponseTO sicoAuthPayload = makeAuthCall();

            this.decryptKey = getDecryptkey(sicoAuthPayload.getAccessToken().getToken());

            this.currentToken = sicoAuthPayload.getAccessToken().getToken();

            this.headers.set(
                    "Authorization",
                    String.format("Bearer %s", this.currentToken)
            );

        } catch (Exception e) {
            LOG.info(
                    String.format(
                            "Ocurrio un error en SICOADAPTER.getAuthToken: %s: %s",
                            e.getMessage(),
                            e.toString()
                    )
            );
            throw new BusinessException(e.getMessage(), e);
        }
    }

    private String getDecryptkey (String token) {
        return token.substring(token.length() - 8, token.length());
    }

    private String decryptData(String payload) throws Exception {

        String cleanedPayload = payload.substring(1, payload.length() - 1);

        SecretKey secKey = new SecretKeySpec(this.decryptKey.getBytes("UTF-8"), "DES");
        byte[] decodedPayLoad = Base64.getDecoder().decode(cleanedPayload);

        Cipher cipher = Cipher.getInstance(Constants.SICO_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secKey);
        byte[] textDecrypted = cipher.doFinal(decodedPayLoad);

        return new String(textDecrypted, "UTF-8");
    }

    private void getAuthEndpoint() {

        ParameterDO sicoUser = this.parameterDAO.findByName(Constants.SICO_PARAMETER_USER);
        ParameterDO sicoPass = this.parameterDAO.findByName(Constants.SICO_PARAMETER_PASS);

        this.apiUser = sicoUser.getValue();
        this.apiPassword = sicoPass.getValue();

        this.completedApiAuthEndpoint = String.format(
                this.apiAuthEndpoint,
                this.apiUser,
                this.apiPassword
        );

    }

    private String decodeCharacter(String data){
        try {
            return URLDecoder.decode(data, "UTF-8");
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

}
