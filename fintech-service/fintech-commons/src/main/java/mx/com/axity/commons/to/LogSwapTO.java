package mx.com.axity.commons.to;

import java.time.LocalDateTime;

public class LogSwapTO {


    private Long idLogSwap;
    private String folioSol;
    private String dataSend;
    private String dataResponse;
    private String creationUser;
    private LocalDateTime creationDate;
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
