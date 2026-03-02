package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EmployeeTO implements Serializable {

    private Long id;
    private UserTO user;
    private String cvilStatus;
    private String name;
    private String lastName;
    private String lastMName;
    private String gender;
    private String lastUserModifier;
    private String creationUser;
    private LocalDateTime lastModification;
    private LocalDateTime creationDate;
    private boolean active;
    private ClientTO client;
    private ProjectTO project;


    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(UserTO user) {
        this.user = user;
    }

    public void setCvilStatus(String cvilStatus) {
        this.cvilStatus = cvilStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLastMName(String lastMName) {
        this.lastMName = lastMName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLastUserModifier(String lastUserModifier) {
        this.lastUserModifier = lastUserModifier;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    public void setLastModification(LocalDateTime lastModification) {
        this.lastModification = lastModification;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public UserTO getUser() {
        return user;
    }

    public String getCvilStatus() {
        return cvilStatus;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLastMName() {
        return lastMName;
    }

    public String getGender() {
        return gender;
    }

    public String getLastUserModifier() {
        return lastUserModifier;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public LocalDateTime getLastModification() {
        return lastModification;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public boolean isActive() {
        return active;
    }

    public ClientTO getClient() {
        return client;
    }

    public void setClient(ClientTO client) {
        this.client = client;
    }

    public ProjectTO getProject() {
        return project;
    }

    public void setProject(ProjectTO proyect) {
        this.project = proyect;
    }
}
