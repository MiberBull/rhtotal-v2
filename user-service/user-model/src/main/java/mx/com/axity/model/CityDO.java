package mx.com.axity.model;


import javax.persistence.*;
@Entity
@Table(name = "c_municipios", schema = "public")
public class CityDO {
    @Id
    @Column(name = "id")
    private int idCity;
    @Column(name = "municipio")
    private String city;

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
