package mx.com.axity.model;

import mx.com.axity.model.annotations.ExelAnnotations;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_repse_profile", schema = "public")
public class RepseProfileDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_repse_profile")
    @ExelAnnotations(getMethod = "N/R")
    private Long idRepseProfile;

    @Column(name = "tenant_id")
    @ExelAnnotations(getMethod = "getTenantId")
    private String tenantId;

    @Column(name = "ds_registro_stps")
    @ExelAnnotations(getMethod = "getRegistroStps")
    private String registroStps;

    @Column(name = "dt_vigencia")
    @ExelAnnotations(getMethod = "getVigencia")
    private LocalDate vigencia;

    @Column(name = "ds_razon_social")
    @ExelAnnotations(getMethod = "getRazonSocial")
    private String razonSocial;

    @Column(name = "ds_rfc")
    @ExelAnnotations(getMethod = "getRfc")
    private String rfc;

    @Column(name = "ds_representante_legal")
    @ExelAnnotations(getMethod = "getRepresentanteLegal")
    private String representanteLegal;

    @Column(name = "ds_actividad_economica")
    @ExelAnnotations(getMethod = "getActividadEconomica")
    private String actividadEconomica;

    @Column(name = "ds_status")
    @ExelAnnotations(getMethod = "getStatus")
    private String status;

    @Column(name = "fg_active")
    @ExelAnnotations(getMethod = "N/R")
    private Boolean active;

    @Column(name = "dt_creation_date")
    @ExelAnnotations(getMethod = "N/R")
    private LocalDateTime creationDate;

    @Column(name = "dt_modification_date")
    @ExelAnnotations(getMethod = "N/R")
    private LocalDateTime modificationDate;

    @Column(name = "ds_creation_user")
    @ExelAnnotations(getMethod = "N/R")
    private String creationUser;

    @Column(name = "ds_modification_user")
    @ExelAnnotations(getMethod = "N/R")
    private String modificationUser;

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
}
