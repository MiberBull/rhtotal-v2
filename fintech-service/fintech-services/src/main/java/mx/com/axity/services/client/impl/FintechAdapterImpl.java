package mx.com.axity.services.client.impl;


import com.google.gson.Gson;
import mx.com.axity.commons.to.ResponseSwapTO;
import mx.com.axity.commons.to.SwapNewUserTO;
import mx.com.axity.commons.to.SwapRequieredTO;
import mx.com.axity.services.client.FintechAdapter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;



@Component
public class FintechAdapterImpl implements FintechAdapter {


    @Qualifier("appRestFintech")
    @Autowired
    RestTemplate restTemplateFintech;

    private String requestPayload="";
    private Gson gson = new Gson();

    static final Logger LOG = LogManager.getLogger(FintechAdapterImpl.class);

    @Override
    public ResponseSwapTO sendSolicitudSWAP(String uri, String applicationID, String apiKey,SwapRequieredTO swap) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-parse-application-id", applicationID);
        headers.set("x-parse-rest-api-key", apiKey);

        try {

            var request = new HttpEntity<>(swap, headers);
            var response = restTemplateFintech.exchange(uri, HttpMethod.POST, request, String.class);

            ResponseSwapTO to =new ResponseSwapTO();
            to.setCodeResponse(response.getStatusCode().toString());
            to.setResponse(response.toString());


            return to;

        } catch (HttpClientErrorException e) {
            LOG.error(e.getResponseBodyAsString());
            ResponseSwapTO to = new ResponseSwapTO();
            to.setCodeResponse(e.getStatusCode().toString());
            to.setResponse(e.getResponseBodyAsString());
            return  to;
        }
    }


    @Override
    public ResponseSwapTO createIdSWAP(HttpHeaders headers , Map<String,String> map, SwapNewUserTO swap) {
            String url = "https://parseapi.back4app.com/functions/newUser/";
            HttpHeaders header = new HttpHeaders();
            header.set("Content-Type", "application/json");
            header.set("x-parse-application-id", "GQuKygkN8LMdZw5hEa581vmwHb2IwpasdFrOjIIA");
            header.set("x-parse-rest-api-key", "SRd6qEf2nc3aaUgc21ZLJ8LDUbbxSac8F18r6TT6");

        try {
            var request = new HttpEntity<>(swap, header);
            var response = this.restTemplateFintech.exchange(url, HttpMethod.POST, request, String.class);
            ResponseSwapTO to =new ResponseSwapTO();
            to.setCodeResponse(response.getStatusCode().toString());
            to.setResponse(response.toString());
            LOG.info("ID SWAP Generado en NewUser ", to.getResponse());
            return to;

        } catch (HttpClientErrorException e) {
            ResponseSwapTO to = new ResponseSwapTO();
            to.setCodeResponse(e.getStatusCode().toString());
            to.setResponse(e.getResponseBodyAsString());
            LOG.error("Error",e.getResponseBodyAsString());
            return  to;

        }


    }
}
