package mx.com.axity.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "w_company_information", schema = "public")
public class CompanyInformationDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id_company_information")
    private Integer idCompanyInformation;
    @Column (name="ds_name_company_information")
    private String nameCompanyInformation;
    @Column (name="ds_value")
    private String dsValue;
    @Column (name="ds_parameter_description")
    private String parameterDescription;
    @Column (name="ds_last_user_modifier")
    private String lastUserModifier;
    @Column (name="dt_last_modification")
    private LocalDate lastModification;
    @Column (name="ds_creation_user")
    private String creationUser;
    @Column (name="dt_creation_date")
    private LocalDate creationDate;
    @Column (name="fg_active")
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
