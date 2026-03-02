package mx.com.axity.services.facade;

import com.fasterxml.jackson.databind.JsonNode;
import mx.com.axity.commons.to.*;

import java.util.List;

public interface IPaysheetRequestFacade {

    FintechTO requestSicoPaysheetEmploye(String url, String user,Integer section);

    JsonNode requestApiSico(String url, String user);

    EmployeePaymentDetailTO mapResponseForPaymentDetail(JsonNode json );

    List<PaymentPropertiesTO> operationsForVeloCahs(SicoPaysheetTO paysheet);

    List<PaymentPropertiesTO> operationsForAdvance(SicoPaysheetTO paysheet );

}
