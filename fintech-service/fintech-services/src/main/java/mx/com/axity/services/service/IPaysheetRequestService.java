package mx.com.axity.services.service;

import com.fasterxml.jackson.databind.JsonNode;
import mx.com.axity.commons.to.*;

import java.util.List;

public interface IPaysheetRequestService {

   FintechTO requestSicoPaysheetEmploye(String url, String user,Integer section) throws Exception;

   JsonNode requestApiSico(String url,String user);

   EmployeePaymentDetailTO mapResponseForPaymentDetail( JsonNode json );

   List<PaymentPropertiesTO> operationsForVeloCahs(SicoPaysheetTO paysheet);

   List<PaymentPropertiesTO> operationsForAdvance(SicoPaysheetTO paysheet );


}
