package mx.com.axity.model;

import mx.com.axity.model.annotations.ExelAnnotations;

import java.time.LocalDateTime;

public class EmployeesDataExcelDO {

    // datos employee
    @ExelAnnotations(getMethod = "N/R")
    private Long id;
    @ExelAnnotations(getMethod = "N/R")
    private Long idUser;
    @ExelAnnotations(getMethod = "getName")
    private String name;
    @ExelAnnotations(getMethod = "getLastName")
    private String lastName;
    @ExelAnnotations(getMethod = "getLastMName")
    private String lastMName;
    @ExelAnnotations(getMethod = "getGender")
    private String gender;
    @ExelAnnotations(getMethod = "N/R")
    private Long idClient;
    @ExelAnnotations(getMethod = "N/R")
    private Long idProyect;
//datos complementarios

    @ExelAnnotations(getMethod = "N/R")
    private Long idEmployee;
    @ExelAnnotations(getMethod = "N/R")
    private Long idSwap;
    @ExelAnnotations(getMethod = "getRfc")
    private String rfc;
    @ExelAnnotations(getMethod = "getCurp")
    private String curp;
    @ExelAnnotations(getMethod = "getNss")
    private String nss;
    @ExelAnnotations(getMethod = "N/R")
    private String emailClient;
    @ExelAnnotations(getMethod = "getEmail")
    private String email;
    @ExelAnnotations(getMethod = "getCivilStatus")
    private String civilStatus;
    @ExelAnnotations(getMethod = "getPhone")
    private String phone;
    @ExelAnnotations(getMethod = "getWorkPermitConfirm")
    private String workPermitConfirm;
    @ExelAnnotations(getMethod = "getWorkPermit")
    private String workPermit;
    @ExelAnnotations(getMethod = "getBirthDate")
    private String birthDate;
    @ExelAnnotations(getMethod = "getBirthState")
    private String birthState;
    @ExelAnnotations(getMethod = "getBirthCountry")
    private String birthCountry;
    @ExelAnnotations(getMethod = "getPassportNumber")
    private String passportNumber;
    @ExelAnnotations(getMethod = "N/R")
    private String nationality;

    // direccion
    @ExelAnnotations(getMethod = "N/R")
    private Long idStreet;
    @ExelAnnotations(getMethod = "getStreet")
    private String street;
    @ExelAnnotations(getMethod = "getInteriorNumber")
    private String interiorNumber;
    @ExelAnnotations(getMethod = "getOutDoorNumber")
    private String outDoorNumber;
    @ExelAnnotations(getMethod = "getColony")
    private String colony;
    @ExelAnnotations(getMethod = "getPostalCode")
    private String postalCode;
    @ExelAnnotations(getMethod = "getCity")
    private String city;
    @ExelAnnotations(getMethod = "getState")
    private String state;
    //datos de contratacion
    @ExelAnnotations(getMethod = "N/R")
    private  Long idContrating;
    @ExelAnnotations(getMethod = "N/R")
    private Long idLevel;
    @ExelAnnotations(getMethod = "getQtSalary")
    private String qtSalary;
    @ExelAnnotations(getMethod = "getDsArea")
    private String dsArea;
    @ExelAnnotations(getMethod = "getJob")
    private String job;
    @ExelAnnotations(getMethod = "getEndOfContract")
    private String endOfContract;
    @ExelAnnotations(getMethod = "getSkill")
    private String skill;

    //datos  de compensacion
    @ExelAnnotations(getMethod = "getSueldo_bruto_mensual")
    private String Sueldo_bruto_mensual;
    @ExelAnnotations(getMethod = "getAutomovil")
    private String Automovil;

