package mx.com.axity.commons.to;

import java.io.Serializable;

/**
 * TO agregador para la Ficha del Colaborador (Sprint 15).
 * Reune en una sola respuesta todos los datos del empleado:
 * identidad, complementario, domicilio, contratacion,
 * asignacion, contacto de emergencia y saldo de vacaciones.
 */
public class EmployeeProfileTO implements Serializable {

    /** Identidad basica del empleado (k_employee) */
    private EmployeeTO employee;

    /** Datos complementarios: CURP, RFC, NSS, fotografia (k_employee_complementary) */
    private EmployeeComplementaryTO complementary;

    /** Domicilio del empleado (k_employee_address) */
    private EmployeeAddressTO address;

    /** Datos de contratacion: puesto, contrato, jornada, fecha ingreso (k_contrating_data) */
    private ContratingDataTO contracting;

    /** Datos de asignacion: cliente, proyecto, centro de trabajo, region (k_data_assignment) */
    private AsignationDataTO assignment;

    /** Contacto de emergencia (k_emergency_contact) */
    private EmergencyContactTO emergency;

    /** Nombre de la razon social (w_tenant.ds_name) */
    private String tenantName;

    /** Saldo de dias de vacaciones disponibles — viene de hr-service, null si no disponible */
    private Integer vacationBalance;

    public EmployeeTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeTO employee) {
        this.employee = employee;
    }

    public EmployeeComplementaryTO getComplementary() {
        return complementary;
    }

    public void setComplementary(EmployeeComplementaryTO complementary) {
        this.complementary = complementary;
    }

    public EmployeeAddressTO getAddress() {
        return address;
    }

    public void setAddress(EmployeeAddressTO address) {
        this.address = address;
    }

    public ContratingDataTO getContracting() {
        return contracting;
    }

    public void setContracting(ContratingDataTO contracting) {
        this.contracting = contracting;
    }

    public AsignationDataTO getAssignment() {
        return assignment;
    }

    public void setAssignment(AsignationDataTO assignment) {
        this.assignment = assignment;
    }

    public EmergencyContactTO getEmergency() {
        return emergency;
    }

    public void setEmergency(EmergencyContactTO emergency) {
        this.emergency = emergency;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Integer getVacationBalance() {
        return vacationBalance;
    }

    public void setVacationBalance(Integer vacationBalance) {
        this.vacationBalance = vacationBalance;
    }
}
