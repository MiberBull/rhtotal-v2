package mx.com.axity.commons.to.totree;

import java.io.Serializable;

public class TreeEmployeeTO implements Serializable {
    private Boolean everyBody;
    private String name;
    private Long id;
    private Boolean check;
    private Long idUser;


    public TreeEmployeeTO() {
    }

    public TreeEmployeeTO(Boolean everyBody, String name, Long id, Boolean check, Long idUser) {
        this.everyBody = everyBody;
        this.name = name;
        this.id = id;
        this.check = check;
        this.idUser = idUser;
    }

    public Boolean getEveryBody() {
        return everyBody;
    }

    public void setEveryBody(Boolean everyBody) {
        this.everyBody = everyBody;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
