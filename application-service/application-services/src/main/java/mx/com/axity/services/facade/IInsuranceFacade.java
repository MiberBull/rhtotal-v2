package mx.com.axity.services.facade;

import mx.com.axity.commons.to.*;
import mx.com.axity.commons.to.totree.CountRowTO;

import java.util.List;
import java.util.Map;

public interface IInsuranceFacade {

    InsuranceTableTO getOneInsurance(int insurance);
    EventualyTO getOneEventualy(int eventualy);
    PlanCoverageTO getOnePlanCoverage(int coverage);
    List<EventualyTO> getAllEventualy(int page,int idInsurance);
    List<PlanCoverageTO> getAllPlanCoverage(int page,int idInsurance);
    Boolean saveUpdateEventualy(EventualyTO eventualyTO);
    Boolean saveUpdatePlanCoverage(PlanCoverageTO planCoverageTO);
    List<InsuranceTableTO> showAllInsurance(int page,String nameTab, String insuranceCarrier,String startDate,String endDate,String author);
    InsuranceTableTO saveUpdateInsuranceNotification(BenefitsInsuranceNotificationsTreeTO benefitsInsuranceTreeTO);
    CountRowTO getNumberRow(String insuranceCarrier,String startDate,String endDate,String author);
    CountRowTO getNumberRowEventualy(int idInsurance);
    CountRowTO getNumberRowCoverage(int idInsurance);
    List<Map<String,String>> getInsuranseUser(Long idUser);
    Map<String,String> geInsurangeByInsurance(Long idInsurance);
    List<Map<String,String>>getCoverageInsurance(Long idInsurance);
    Map<String,Object> verifyHourPublication(Long id);
}
