package mx.com.axity.commons.to;

import java.io.Serializable;
import java.time.LocalDate;

public class EmployeeComplementaryTO implements Serializable {
    private Long idEmploeyeeComplementary;
    private UserTO idUser;
    private Long idEmpleyee;
    private CatalogoRolTO idRol;
    private Long idSkill;
    private Long idCountry;
    private String RFC;
    private String Curp;
    private String NSS;
    private String emailCliente;
    private String email;
    private String phone;
    private String workPermit;
    private byte photography;
    private String passportNumber;
    private String status;
    private String lastUserModifier;
    private LocalDate lastModification;
    private LocalDate creationDate;

    public Long getIdEmploeyeeComplementary() {
        return idEmploeyeeComplementary;
    }

    public void setIdEmploeyeeComplementary(Long idEmploeyeeComplementary) {
        this.idEmploeyeeComplementary = idEmploeyeeComplementary;
    }

    public UserTO getIdUser() {
        return idUser;
    }

    public void setIdUser(UserTO idUser) {
        this.idUser = idUser;
    }

    public Long getIdEmpleyee() {
        return idEmpleyee;
    }

    public void setIdEmpleyee(Long idEmpleyee) {
        this.idEmpleyee = idEmpleyee;
    }

    public CatalogoRolTO getIdRol() {
        return idRol;
    }

    public void setIdRol(CatalogoRolTO idRol) {
        this.idRol = idRol;
    }

    public Long getIdSkill() {
        return idSkill;
    }

    public void setIdSkill(Long idSkill) {
        this.idSkill = idSkill;
    }

    public Long getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(Long idCountry) {
        this.idCountry = idCountry;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getCurp() {
        return Curp;
    }

    public void setCurp(String curp) {
        Curp = curp;
    }

    public String getNSS() {
        return NSS;
    }

    public void setNSS(String NSS) {
        this.NSS = NSS;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkPermit() {
        return workPermit;
    }

    public void setWorkPermit(String workPermit) {
        this.workPermit = workPermit;
    }

    public byte getPhotography() {
        return photography;
    }

    public void setPhotography(byte photography) {
        this.photography = photography;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastUserModifier() {
        return lastUserModifier;
    }

    public void setLastUserModifier(String lastUserModifier) {
        this.lastUserModifier = lastUserModifier;
    }

    public LocalDate getLastModification() {
        return lastModification;
    }

    public void setLastModification(LocalDate lastModification) {
        this.lastModification = lastModification;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
