package mx.com.axity.commons.to;

import mx.com.axity.commons.to.totree.BenefitsNotificationsTO;

public class BenefitsInsuranceNotificationsTreeTO {
    InsuranceTableTO insuranceTO;
    BenefitsNotificationsTO benefitsNotificationsTO;

    public InsuranceTableTO getInsuranceTO() {
        return insuranceTO;
    }

    public void setInsuranceTO(InsuranceTableTO insuranceTO) {
        this.insuranceTO = insuranceTO;
    }

    public BenefitsNotificationsTO getBenefitsNotificationsTO() {
        return benefitsNotificationsTO;
    }

    public void setBenefitsNotificationsTO(BenefitsNotificationsTO benefitsNotificationsTO) {
        this.benefitsNotificationsTO = benefitsNotificationsTO;
    }
}
