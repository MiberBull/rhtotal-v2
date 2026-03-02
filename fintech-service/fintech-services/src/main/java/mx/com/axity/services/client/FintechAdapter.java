package mx.com.axity.services.client;

import mx.com.axity.commons.to.ResponseSwapTO;
import mx.com.axity.commons.to.SwapNewUserTO;
import mx.com.axity.commons.to.SwapRequieredTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface FintechAdapter {

    ResponseSwapTO sendSolicitudSWAP(String uri, String applicationID, String apiKey,SwapRequieredTO swap);

    ResponseSwapTO createIdSWAP(HttpHeaders headers ,  Map<String,String> map,SwapNewUserTO swap);
}
