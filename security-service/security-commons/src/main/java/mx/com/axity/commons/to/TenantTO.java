package mx.com.axity.commons.to;

public class TenantTO {

    private String idTenant;
    private String dsName;
    private String dsDomain;
    private Boolean fgActive;

    public String getIdTenant() {
        return idTenant;
    }

    public void setIdTenant(String idTenant) {
        this.idTenant = idTenant;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getDsDomain() {
        return dsDomain;
    }

    public void setDsDomain(String dsDomain) {
        this.dsDomain = dsDomain;
    }

    public Boolean getFgActive() {
        return fgActive;
    }

    public void setFgActive(Boolean fgActive) {
        this.fgActive = fgActive;
    }
}
