package mx.com.axity.model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_logs_swap",schema = "public")
public class LogSwapDO {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id_log_swap")
    private Long idLogSwap;

    @Column(name = "ds_folio")
    private String folioSol;

    @Column(name = "ds_data_send")
    private String dataSend;

    @Column(name = "ds_data_response")
    private String dataResponse;

    @Column(name = "ds_creation_user")
    private String creationUser;

    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;

    @Column(name = "fg_active")
    private Boolean active;


    public Long getIdLogSwap() {
        return idLogSwap;
    }

    public void setIdLogSwap(Long idLogSwap) {
        this.idLogSwap = idLogSwap;
    }

    public String getFolioSol() {
        return folioSol;
    }

    public void setFolioSol(String folioSol) {
        this.folioSol = folioSol;
    }

    public String getDataSend() {
        return dataSend;
    }

    public void setDataSend(String dataSend) {
        this.dataSend = dataSend;
    }

    public String getDataResponse() {
        return dataResponse;
    }

    public void setDataResponse(String dataResponse) {
        this.dataResponse = dataResponse;
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
