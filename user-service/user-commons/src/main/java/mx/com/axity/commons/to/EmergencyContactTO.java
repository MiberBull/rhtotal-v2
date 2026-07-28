package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EmergencyContactTO implements Serializable {

    private Long idEmergencyContact;
    private String tenantId;
    private Long idEmployee;
    private String dsName;
    private String dsRelationship;
    private String dsPhone;
    private Boolean fgActive;
    private LocalDateTime dtCreationDate;

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
