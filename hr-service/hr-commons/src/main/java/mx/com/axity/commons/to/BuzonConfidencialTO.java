package mx.com.axity.commons.to;

import java.time.LocalDateTime;

public class BuzonConfidencialTO {
    private Long idBuzon;
    private String tenantId;
    private String dsCategoria;
    private String dsDescripcion;
    private Boolean fgAnonimo;
    private Long idUsuario;
    private String dsNombreReportante;
    private String dsEstatus;
    private String dsComentarioRh;
    private LocalDateTime dtCreacion;
    private LocalDateTime dtActualizacion;

    public Long getIdBuzon() { return idBuzon; } public void setIdBuzon(Long v) { idBuzon = v; }
    public String getTenantId() { return tenantId; } public void setTenantId(String v) { tenantId = v; }
    public String getDsCategoria() { return dsCategoria; } public void setDsCategoria(String v) { dsCategoria = v; }
    public String getDsDescripcion() { return dsDescripcion; } public void setDsDescripcion(String v) { dsDescripcion = v; }
    public Boolean getFgAnonimo() { return fgAnonimo; } public void setFgAnonimo(Boolean v) { fgAnonimo = v; }
    public Long getIdUsuario() { return idUsuario; } public void setIdUsuario(Long v) { idUsuario = v; }
    public String getDsNombreReportante() { return dsNombreReportante; } public void setDsNombreReportante(String v) { dsNombreReportante = v; }
    public String getDsEstatus() { return dsEstatus; } public void setDsEstatus(String v) { dsEstatus = v; }
    public String getDsComentarioRh() { return dsComentarioRh; } public void setDsComentarioRh(String v) { dsComentarioRh = v; }
    public LocalDateTime getDtCreacion() { return dtCreacion; } public void setDtCreacion(LocalDateTime v) { dtCreacion = v; }
    public LocalDateTime getDtActualizacion() { return dtActualizacion; } public void setDtActualizacion(LocalDateTime v) { dtActualizacion = v; }
}