    @ExelAnnotations(getMethod = "getGastos_Automovil")
    private String Gastos_Automovil;
    @ExelAnnotations(getMethod = "getOpcion_Compra")
    private String Opcion_Compra;
    @ExelAnnotations(getMethod = "getBono_Mensual")
    private String Bono_Mensual;
    @ExelAnnotations(getMethod = "getCantidad_Bono_Mensual")
    private String Cantidad_Bono_Mensual;
    @ExelAnnotations(getMethod = "getBono_Bimestral")
    private String Bono_Bimestral;
    @ExelAnnotations(getMethod = "getCantidad_Bono_Bimestral")
    private String Cantidad_Bono_Bimestral;
    @ExelAnnotations(getMethod = "getBono_Trimestral")
    private String Bono_Trimestral;
    @ExelAnnotations(getMethod = "getCantidad_Bono_Trimestral")
    private String Cantidad_Bono_Trimestral;
    @ExelAnnotations(getMethod = "getBono_Anual")
    private String Bono_Anual;
    @ExelAnnotations(getMethod = "getCantidad_Bono_Anual")
    private String Cantidad_Bono_Anual;
    @ExelAnnotations(getMethod = "getMetricas_Otorgamiento_Bono")
    private String Metricas_Otorgamiento_Bono;
    @ExelAnnotations(getMethod = "getFondo_de_Ahorro")
    private String Fondo_de_Ahorro;
    @ExelAnnotations(getMethod = "getCantidad_Fondo_de_Ahorro")
    private String Cantidad_Fondo_de_Ahorro;
    @ExelAnnotations(getMethod = "getVales_de_Despensa")
    private String Vales_de_Despensa;
    @ExelAnnotations(getMethod = "getCantidad_Vales_de_Despensa")
    private String Cantidad_Vales_de_Despensa;
    @ExelAnnotations(getMethod = "getVales_Restaurante")
    private String Vales_Restaurante;
    @ExelAnnotations(getMethod = "getCantidad_Vales_Restaurante")
    private String Cantidad_Vales_Restaurante;
    @ExelAnnotations(getMethod = "getVales_Gasolina")
    private String Vales_Gasolina;
    @ExelAnnotations(getMethod = "getCantidad_Vales_Gasolina")
    private String Cantidad_Vales_Gasolina;
    @ExelAnnotations(getMethod = "getAguinaldo")
    private String Aguinaldo;
    @ExelAnnotations(getMethod = "getDias_Aguinaldo")
    private String Dias_Aguinaldo;
    @ExelAnnotations(getMethod = "getCuantos_dias_de_vacaciones")
    private String Cuantos_dias_de_vacaciones;
    @ExelAnnotations(getMethod = "getPorcentaje_prima_vacacional")
    private String Porcentaje_prima_vacacional;
    @ExelAnnotations(getMethod = "getSeguro_GM_Mayores")
    private String Seguro_GM_Mayores;
    @ExelAnnotations(getMethod = "getSeguro_GM_Menores")
    private String Seguro_GM_Menores;
    @ExelAnnotations(getMethod = "getSeguro_de_vida")
    private String Seguro_de_vida;
    @ExelAnnotations(getMethod = "getMeses_de_Cobertura_por_Muerte")
    private String Meses_de_Cobertura_por_Muerte;
    @ExelAnnotations(getMethod = "getReparto_de_utilidades")
    private String Reparto_de_utilidades;
    @ExelAnnotations(getMethod = "getUltimo_monto_recibido")
    private String Ultimo_monto_recibido;
    @ExelAnnotations(getMethod = "getPlan_de_pensiones")
    private String Plan_de_pensiones;
    @ExelAnnotations(getMethod = "getOtra_prestacion")
    private String Otra_prestacion;
    @ExelAnnotations(getMethod = "getIngreso_mensual_bruto_integrado")
    private String Ingreso_mensual_bruto_integrado;
    @ExelAnnotations(getMethod = "getIngreso_anual_bruto_estimado")
    private String Ingreso_anual_bruto_estimado;
    @ExelAnnotations(getMethod = "N/R")
    private Long idDataAssigment;
    @ExelAnnotations(getMethod = "getEmployeePosition")
    private String employeePosition;
    @ExelAnnotations(getMethod = "getCompany")
    private String company;
    @ExelAnnotations(getMethod = "getProyect")
    private String proyect;
    @ExelAnnotations(getMethod = "getManager")
    private String manager;
    @ExelAnnotations(getMethod = "getAsinationState")
    private String asinationState;
    @ExelAnnotations(getMethod = "getAsignationCity")
    private String asignationCity;
    @ExelAnnotations(getMethod = "getEmailDirectBoss")
    private String emailDirectBoss;
    @ExelAnnotations(getMethod = "getTelephoneDirectBoss")
    private String telephoneDirectBoss;



