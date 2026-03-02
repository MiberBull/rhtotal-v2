package mx.com.axity.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "k_project",schema = "public")
public class ProjectDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_project")
    private Long idProject;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_client")
    private CustomerDO idClient;
    @Column(name = "ds_name")
    private String name;
    @Column(name = "ds_rfc")
    private String rfc;
    @Column(name = "ds_business_name")
    private String businessName;
    @Column(name = "ds_address")
    private String address;
    @Column(name = "ds_contact")
    private String contact;
    @Column(name = "ds_phone")
    private String phone;
    @Column(name = "ds_extension")
    private String extension;
    @Column(name = "ds_email")
    private String email;
    @Column(name = "ds_additional_information")
    private String additionalInformation;
    @Column(name = "ds_status")
    private String status;
    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;
    @Column(name = "dt_last_modification")
    private LocalDate lastModification;
    @Column(name = "ds_creation_user")
    private String creationUser;
    @Column(name = "dt_creation_date")
    private LocalDate creationDate;
    @Column(name = "fg_active")
    private Boolean active;

    public Long getIdProject() {
        return idProject;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }

    public CustomerDO getIdClient() {
        return idClient;
    }

    public void setIdClient(CustomerDO idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
