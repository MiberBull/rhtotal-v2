package mx.com.axity.model;

import javax.persistence.*;

@Entity
@Table(name = "k_project",schema = "public")
public class ProjectDO {

    @Id
    @Column(name = "id_project")
    private Long idProject;

    @Column(name = "ds_name")
    private String name;

    @Column(name = "id_client")
    private Long idClient;

    @Column(name="ds_status")
    private String status;


    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIdProject() {
        return idProject;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
