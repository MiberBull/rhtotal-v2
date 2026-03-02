package mx.com.axity.services.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

public interface IHumanResourcesService {


    Map<String,String> getInfoEmployeeByTypeOne(JsonNode json);

    Map<String,String> getPaymentDetailsByMount(JsonNode json);

    List<Map<String,String>> getCfdiByMount(JsonNode json );

}
