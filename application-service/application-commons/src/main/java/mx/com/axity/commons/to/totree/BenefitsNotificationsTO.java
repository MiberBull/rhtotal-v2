package mx.com.axity.commons.to.totree;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class BenefitsNotificationsTO implements Serializable {
    private Long idNotificacion;
    private String typeNotification;
    private Boolean checkClient;
    private Boolean checkExtenrno;
    private Boolean checkProject;
    private Boolean checkEmployee;
    private String lastUserModifier;
    private LocalDateTime lastModification;
    private String creationUser;
    private LocalDateTime creationDate;
    private Boolean active;
    private List<TreeClientTO> clients;



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

    public String getLastUserModifier() {
        return lastUserModifier;
    }

    public void setLastUserModifier(String lastUserModifier) {
        this.lastUserModifier = lastUserModifier;
    }

    public String getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(String typeNotification) {
        this.typeNotification = typeNotification;
    }

    public Long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Boolean getCheckClient() {
        return checkClient;
    }

    public void setCheckClient(Boolean checkClient) {
        this.checkClient = checkClient;
    }

    public Boolean getCheckExtenrno() {
        return checkExtenrno;
    }

    public void setCheckExtenrno(Boolean checkExtenrno) {
        this.checkExtenrno = checkExtenrno;
    }

    public Boolean getCheckProject() {
        return checkProject;
    }

    public void setCheckProject(Boolean checkProject) {
        this.checkProject = checkProject;
    }

    public Boolean getCheckEmployee() {
        return checkEmployee;
    }

    public void setCheckEmployee(Boolean checkEmployee) {
        this.checkEmployee = checkEmployee;
    }

    public List<TreeClientTO> getClients() {
        return clients;
    }

    public void setClients(List<TreeClientTO> clients) {
        this.clients = clients;
    }
}
