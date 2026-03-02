package mx.com.axity.services.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.util.AES;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.services.service.IHumanResourcesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HumanResourcesImpl implements IHumanResourcesService {

    static final Logger LOG = LogManager.getLogger( HumanResourcesImpl.class );

    @Override
    public Map<String, String>getInfoEmployeeByTypeOne(JsonNode json) {
        try{
            Map<String,String> infoMap = new HashMap<>();
            if( !json.path("data").elements().hasNext() ){
                throw new Exception(Constants.MSG_RESPONSE_EMPTY_SICO);
            }
            var data = json.path("data");
            if( data.path("personal").size() > 0 ) {
                infoMap.put("periodoPago",data.path("personal").path("periodoPago").asText());
            } else {
                infoMap.put("periodoPago","");
            }
            var payments = (ArrayNode) data.path("nomina");
            if( payments.elements().hasNext() ) {
                var item = payments.get(0);
                infoMap.put("fechaPrimerPagoConsecutivo",item.path("fechaPrimerPagoConsecutivo").asText());
                infoMap.put("nominaMesAnterior",item.path("nominaMesAnterior").asText());
                infoMap.put("nombreProyecto",item.path("nombreProyecto").asText());
            } else {
                infoMap.put("fechaPrimerPagoConsecutivo","");
                infoMap.put("nominaMesActual","");
                infoMap.put("nombreProyecto","");
            }
            return infoMap;
        } catch (Exception e) {
            LOG.info("Ocurrio un error en getInfoEmployeeByTypeOne");
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public Map<String, String> getPaymentDetailsByMount(JsonNode json) {
        try {
            Map<String, String> infoMap = new HashMap<>();
            if (!json.path("data").elements().hasNext()) {
                throw new Exception(Constants.MSG_RESPONSE_EMPTY_SICO);
            }
            var data = json.path("data");
            var payments = (ArrayNode) data.path("pago");
            if (payments.elements().hasNext()) {
                var item = payments.get(0);
                infoMap.put("salarioRealPercibidoMes", item.path("salarioRealPercibidoMes").asText());
                infoMap.put("nombreCliente", item.path("nombreCliente").asText());
                infoMap.put("nombreProyecto", item.path("nombreProyecto").asText());
            }else {
                throw new Exception(Constants.MSG_RESPONSE_EMPTY_SICO);
            }
            return infoMap;
        }catch (Exception e) {
            LOG.info("Ocurrio un error en getPaymentDetailsByMount");
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public List<Map<String, String>> getCfdiByMount(JsonNode json) {

        List<Map<String,String>> listCfdi = new ArrayList<>();

        var data = json.path("data");
        var cfdis = (ArrayNode) data.path("cfdi");

        if( cfdis.elements().hasNext() ) {
            for (int i = 0; i < cfdis.size(); i++) {
                JsonNode cfdi = cfdis.get(i);
                Map<String,String> values = new HashMap<>();
                values.put("urlPdf",cfdi.path("urlPdf").asText());
                values.put("periodo",cfdi.path("periodo").asText());
                listCfdi.add(values);
            }
        }

        return listCfdi;
    }

}
