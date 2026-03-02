package mx.com.axity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_employee",schema = "public")
public class EmployeeDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee")
    private Long idEmployee;
    @OneToOne
    @JoinColumn(name = "id_client")
    private CustomerDO idCliente;
    @OneToOne
    @JoinColumn(name = "id_project")
    private ProjectDO idProject;
    @Column(name = "ds_civil_status")
    private String civilStatus;
    @OneToOne
    @JoinColumn(name = "id_user")
    private UserDO idUserDO;
    @Column(name = "ds_name")
    private String name;
    @Column(name = "ds_last_name")
    private String lastName;
    @Column(name = "ds_m_last_name")
    private String mLastName;
    @Column(name = "ds_gender")
    private String gender;
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

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public CustomerDO getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(CustomerDO idCliente) {
        this.idCliente = idCliente;
    }

    public ProjectDO getIdProject() {
        return idProject;
    }

    public void setIdProject(ProjectDO idProject) {
        this.idProject = idProject;
    }

    public UserDO getIdUserDO() {
        return idUserDO;
    }

    public void setIdUserDO(UserDO idUserDO) {
        this.idUserDO = idUserDO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
