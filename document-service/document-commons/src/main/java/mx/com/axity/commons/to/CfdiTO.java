package mx.com.axity.commons.to;

import java.math.BigDecimal;

public class CfdiTO {
    private Long idCfdi;
    private String tenantId;
    private Long idEmployee;
    private String dsPeriod;
    private String dsType;
    private String dsUuid;
    private String dsRfcEmisor;
    private String dsRfcReceptor;
    private BigDecimal nbTotal;
    private BigDecimal nbTotalPercepciones;
    private BigDecimal nbTotalDeducciones;
    private String dsXmlContent;
    private String dsXmlS3Key;
    private String dsPdfS3Key;
    private Boolean fgActive;

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
}
