package mx.com.axity.model;

import javax.persistence.*;

@Entity
@Table(name = "c_customer",schema = "public")
public class ClientDO {
    @Id
    @Column(name = "id_client")
    private Long idClient;

    @Column(name = "ds_name")
    private String name;

    @Column(name="ds_status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
