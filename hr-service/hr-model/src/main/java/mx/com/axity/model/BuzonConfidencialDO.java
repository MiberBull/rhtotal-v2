package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_buzon_confidencial", schema = "public")
public class BuzonConfidencialDO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_buzon") private Long idBuzon;

    @Column(name = "tenant_id", nullable = false) private String tenantId;
    @Column(name = "ds_categoria", nullable = false) private String dsCategoria;
    @Column(name = "ds_descripcion", nullable = false, columnDefinition = "TEXT") private String dsDescripcion;
    @Column(name = "fg_anonimo", nullable = false) private Boolean fgAnonimo = true;
    @Column(name = "id_usuario") private Long idUsuario;
    @Column(name = "ds_nombre_reportante") private String dsNombreReportante;
    @Column(name = "ds_estatus", nullable = false) private String dsEstatus = "NUEVO";
    @Column(name = "ds_comentario_rh", columnDefinition = "TEXT") private String dsComentarioRh;
    @Column(name = "dt_creacion", nullable = false) private LocalDateTime dtCreacion;
    @Column(name = "dt_actualizacion") private LocalDateTime dtActualizacion;

    @PrePersist protected void onCreate() {
        dtCreacion = LocalDateTime.now();
        if (fgAnonimo == null) fgAnonimo = true;
        if (dsEstatus == null) dsEstatus = "NUEVO";
    }
    @PreUpdate protected void onUpdate() { dtActualizacion = LocalDateTime.now(); }

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