    @ExelAnnotations(getMethod = "getStartAssigment")
    private String startAssigment;
    @ExelAnnotations(getMethod = "getEndAllocation")
    private String endAllocation;
    @ExelAnnotations(getMethod = "getAllocationEmail")
    private String allocationEmail;
    @ExelAnnotations(getMethod = "getUserType")
    private String userType;
    @ExelAnnotations(getMethod = "getAllocationSalary")
    private String allocationSalary;
    @ExelAnnotations(getMethod = "getEvaluation")
    private String evaluation;
    @ExelAnnotations(getMethod = "getStatus")
    private String status;
    @ExelAnnotations(getMethod = "getLastUserModifier")
    private String lastUserModifier;


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastMName() {
        return lastMName;
    }

    public void setLastMName(String lastMName) {
        this.lastMName = lastMName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Long getIdProyect() {
        return idProyect;
    }

    public void setIdProyect(Long idProyect) {
        this.idProyect = idProyect;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Long getIdSwap() {
        return idSwap;
    }

    public void setIdSwap(Long idSwap) {
        this.idSwap = idSwap;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthState() {
        return birthState;
    }

    public void setBirthState(String birthState) {
        this.birthState = birthState;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Long getIdStreet() {
        return idStreet;
    }

    public void setIdStreet(Long idStreet) {
        this.idStreet = idStreet;
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

    public Long getIdContrating() {
        return idContrating;
    }

    public void setIdContrating(Long idContrating) {
        this.idContrating = idContrating;
    }

    public Long getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(Long idLevel) {
        this.idLevel = idLevel;
    }

    public String getQtSalary() {
        return qtSalary;
    }

    public void setQtSalary(String qtSalary) {
        this.qtSalary = qtSalary;
    }

    public String getDsArea() {
        return dsArea;
    }

    public void setDsArea(String dsArea) {
        this.dsArea = dsArea;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEndOfContract() {
        return endOfContract;
    }

    public void setEndOfContract(String endOfContract) {
        this.endOfContract = endOfContract;
    }

    public String getSueldo_bruto_mensual() {
        return Sueldo_bruto_mensual;
    }

    public void setSueldo_bruto_mensual(String sueldo_bruto_mensual) {
        Sueldo_bruto_mensual = sueldo_bruto_mensual;
    }

    public String getAutomovil() {
        return Automovil;
    }

    public void setAutomovil(String automovil) {
        Automovil = automovil;
    }

    public String getGastos_Automovil() {
        return Gastos_Automovil;
    }

    public void setGastos_Automovil(String gastos_Automovil) {
        Gastos_Automovil = gastos_Automovil;
    }

    public String getOpcion_Compra() {
        return Opcion_Compra;
    }

    public void setOpcion_Compra(String opcion_Compra) {
        Opcion_Compra = opcion_Compra;
    }

    public String getBono_Mensual() {
        return Bono_Mensual;
    }

    public void setBono_Mensual(String bono_Mensual) {
        Bono_Mensual = bono_Mensual;
    }

    public String getCantidad_Bono_Mensual() {
        return Cantidad_Bono_Mensual;
    }

    public void setCantidad_Bono_Mensual(String cantidad_Bono_Mensual) {
        Cantidad_Bono_Mensual = cantidad_Bono_Mensual;
    }

    public String getBono_Bimestral() {
        return Bono_Bimestral;
    }

    public void setBono_Bimestral(String bono_Bimestral) {
        Bono_Bimestral = bono_Bimestral;
    }

    public String getCantidad_Bono_Bimestral() {
        return Cantidad_Bono_Bimestral;
    }

    public void setCantidad_Bono_Bimestral(String cantidad_Bono_Bimestral) {
        Cantidad_Bono_Bimestral = cantidad_Bono_Bimestral;
    }

    public String getBono_Trimestral() {
        return Bono_Trimestral;
    }

    public void setBono_Trimestral(String bono_Trimestral) {
        Bono_Trimestral = bono_Trimestral;
    }

    public String getCantidad_Bono_Trimestral() {
        return Cantidad_Bono_Trimestral;
    }

    public void setCantidad_Bono_Trimestral(String cantidad_Bono_Trimestral) {
        Cantidad_Bono_Trimestral = cantidad_Bono_Trimestral;
    }

    public String getBono_Anual() {
        return Bono_Anual;
    }

    public void setBono_Anual(String bono_Anual) {
        Bono_Anual = bono_Anual;
    }

    public String getCantidad_Bono_Anual() {
        return Cantidad_Bono_Anual;
    }

    public void setCantidad_Bono_Anual(String cantidad_Bono_Anual) {
        Cantidad_Bono_Anual = cantidad_Bono_Anual;
    }

    public String getMetricas_Otorgamiento_Bono() {
        return Metricas_Otorgamiento_Bono;
    }

    public void setMetricas_Otorgamiento_Bono(String metricas_Otorgamiento_Bono) {
        Metricas_Otorgamiento_Bono = metricas_Otorgamiento_Bono;
    }

    public String getFondo_de_Ahorro() {
        return Fondo_de_Ahorro;
    }

    public void setFondo_de_Ahorro(String fondo_de_Ahorro) {
        Fondo_de_Ahorro = fondo_de_Ahorro;
    }

    public String getCantidad_Fondo_de_Ahorro() {
        return Cantidad_Fondo_de_Ahorro;
    }

    public void setCantidad_Fondo_de_Ahorro(String cantidad_Fondo_de_Ahorro) {
        Cantidad_Fondo_de_Ahorro = cantidad_Fondo_de_Ahorro;
    }

    public String getVales_de_Despensa() {
        return Vales_de_Despensa;
    }

    public void setVales_de_Despensa(String vales_de_Despensa) {
        Vales_de_Despensa = vales_de_Despensa;
    }

    public String getCantidad_Vales_de_Despensa() {
        return Cantidad_Vales_de_Despensa;
    }

    public void setCantidad_Vales_de_Despensa(String cantidad_Vales_de_Despensa) {
        Cantidad_Vales_de_Despensa = cantidad_Vales_de_Despensa;
    }

    public String getVales_Restaurante() {
        return Vales_Restaurante;
    }

    public void setVales_Restaurante(String vales_Restaurante) {
        Vales_Restaurante = vales_Restaurante;
    }

    public String getCantidad_Vales_Restaurante() {
        return Cantidad_Vales_Restaurante;
    }

    public void setCantidad_Vales_Restaurante(String cantidad_Vales_Restaurante) {
        Cantidad_Vales_Restaurante = cantidad_Vales_Restaurante;
    }

    public String getVales_Gasolina() {
        return Vales_Gasolina;
    }

    public void setVales_Gasolina(String vales_Gasolina) {
        Vales_Gasolina = vales_Gasolina;
    }

    public String getCantidad_Vales_Gasolina() {
        return Cantidad_Vales_Gasolina;
    }

    public void setCantidad_Vales_Gasolina(String cantidad_Vales_Gasolina) {
        Cantidad_Vales_Gasolina = cantidad_Vales_Gasolina;
    }

    public String getAguinaldo() {
        return Aguinaldo;
    }

    public void setAguinaldo(String aguinaldo) {
        Aguinaldo = aguinaldo;
    }

    public String getDias_Aguinaldo() {
        return Dias_Aguinaldo;
    }

    public void setDias_Aguinaldo(String dias_Aguinaldo) {
        Dias_Aguinaldo = dias_Aguinaldo;
    }

    public String getCuantos_dias_de_vacaciones() {
        return Cuantos_dias_de_vacaciones;
    }

    public void setCuantos_dias_de_vacaciones(String cuantos_dias_de_vacaciones) {
        Cuantos_dias_de_vacaciones = cuantos_dias_de_vacaciones;
    }

    public String getPorcentaje_prima_vacacional() {
        return Porcentaje_prima_vacacional;
    }

    public void setPorcentaje_prima_vacacional(String porcentaje_prima_vacacional) {
        Porcentaje_prima_vacacional = porcentaje_prima_vacacional;
    }

    public String getSeguro_GM_Mayores() {
        return Seguro_GM_Mayores;
    }

    public void setSeguro_GM_Mayores(String seguro_GM_Mayores) {
        Seguro_GM_Mayores = seguro_GM_Mayores;
    }

    public String getSeguro_GM_Menores() {
        return Seguro_GM_Menores;
    }

    public void setSeguro_GM_Menores(String seguro_GM_Menores) {
        Seguro_GM_Menores = seguro_GM_Menores;
    }

    public String getSeguro_de_vida() {
        return Seguro_de_vida;
    }

    public void setSeguro_de_vida(String seguro_de_vida) {
        Seguro_de_vida = seguro_de_vida;
    }

    public String getMeses_de_Cobertura_por_Muerte() {
        return Meses_de_Cobertura_por_Muerte;
    }

    public void setMeses_de_Cobertura_por_Muerte(String meses_de_Cobertura_por_Muerte) {
        Meses_de_Cobertura_por_Muerte = meses_de_Cobertura_por_Muerte;
    }

    public String getReparto_de_utilidades() {
        return Reparto_de_utilidades;
    }

    public void setReparto_de_utilidades(String reparto_de_utilidades) {
        Reparto_de_utilidades = reparto_de_utilidades;
    }

    public String getUltimo_monto_recibido() {
        return Ultimo_monto_recibido;
    }

    public void setUltimo_monto_recibido(String ultimo_monto_recibido) {
        Ultimo_monto_recibido = ultimo_monto_recibido;
    }

    public String getPlan_de_pensiones() {
        return Plan_de_pensiones;
    }

    public void setPlan_de_pensiones(String plan_de_pensiones) {
        Plan_de_pensiones = plan_de_pensiones;
    }

    public String getOtra_prestacion() {
        return Otra_prestacion;
    }

    public void setOtra_prestacion(String otra_prestacion) {
        Otra_prestacion = otra_prestacion;
    }

    public String getIngreso_mensual_bruto_integrado() {
        return Ingreso_mensual_bruto_integrado;
    }

    public void setIngreso_mensual_bruto_integrado(String ingreso_mensual_bruto_integrado) {
        Ingreso_mensual_bruto_integrado = ingreso_mensual_bruto_integrado;
    }

    public String getIngreso_anual_bruto_estimado() {
        return Ingreso_anual_bruto_estimado;
    }

    public void setIngreso_anual_bruto_estimado(String ingreso_anual_bruto_estimado) {
        Ingreso_anual_bruto_estimado = ingreso_anual_bruto_estimado;
    }

    public Long getIdDataAssigment() {
        return idDataAssigment;
    }

    public void setIdDataAssigment(Long idDataAssigment) {
        this.idDataAssigment = idDataAssigment;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProyect() {
        return proyect;
    }

    public void setProyect(String proyect) {
        this.proyect = proyect;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getAsinationState() {
        return asinationState;
    }

    public void setAsinationState(String asinationState) {
        this.asinationState = asinationState;
    }

    public String getAsignationCity() {
        return asignationCity;
    }

    public void setAsignationCity(String asignationCity) {
        this.asignationCity = asignationCity;
    }

    public String getEmailDirectBoss() {
        return emailDirectBoss;
    }

    public void setEmailDirectBoss(String emailDirectBoss) {
        this.emailDirectBoss = emailDirectBoss;
    }

    public String getTelephoneDirectBoss() {
        return telephoneDirectBoss;
    }

    public void setTelephoneDirectBoss(String telephoneDirectBoss) {
        this.telephoneDirectBoss = telephoneDirectBoss;
    }

    public String getStartAssigment() {
        return startAssigment;
    }

    public void setStartAssigment(String startAssigment) {
        this.startAssigment = startAssigment;
    }

    public String getEndAllocation() {
        return endAllocation;
    }

    public void setEndAllocation(String endAllocation) {
        this.endAllocation = endAllocation;
    }

    public String getAllocationEmail() {
        return allocationEmail;
    }

    public void setAllocationEmail(String allocationEmail) {
        this.allocationEmail = allocationEmail;
    }

    public String getAllocationSalary() {
        return allocationSalary;
    }

    public void setAllocationSalary(String allocationSalary) {
        this.allocationSalary = allocationSalary;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getLastUserModifier() {
        return lastUserModifier;
    }

    public void setLastUserModifier(String lastUserModifier) {
        this.lastUserModifier = lastUserModifier;
    }

    public String getWorkPermitConfirm() {
        return workPermitConfirm;
    }

    public void setWorkPermitConfirm(String workPermitConfirm) {
        this.workPermitConfirm = workPermitConfirm;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
