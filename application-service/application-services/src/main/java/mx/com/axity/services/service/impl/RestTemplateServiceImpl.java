package mx.com.axity.services.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import mx.com.axity.services.service.IRestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class RestTemplateServiceImpl implements IRestTemplateService {

    @Autowired
    public RestTemplate restTemplate;

    @Override
    public <T> ResponseEntity<T> post(String uri, ObjectNode body, HttpHeaders headers, Class<T> responseType) throws URISyntaxException {

        var url = new URI(uri);
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity( url, request , responseType );
    }
}
