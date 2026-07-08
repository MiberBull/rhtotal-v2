package mx.com.axity.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_cfdi")
public class CfdiDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cfdi")
    private Long idCfdi;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "id_employee", nullable = false)
    private Long idEmployee;

    @Column(name = "ds_period", nullable = false, length = 7)
    private String dsPeriod;

    @Column(name = "ds_type", nullable = false, length = 50)
    private String dsType;

    @Column(name = "ds_uuid", length = 100)
    private String dsUuid;

    @Column(name = "ds_rfc_emisor", length = 13)
    private String dsRfcEmisor;

    @Column(name = "ds_rfc_receptor", length = 13)
    private String dsRfcReceptor;

    @Column(name = "nb_total", precision = 12, scale = 2)
    private BigDecimal nbTotal;

    @Column(name = "nb_total_percepciones", precision = 12, scale = 2)
    private BigDecimal nbTotalPercepciones;

    @Column(name = "nb_total_deducciones", precision = 12, scale = 2)
    private BigDecimal nbTotalDeducciones;

    @Column(name = "ds_xml_content", columnDefinition = "TEXT")
    private String dsXmlContent;

    @Column(name = "ds_xml_s3_key", length = 500)
    private String dsXmlS3Key;

    @Column(name = "ds_pdf_s3_key", length = 500)
    private String dsPdfS3Key;

    @Column(name = "fg_active", nullable = false)
    private Boolean fgActive = true;

    @Column(name = "dt_creation_date")
    private LocalDateTime dtCreationDate;

    @Column(name = "dt_modification_date")
    private LocalDateTime dtModificationDate;

    @Column(name = "ds_creation_user", length = 200)
    private String dsCreationUser;

    @Column(name = "ds_modification_user", length = 200)
    private String dsModificationUser;

    @PrePersist
    public void prePersist() { this.dtCreationDate = LocalDateTime.now(); }

    @PreUpdate
    public void preUpdate() { this.dtModificationDate = LocalDateTime.now(); }

    public Long getIdCfdi() { return idCfdi; }
    public void setIdCfdi(Long idCfdi) { this.idCfdi = idCfdi; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getIdEmployee() { return idEmployee; }
    public void setIdEmployee(Long idEmployee) { this.idEmployee = idEmployee; }
    public String getDsPeriod() { return dsPeriod; }
    public void setDsPeriod(String dsPeriod) { this.dsPeriod = dsPeriod; }
    public String getDsType() { return dsType; }
    public void setDsType(String dsType) { this.dsType = dsType; }
    public String getDsUuid() { return dsUuid; }
    public void setDsUuid(String dsUuid) { this.dsUuid = dsUuid; }
    public String getDsRfcEmisor() { return dsRfcEmisor; }
    public void setDsRfcEmisor(String dsRfcEmisor) { this.dsRfcEmisor = dsRfcEmisor; }
    public String getDsRfcReceptor() { return dsRfcReceptor; }
    public void setDsRfcReceptor(String dsRfcReceptor) { this.dsRfcReceptor = dsRfcReceptor; }
    public BigDecimal getNbTotal() { return nbTotal; }
    public void setNbTotal(BigDecimal nbTotal) { this.nbTotal = nbTotal; }
    public BigDecimal getNbTotalPercepciones() { return nbTotalPercepciones; }
    public void setNbTotalPercepciones(BigDecimal nbTotalPercepciones) { this.nbTotalPercepciones = nbTotalPercepciones; }
    public BigDecimal getNbTotalDeducciones() { return nbTotalDeducciones; }
    public void setNbTotalDeducciones(BigDecimal nbTotalDeducciones) { this.nbTotalDeducciones = nbTotalDeducciones; }
    public String getDsXmlContent() { return dsXmlContent; }
    public void setDsXmlContent(String dsXmlContent) { this.dsXmlContent = dsXmlContent; }
    public String getDsXmlS3Key() { return dsXmlS3Key; }
    public void setDsXmlS3Key(String dsXmlS3Key) { this.dsXmlS3Key = dsXmlS3Key; }
    public String getDsPdfS3Key() { return dsPdfS3Key; }
    public void setDsPdfS3Key(String dsPdfS3Key) { this.dsPdfS3Key = dsPdfS3Key; }
    public Boolean getFgActive() { return fgActive; }
    public void setFgActive(Boolean fgActive) { this.fgActive = fgActive; }
    public LocalDateTime getDtCreationDate() { return dtCreationDate; }
    public LocalDateTime getDtModificationDate() { return dtModificationDate; }
}
