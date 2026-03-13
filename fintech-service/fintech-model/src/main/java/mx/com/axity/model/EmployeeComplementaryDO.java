package mx.com.axity.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_employee_complementary",schema = "public")
public class EmployeeComplementaryDO {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id_employee_complementary")
    private Long idEmployeeComplementary;

    @OneToOne
    @JoinColumn(name = "id_employee")
    private EmployeeDO idEmployee;

    @OneToOne
    @JoinColumn(name = "id_skill")
    private SkillDO idSkill;

   /* @OneToOne
    @JoinColumn(name = "id_country")
    private CountryDO idCountry;

    @OneToOne
    @JoinColumn(name = "id_user")
    private UserDO user;
*/
    @Column(name = "id_swap")
    private String idSwap;

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

    @Column(name = "photography")
    private  String photography;

    @Column(name = "ds_passport_number")
    private String passportNumber;

    @Column(name = "ds_birthdate")
    private LocalDateTime birthdate;

    /*@Column(name = "ds_status")
    private String status;
*/
    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;

    @Column(name = "dt_last_modification")
    private LocalDateTime lastModification;

    @Column(name = "ds_creation_user")
    private String creationUser;

    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;

    @Column(name = "fg_active")
    private Boolean active;

    public Long getIdEmployeeComplementary() {
        return idEmployeeComplementary;
    }

    public void setIdEmployeeComplementary(Long idEmployeeComplementary) {
        this.idEmployeeComplementary = idEmployeeComplementary;
    }

    public EmployeeDO getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(EmployeeDO idEmployee) {
        this.idEmployee = idEmployee;
    }

    public SkillDO getIdSkill() {
        return idSkill;
    }

    public void setIdSkill(SkillDO idSkill) {
        this.idSkill = idSkill;
    }

    /*public CountryDO getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(CountryDO idCountry) {
        this.idCountry = idCountry;
    }

    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
        this.user = user;
    }*/

    public String getIdSwap() {
        return idSwap;
    }

    public void setIdSwap(String idSwap) {
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

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

   /* public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }*/

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
