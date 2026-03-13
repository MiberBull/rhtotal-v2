package mx.com.axity.model;


import jakarta.persistence.*;
@Entity
@Table(name = "c_estados", schema = "public")
public class StateDO {
    @Id
    @Column(name = "id")
    private int idState;
    @Column(name = "estado")
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getIdState() {
        return idState;
    }

    public void setIdState(int idState) {
        this.idState = idState;
    }
}