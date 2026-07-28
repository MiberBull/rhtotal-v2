package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_emergency_contact", schema = "public")
public class EmergencyContactDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_emergency_contact")
    private Long idEmergencyContact;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "id_employee", nullable = false)
    private Long idEmployee;

    @Column(name = "ds_name", nullable = false)
    private String dsName;

    @Column(name = "ds_relationship")
    private String dsRelationship;

    @Column(name = "ds_phone")
    private String dsPhone;

    @Column(name = "fg_active", nullable = false)
    private Boolean fgActive = true;

    @Column(name = "dt_creation_date", nullable = false)
    private LocalDateTime dtCreationDate;

    @PrePersist
    protected void onCreate() {
        if (dtCreationDate == null) {
            dtCreationDate = LocalDateTime.now();
        }
        if (fgActive == null) {
            fgActive = true;
        }
    }

    public Long getIdEmergencyContact() {
        return idEmergencyContact;
    }

    public void setIdEmergencyContact(Long idEmergencyContact) {
        this.idEmergencyContact = idEmergencyContact;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getDsRelationship() {
        return dsRelationship;
    }

    public void setDsRelationship(String dsRelationship) {
        this.dsRelationship = dsRelationship;
    }

    public String getDsPhone() {
        return dsPhone;
    }

    public void setDsPhone(String dsPhone) {
        this.dsPhone = dsPhone;
    }

    public Boolean getFgActive() {
        return fgActive;
    }

    public void setFgActive(Boolean fgActive) {
        this.fgActive = fgActive;
    }

    public LocalDateTime getDtCreationDate() {
        return dtCreationDate;
    }

    public void setDtCreationDate(LocalDateTime dtCreationDate) {
        this.dtCreationDate = dtCreationDate;
    }
}
