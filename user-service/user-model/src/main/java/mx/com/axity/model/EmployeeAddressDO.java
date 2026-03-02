package mx.com.axity.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "k_employee_address", schema = "public")
public class EmployeeAddressDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_employee")
    private EmployeeDO employee;

    @Column(name = "ds_street")
    private String street;

    @Column(name = "ds_interior_number")
    private String interiorNumber;

    @Column(name = "ds_outdoor_number")
    private String outDoorNumber;

    @Column(name = "ds_colony")
    private String colony;

    @Column(name = "ds_postal_code")
    private String postalCode;

    @Column(name = "ds_city")
    private String city;

    @Column(name = "ds_state")
    private String state;

    @Column(name = "ds_last_user_modifier")
    private String lastUserModifier;

    @Column(name = "dt_last_modification")
    private LocalDateTime lastModification;

    @Column(name = "ds_creation_user")
    private String creationUser;

    @Column(name = "dt_creation_date")
    private LocalDateTime creationDate;

    @Column(name = "fg_active")
    private boolean active;


    public EmployeeAddressDO() {}

    public EmployeeAddressDO(Long id, EmployeeDO employee, String street, String interiorNumber, String outDoorNumber, String colony, String postalCode, String city, String state, String lastUserModifier, LocalDateTime lastModification, String creationUser, LocalDateTime creationDate, boolean active) {
        this.id = id;
        this.employee = employee;
        this.street = street;
        this.interiorNumber = interiorNumber;
        this.outDoorNumber = outDoorNumber;
        this.colony = colony;
        this.postalCode = postalCode;
        this.city = city;
        this.state = state;
        this.lastUserModifier = lastUserModifier;
        this.lastModification = lastModification;
        this.creationUser = creationUser;
        this.creationDate = creationDate;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeDO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDO employee) {
        this.employee = employee;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getInteriorNumber() {
        return interiorNumber;
    }

    public void setInteriorNumber(String interiorNumber) {
        this.interiorNumber = interiorNumber;
    }

    public String getOutDoorNumber() {
        return outDoorNumber;
    }

    public void setOutDoorNumber(String outDoorNumber) {
        this.outDoorNumber = outDoorNumber;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastUserModifier() {
        return lastUserModifier;
    }

    public void setLastUserModifier(String lastUserModifier) {
        this.lastUserModifier = lastUserModifier;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
