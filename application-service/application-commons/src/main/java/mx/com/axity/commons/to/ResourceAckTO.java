package mx.com.axity.commons.to;

import java.time.LocalDateTime;

public class ResourceAckTO {

    private Long idAck;
    private String tenantId;
    private Long idDocument;
    private Long idEmployee;
    private String employeeName;
    private LocalDateTime acknowledgedAt;

    public Long getIdAck() { return idAck; }
    public void setIdAck(Long idAck) { this.idAck = idAck; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public Long getIdDocument() { return idDocument; }
    public void setIdDocument(Long idDocument) { this.idDocument = idDocument; }

    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public LocalDateTime getAcknowledgedAt() { return acknowledgedAt; }
    public void setAcknowledgedAt(LocalDateTime acknowledgedAt) { this.acknowledgedAt = acknowledgedAt; }
}
