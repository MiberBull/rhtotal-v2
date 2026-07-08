package mx.com.axity.model;

import mx.com.axity.model.annotations.ExelAnnotations;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_repse_client", schema = "public")
public class RepseClientDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_repse_client")
    @ExelAnnotations(getMethod = "N/R")
    private Long idRepseClient;

    @Column(name = "tenant_id")
    @ExelAnnotations(getMethod = "getTenantId")
    private String tenantId;

    @Column(name = "ds_razon_social")
    @ExelAnnotations(getMethod = "getRazonSocial")
    private String razonSocial;

    @Column(name = "ds_rfc")
    @ExelAnnotations(getMethod = "getRfc")
    private String rfc;

    @Column(name = "ds_contacto")
    @ExelAnnotations(getMethod = "getContacto")
    private String contacto;

    @Column(name = "ds_email_contacto")
    @ExelAnnotations(getMethod = "getEmailContacto")
    private String emailContacto;

    @Column(name = "ds_telefono")
    @ExelAnnotations(getMethod = "getTelefono")
    private String telefono;

    @Column(name = "nb_empleados_asignados")
    @ExelAnnotations(getMethod = "getEmpleadosAsignados")
    private Integer empleadosAsignados;

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

    public Long getIdRepseClient() { return idRepseClient; }
    public void setIdRepseClient(Long idRepseClient) { this.idRepseClient = idRepseClient; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getEmailContacto() { return emailContacto; }
    public void setEmailContacto(String emailContacto) { this.emailContacto = emailContacto; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Integer getEmpleadosAsignados() { return empleadosAsignados; }
    public void setEmpleadosAsignados(Integer empleadosAsignados) { this.empleadosAsignados = empleadosAsignados; }
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
