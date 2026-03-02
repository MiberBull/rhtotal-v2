package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EmployeeTO implements Serializable {

    private Long idEmployee;
    private CustomerTO idCliente;
    private ProjectTO idProject;
    private String civilStatus;
    private UserTO idUserDO;
    private String name;
    private String lastName;
    private String mLastName;
    private String gender;
    private String lastUserModifier;
    private LocalDateTime lastModification;
    private String creationUser;
    private LocalDateTime creationDate;
    private Boolean active;
    private UserTO user;
    private String cvilStatus;
    private String lastMName;
    private ClientTO client;


    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public CustomerTO getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(CustomerTO idCliente) {
        this.idCliente = idCliente;
    }

    public ProjectTO getIdProject() {
        return idProject;
    }

    public void setIdProject(ProjectTO idProject) {
        this.idProject = idProject;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public UserTO getIdUserDO() {
        return idUserDO;
    }

    public void setIdUserDO(UserTO idUserDO) {
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

    public UserTO getUser() {
        return user;
    }

    public void setUser(UserTO user) {
        this.user = user;
    }

    public String getCvilStatus() {
        return cvilStatus;
    }

    public void setCvilStatus(String cvilStatus) {
        this.cvilStatus = cvilStatus;
    }

    public String getLastMName() {
        return lastMName;
    }

    public void setLastMName(String lastMName) {
        this.lastMName = lastMName;
    }

    public ClientTO getClient() {
        return client;
    }

    public void setClient(ClientTO client) {
        this.client = client;
    }
}
