package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_resource_ack", schema = "public")
public class ResourceAckDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ack")
    private Long idAck;

    @Column(name = "tenant_id")
    private String tenantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_document")
    private ResourceDocumentDO document;

    @Column(name = "id_employee")
    private Long idEmployee;

    @Column(name = "ds_employee_name")
    private String employeeName;

    @Column(name = "dt_acknowledged_at")
    private LocalDateTime acknowledgedAt;

    public Long getIdAck() { return idAck; }
    public void setIdAck(Long idAck) { this.idAck = idAck; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public ResourceDocumentDO getDocument() { return document; }
    public void setDocument(ResourceDocumentDO document) { this.document = document; }

    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public LocalDateTime getAcknowledgedAt() { return acknowledgedAt; }
    public void setAcknowledgedAt(LocalDateTime acknowledgedAt) { this.acknowledgedAt = acknowledgedAt; }
}
