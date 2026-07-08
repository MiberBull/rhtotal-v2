package mx.com.axity.commons.to;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RepseProfileTO {
    private Long idRepseProfile;
    private String tenantId;
    private String registroStps;
    private LocalDate vigencia;
    private String razonSocial;
    private String rfc;
    private String representanteLegal;
    private String actividadEconomica;
    private String status;
    private Boolean active;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private String creationUser;
    private String modificationUser;
    /** Días restantes hasta vencimiento (calculado, no persistido) */
    private Long diasParaVencimiento;

    public Long getIdRepseProfile() { return idRepseProfile; }
    public void setIdRepseProfile(Long idRepseProfile) { this.idRepseProfile = idRepseProfile; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getRegistroStps() { return registroStps; }
    public void setRegistroStps(String registroStps) { this.registroStps = registroStps; }
    public LocalDate getVigencia() { return vigencia; }
    public void setVigencia(LocalDate vigencia) { this.vigencia = vigencia; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }
    public String getRepresentanteLegal() { return representanteLegal; }
    public void setRepresentanteLegal(String representanteLegal) { this.representanteLegal = representanteLegal; }
    public String getActividadEconomica() { return actividadEconomica; }
    public void setActividadEconomica(String actividadEconomica) { this.actividadEconomica = actividadEconomica; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
    public LocalDateTime getModificationDate() { return modificationDate; }
    public void setModificationDate(LocalDateTime modificationDate) { this.modificationDate = modificationDate; }
    public String getCreationUser() { return creationUser; }
    public void setCreationUser(String creationUser) { this.creationUser = creationUser; }
    public String getModificationUser() { return modificationUser; }
    public void setModificationUser(String modificationUser) { this.modificationUser = modificationUser; }
    public Long getDiasParaVencimiento() { return diasParaVencimiento; }
    public void setDiasParaVencimiento(Long diasParaVencimiento) { this.diasParaVencimiento = diasParaVencimiento; }
}
