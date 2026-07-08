package mx.com.axity.commons.to;

import java.time.LocalDateTime;

public class RepseClientTO {
    private Long idRepseClient;
    private String tenantId;
    private String razonSocial;
    private String rfc;
    private String contacto;
    private String emailContacto;
    private String telefono;
    private Integer empleadosAsignados;
    private String status;
    private Boolean active;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
    private String creationUser;
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
