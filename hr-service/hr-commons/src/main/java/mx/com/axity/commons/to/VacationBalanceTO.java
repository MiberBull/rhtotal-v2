package mx.com.axity.commons.to;

import java.time.LocalDate;

public class VacationBalanceTO {
    private Long idBalance;
    private String tenantId;
    private Long idEmployee;
    private Integer nbYearOfService;
    private Integer nbDaysEntitled;
    private Integer nbDaysTaken;
    private Integer nbDaysPending;
    private LocalDate dtPeriodStart;
    private LocalDate dtPeriodEnd;
    private Boolean fgActive;

    public Long getIdBalance() { return idBalance; }
    public void setIdBalance(Long idBalance) { this.idBalance = idBalance; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }
    public Integer getNbYearOfService() { return nbYearOfService; }
    public void setNbYearOfService(Integer nbYearOfService) { this.nbYearOfService = nbYearOfService; }
    public Integer getNbDaysEntitled() { return nbDaysEntitled; }
    public void setNbDaysEntitled(Integer nbDaysEntitled) { this.nbDaysEntitled = nbDaysEntitled; }
    public Integer getNbDaysTaken() { return nbDaysTaken; }
    public void setNbDaysTaken(Integer nbDaysTaken) { this.nbDaysTaken = nbDaysTaken; }
    public Integer getNbDaysPending() { return nbDaysPending; }
    public void setNbDaysPending(Integer nbDaysPending) { this.nbDaysPending = nbDaysPending; }
    public LocalDate getDtPeriodStart() { return dtPeriodStart; }
    public void setDtPeriodStart(LocalDate dtPeriodStart) { this.dtPeriodStart = dtPeriodStart; }
    public LocalDate getDtPeriodEnd() { return dtPeriodEnd; }
    public void setDtPeriodEnd(LocalDate dtPeriodEnd) { this.dtPeriodEnd = dtPeriodEnd; }
    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
}
