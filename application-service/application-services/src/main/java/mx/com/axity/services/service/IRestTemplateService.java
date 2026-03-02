package mx.com.axity.services.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import java.net.URISyntaxException;

public interface IRestTemplateService {

    <T> ResponseEntity<T> post(String url, ObjectNode body, HttpHeaders headers, Class<T> responseType) throws URISyntaxException;
}
