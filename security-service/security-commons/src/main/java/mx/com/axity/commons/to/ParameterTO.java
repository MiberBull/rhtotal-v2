package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDate;

public class ParameterTO implements Serializable {

    private Long idParameter;
    private String nameParameter;
    private String value;
    private String descriptionParameter;
    private LocalDate lastUserModifier;
    private LocalDate lastModification;
    private LocalDate creationDate;

    public Long getIdParameter() {
        return idParameter;
    }

    public void setIdParameter(Long idParameter) {
        this.idParameter = idParameter;
    }

    public String getNameParameter() {
        return nameParameter;
    }

    public void setNameParameter(String nameParameter) {
        this.nameParameter = nameParameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescriptionParameter() {
        return descriptionParameter;
    }

    public void setDescriptionParameter(String descriptionParameter) {
        this.descriptionParameter = descriptionParameter;
    }

    public LocalDate getLastUserModifier() {
        return lastUserModifier;
    }

    public void setLastUserModifier(LocalDate lastUserModifier) {
        this.lastUserModifier = lastUserModifier;
    }

    public LocalDate getLastModification() {
        return lastModification;
    }

    public void setLastModification(LocalDate lastModification) {
        this.lastModification = lastModification;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
