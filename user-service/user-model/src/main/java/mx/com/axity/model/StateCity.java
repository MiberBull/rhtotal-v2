package mx.com.axity.model;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_estados_municipios", schema = "public")
public class StateCity {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "c_estados_id")
    private Long stateId;
    @Column(name = "c_municipios_id")
    private Long cityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
