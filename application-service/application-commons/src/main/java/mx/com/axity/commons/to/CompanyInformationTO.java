package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDate;

public class CompanyInformationTO implements Serializable {

    private Integer idCompanyInformation;
    private String nameCompanyInformation;
    private String dsValue;
    private String parameterDescription;
    private String lastUserModifier;
    private LocalDate lastModification;
    private String creationUser;
    private LocalDate creationDate;
    private Boolean active;

    public Integer getIdCompanyInformation() {
        return idCompanyInformation;
    }

    public void setIdCompanyInformation(Integer idCompanyInformation) {
        this.idCompanyInformation = idCompanyInformation;
    }

    public String getNameCompanyInformation() {
        return nameCompanyInformation;
    }

    public void setNameCompanyInformation(String nameCompanyInformation) {
        this.nameCompanyInformation = nameCompanyInformation;
    }

    public String getDsValue() {
        return dsValue;
    }

    public void setDsValue(String dsValue) {
        this.dsValue = dsValue;
    }

    public String getParameterDescription() {
        return parameterDescription;
    }

    public void setParameterDescription(String parameterDescription) {
        this.parameterDescription = parameterDescription;
    }

    public String getLastUserModifier() {
        return lastUserModifier;
    }

    public void setLastUserModifier(String lastUserModifier) {
        this.lastUserModifier = lastUserModifier;
    }

    public LocalDate getLastModification() {
        return lastModification;
    }

    public void setLastModification(LocalDate lastModification) {
        this.lastModification = lastModification;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
