package mx.com.axity.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_employee_complementary", schema = "public")
public class EmployeeComplementaryDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee_complementary")
    private Long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employee")
    private EmployeeDO employee;


    @Column(name = "id_swap")
    private Long idSwap;

    @Column(name = "ds_rfc")
    private String rfc;

    @Column(name = "ds_curp")
    private String curp;

    @Column(name = "ds_nss")
    private String nss;

    @Column(name = "ds_email_client")
    private String emailClient;

    @Column(name = "ds_email")
    private String email;

    @Column(name = "ds_phone")
    private String phone;

    @Column(name = "ds_work_permit")
    private String workPermit;

    @Column(name = "ds_birthdate")
    private LocalDateTime birthDate;

    @Column(name = "ds_birth_state")
    private String birthState;

    @Column(name = "ds_country")
    private String birthCountry;

    @Column(name = "photography")
    private String photography;

    @Column(name = "ds_passport_number")
    private String passportNumber;

    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;

    @Column(name = "dt_last_modification")
    private LocalDateTime lastModification;

    @Column(name = "ds_creation_user")
    private String  creationUser;

    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;

    @Column(name = "fg_active")
    private boolean active;

    @Column(name = "ds_nationality")
    private String nationality;

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public EmployeeComplementaryDO () {}

    public EmployeeComplementaryDO(Long id, EmployeeDO employee, Long idSwap, String rfc, String curp, String nss, String emailClient, String email, String phone, String workPermit, LocalDateTime birthDate, String birthState, String birthCountry, String photography, String passportNumber, String lastUserModifier, LocalDateTime lastModification, String creationUser, LocalDateTime creationDate, boolean active) {
        this.id = id;
        this.employee = employee;
        this.idSwap = idSwap;
        this.rfc = rfc;
        this.curp = curp;
        this.nss = nss;
        this.emailClient = emailClient;
        this.email = email;
        this.phone = phone;
        this.workPermit = workPermit;
        this.birthDate = birthDate;
        this.birthState = birthState;
        this.birthCountry = birthCountry;
        this.photography = photography;
        this.passportNumber = passportNumber;
        this.lastUserModifier = lastUserModifier;
        this.lastModification = lastModification;
        this.creationUser = creationUser;
        this.creationDate = creationDate;
        this.active = active;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeDO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDO employee) {
        this.employee = employee;
    }


    public Long getIdSwap() {
        return idSwap;
    }

    public void setIdSwap(Long idSwap) {
        this.idSwap = idSwap;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkPermit() {
        return workPermit;
    }

    public void setWorkPermit(String workPermit) {
        this.workPermit = workPermit;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthState() {
        return birthState;
    }

    public void setBirthState(String birthState) {
        this.birthState = birthState;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public String getPhotography() {
        return photography;
    }

    public void setPhotography(String photography) {
        this.photography = photography;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getLastUserModifier() {
        return lastUserModifier;
    }

    public void setLastUserModifier(String lastUserModifier) {
        this.lastUserModifier = lastUserModifier;
    }

    public LocalDateTime getLastModification() {
        return lastModification;
    }

    public void setLastModification(LocalDateTime lastModification) {
        this.lastModification = lastModification;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
