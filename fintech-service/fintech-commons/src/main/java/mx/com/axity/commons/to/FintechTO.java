package mx.com.axity.commons.to;

import java.util.List;

public class FintechTO {

    private boolean access;
    private String message;
    private SicoPaysheetEmployeeTO paysheetEmployee;
    private List<PaymentPropertiesTO> paymentProperties;
    private EmployeePaymentDetailTO employeePaymentDetail;

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SicoPaysheetEmployeeTO getPaysheetEmployee() {
        return paysheetEmployee;
    }

    public void setPaysheetEmployee(SicoPaysheetEmployeeTO paysheetEmployee) {
        this.paysheetEmployee = paysheetEmployee;
    }

    public List<PaymentPropertiesTO> getPaymentProperties() {
        return paymentProperties;
    }

    public void setPaymentProperties(List<PaymentPropertiesTO> paymentProperties) {
        this.paymentProperties = paymentProperties;
    }

    public EmployeePaymentDetailTO getEmployeePaymentDetail() {
        return employeePaymentDetail;
    }

    public void setEmployeePaymentDetail(EmployeePaymentDetailTO employeePaymentDetail) {
        this.employeePaymentDetail = employeePaymentDetail;
    }
}
