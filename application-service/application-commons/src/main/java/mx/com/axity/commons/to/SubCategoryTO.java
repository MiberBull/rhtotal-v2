package mx.com.axity.commons.to;

import java.time.LocalDate;

public class SubCategoryTO {
    private  Long idSubCategory;
    private CategoryTO categoryTO;
    private String subcategory;
    private String lastUserModifier;
    private LocalDate lastModification;
    private String creationUser;
    private LocalDate creationDate;
    private Boolean active;

    public Long getIdSubCategory() {
        return idSubCategory;
    }

    public void setIdSubCategory(Long idSubCategory) {
        this.idSubCategory = idSubCategory;
    }

    public CategoryTO getCategory() {
        return categoryTO;
    }

    public void setCategory(CategoryTO categoryTO) {
        this.categoryTO = categoryTO;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
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
