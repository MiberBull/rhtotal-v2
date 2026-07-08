package mx.com.axity.commons.to;

import java.time.LocalDate;

public class EmployeeShiftTO {

    private Long idEmployeeShift;
    private String tenantId;
    private Long idEmployee;
    private Long idShift;
    private LocalDate dtEffectiveDate;
    private LocalDate dtEndDate;
    private Boolean fgActive;

    public Long getIdEmployeeShift() { return idEmployeeShift; }
    public void setIdEmployeeShift(Long idEmployeeShift) { this.idEmployeeShift = idEmployeeShift; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }

    public Long getIdShift() { return idShift; }
    public void setIdShift(Long idShift) { this.idShift = idShift; }

    public LocalDate getDtEffectiveDate() { return dtEffectiveDate; }
    public void setDtEffectiveDate(LocalDate dtEffectiveDate) { this.dtEffectiveDate = dtEffectiveDate; }

    public LocalDate getDtEndDate() { return dtEndDate; }
    public void setDtEndDate(LocalDate dtEndDate) { this.dtEndDate = dtEndDate; }

    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
}
