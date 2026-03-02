package mx.com.axity.services.facade.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.*;
import mx.com.axity.services.facade.IPaysheetRequestFacade;
import mx.com.axity.services.service.IPaysheetRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class PaysheetRequestFacadeImpl implements IPaysheetRequestFacade {

    @Autowired
    private IPaysheetRequestService paysheetRequestService;

    @Override
    public FintechTO requestSicoPaysheetEmploye(String url, String user,Integer section) {
        try {
            return this.paysheetRequestService.requestSicoPaysheetEmploye(url,user,section);
        }catch (Exception e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public JsonNode requestApiSico(String url, String user) {
        try {
            return this.paysheetRequestService.requestApiSico( url,user );
        }catch (Exception e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public EmployeePaymentDetailTO mapResponseForPaymentDetail(JsonNode json) {
        try {
            return this.paysheetRequestService.mapResponseForPaymentDetail(json);
        }catch (Exception e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public List<PaymentPropertiesTO > operationsForVeloCahs(SicoPaysheetTO paysheet) {
        try {
            return this.paysheetRequestService.operationsForVeloCahs( paysheet );
        } catch (Exception e) {
            throw  new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public List<PaymentPropertiesTO> operationsForAdvance(SicoPaysheetTO paysheet) {
        try {
            return this.paysheetRequestService.operationsForAdvance( paysheet );
        } catch (Exception e) {
            throw  new BusinessException(e.getMessage(),e);
        }
    }
}
