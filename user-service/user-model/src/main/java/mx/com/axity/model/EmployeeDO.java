package mx.com.axity.model;

import org.springframework.lang.Nullable;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_employee", schema = "public")
public class EmployeeDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee")
    private Long id;
    @OneToOne
    @JoinColumn(name = "id_user")
    private UserDO user;


    @OneToOne(fetch = FetchType.LAZY)
    @Nullable
    @JoinColumn(name = "id_client")
    private ClientDO client;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_project")
    private ProjectDO project;

    @Column(name = "ds_civil_status")
    private String cvilStatus;

    @Column(name = "ds_name")
    private String name;
    @Column(name = "ds_last_name")
    private String lastName;
    @Column(name = "ds_m_last_name")
    private String lastMName;

    @Column(name = "ds_gender")
    private String gender;
    @Column(name = "ds_last_user_modifier")
    private  String lastUserModifier;
    @Column(name = "ds_creation_user")
    private String creationUser;
    @Column(name = "dt_last_modification")
    private LocalDateTime lastModification;
    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;

    @Column(name = "fg_active")
    private boolean active;

    @Column(name = "tenant_id")
    private String tenantId;

    @OneToOne(mappedBy = "employee")
    private EmployeeComplementaryDO employeeComplementary;

    @OneToOne(mappedBy = "employee")
    private EmployeeAddressDO employeeAddress;

    public EmployeeDO() {}

    public EmployeeDO(Long id, UserDO user, String cvilStatus, String name, String lastName, String lastMName, String gender, String lastUserModifier, String creationUser, LocalDateTime lastModification, LocalDateTime creationDate, boolean active, EmployeeComplementaryDO employeeComplementary, EmployeeAddressDO employeeAddress) {
        this.id = id;
        this.user = user;
        this.cvilStatus = cvilStatus;
        this.name = name;
        this.lastName = lastName;
        this.lastMName = lastMName;
        this.gender = gender;
        this.lastUserModifier = lastUserModifier;
        this.creationUser = creationUser;
        this.lastModification = lastModification;
        this.creationDate = creationDate;
        this.active = active;
        this.employeeComplementary = employeeComplementary;
        this.employeeAddress = employeeAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
        this.user = user;
    }

    public String getCvilStatus() {
        return cvilStatus;
    }

    public void setCvilStatus(String cvilStatus) {
        this.cvilStatus = cvilStatus;
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

    public String getLastMName() {
        return lastMName;
    }

    public void setLastMName(String lastMName) {
        this.lastMName = lastMName;
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

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    public LocalDateTime getLastModification() {
        return lastModification;
    }

    public void setLastModification(LocalDateTime lastModification) {
        this.lastModification = lastModification;
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

    public EmployeeComplementaryDO getEmployeeComplementary() {
        return employeeComplementary;
    }

    public void setEmployeeComplementary(EmployeeComplementaryDO employeeComplementary) {
        this.employeeComplementary = employeeComplementary;
    }

    public EmployeeAddressDO getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(EmployeeAddressDO employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Nullable
    public ProjectDO getProject() {
        return project;
    }

    public void setProject(@Nullable ProjectDO project) {
        this.project = project;
    }

    @Nullable
    public ClientDO getClient() {
        return client;
    }

    public void setClient(@Nullable ClientDO client) {
        this.client = client;
    }
}
