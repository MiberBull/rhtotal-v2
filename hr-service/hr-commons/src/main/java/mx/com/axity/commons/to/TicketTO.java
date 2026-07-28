package mx.com.axity.commons.to;

import java.time.LocalDateTime;
import java.util.List;

public class TicketTO {
    private Long idTicket;
    private String tenantId;
    private Long idEmployee;
    private String dsNumber;
    private String dsSubject;
    private String dsDescription;
    private String dsCategory;
    private String dsSubcategory;
    private String dsPriority;
    private String dsStatus;
    private String dsAssignedTo;
    private LocalDateTime dtResolvedDate;
    private Boolean fgActive;
    private List<TicketCommentTO> comments;

    public Long getIdTicket() { return idTicket; }
    public void setIdTicket(Long idTicket) { this.idTicket = idTicket; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }
    public String getDsNumber() { return dsNumber; }
    public void setDsNumber(String dsNumber) { this.dsNumber = dsNumber; }
    public String getDsSubject() { return dsSubject; }
    public void setDsSubject(String dsSubject) { this.dsSubject = dsSubject; }
    public String getDsDescription() { return dsDescription; }
    public void setDsDescription(String dsDescription) { this.dsDescription = dsDescription; }
    public String getDsCategory() { return dsCategory; }
    public void setDsCategory(String dsCategory) { this.dsCategory = dsCategory; }
    public String getDsSubcategory() { return dsSubcategory; }
    public void setDsSubcategory(String dsSubcategory) { this.dsSubcategory = dsSubcategory; }
    public String getDsPriority() { return dsPriority; }
    public void setDsPriority(String dsPriority) { this.dsPriority = dsPriority; }
    public String getDsStatus() { return dsStatus; }
    public void setDsStatus(String dsStatus) { this.dsStatus = dsStatus; }
    public String getDsAssignedTo() { return dsAssignedTo; }
    public void setDsAssignedTo(String dsAssignedTo) { this.dsAssignedTo = dsAssignedTo; }
    public LocalDateTime getDtResolvedDate() { return dtResolvedDate; }
    public void setDtResolvedDate(LocalDateTime dtResolvedDate) { this.dtResolvedDate = dtResolvedDate; }
    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
    public List<TicketCommentTO> getComments() { return comments; }
    public void setComments(List<TicketCommentTO> comments) { this.comments = comments; }
}
