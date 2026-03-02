package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.CivilStatusTO;
import mx.com.axity.commons.to.CompensationPackageTO;
import mx.com.axity.commons.to.EmployeeComplementaryTO;
import mx.com.axity.model.EmployeesDataTO;
import mx.com.axity.persistence.EmployeeComplementaryDAO;
import mx.com.axity.services.service.IEmployeeService;
import mx.com.axity.services.service.IEmployeesExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static mx.com.axity.commons.util.Constants.FORMAT_DATE;
import static mx.com.axity.commons.util.Constants.FORMAT_TABLE;

@Service
public class EmployeesExcelService implements IEmployeesExcelService {

    @Autowired
    IEmployeeService employeeService;

    @Override
    public List<EmployeesDataTO> getEmployeesByCurpClientProject()  {
        List<EmployeesDataTO> userData = new ArrayList<>();
        try {
            var listEmployee = this.employeeService.getUserData();
            var listCivilStatus=this.employeeService.getCivilStatus();

            for (EmployeeComplementaryTO item : listEmployee) {
                EmployeesDataTO employee = new EmployeesDataTO();
                var idUser = item.getEmployee().getIdUserDO().getIdUser();

// region datos employee
                employee.setIdEmployee(item.getEmployee().getIdEmployee());
                employee.setIdUser(item.getEmployee().getUser().getIdUser());
                employee.setUserType(item.getEmployee().getUser().getUserType());
                for(CivilStatusTO civilS :listCivilStatus)
                {
                    if(civilS.getCivilCode().toUpperCase().contains(item.getEmployee().getCivilStatus().toUpperCase()) )
                    {
                        employee.setCivilStatus(civilS.getStatusCivil());
                    }

                }

                employee.setName(item.getEmployee().getName());
                employee.setLastName(item.getEmployee().getLastMName());
                employee.setLastMName(item.getEmployee().getLastMName());
                employee.setGender(item.getEmployee().getGender());
                if(item.getEmployee().getClient()!=null && item.getEmployee().getIdProject()!=null )
                {
                    employee.setIdClient(item.getEmployee().getClient().getIdClient());
                    employee.setIdProyect(item.getEmployee().getIdProject().getIdProject());
                }
//endregion
// region  complementary data

                employee.setId(item.getId());
                employee.setIdSwap(item.getIdSwap());
                employee.setRfc(item.getRfc());
                employee.setCurp(item.getCurp());
                employee.setNss(item.getNss());
                employee.setEmail(item.getEmail());
                employee.setPhone(item.getPhone());
                employee.setWorkPermit(item.getWorkPermit());
                employee.setEmailClient(item.getEmailClient());

                employee.setBirthDate(item.getBirthDate() == null ? "" :
                        (FORMAT_TABLE.format(LocalDate.parse(item.getBirthDate().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));

                employee.setBirthState(item.getBirthState());
                employee.setBirthCountry(item.getBirthCountry());
                employee.setPassportNumber(item.getPassportNumber());
                employee.setNationality(item.getNationality());
// endregion
// region domicile


                var domicile = this.employeeService.getEmployeeAdressByIdUser(idUser);
                if (domicile != null) {
                    employee.setIdStreet(domicile.getId());
                    employee.setStreet(domicile.getStreet());
                    employee.setInteriorNumber(domicile.getInteriorNumber());
                    employee.setOutDoorNumber(domicile.getOutDoorNumber());
                    employee.setColony(domicile.getColony());
                    employee.setPostalCode(domicile.getPostalCode());
                    employee.setCity(domicile.getCity());
                    employee.setState(domicile.getState());
                }
// endregion
// region  contrating data
                var contrating = this.employeeService.getContratingDataByIdUser(idUser);
                if (contrating != null) {
                    employee.setIdContrating(contrating.getIdContrating());
                    employee.setIdLevel(contrating.getIdLevel());
                    employee.setQtSalary(contrating.getQtSalary());
                    employee.setDsArea(contrating.getDsArea());
                    employee.setJob(contrating.getJob());
                    employee.setEndOfContract(contrating.getEndOfContract());
                }
// endregion
// region asignation
                var asignation = this.employeeService.getEmployeeAsignationByIdUser(idUser);
                if (asignation != null) {
                    employee.setEmployeePosition(asignation.getEmployeePosition());
                    employee.setCompany(asignation.getCompany());
                    employee.setProyect(asignation.getProyect());
                    employee.setManager(asignation.getManager());
                    employee.setAsinationState(asignation.getState());
                    employee.setAsignationCity(asignation.getCity());
                    employee.setEmailDirectBoss(asignation.getEmailDirectBoss());
                    employee.setTelephoneDirectBoss(asignation.getTelephoneDirectBoss());
                    employee.setStartAssigment(asignation.getStartAssigment());
                    employee.setEndAllocation(asignation.getEndAllocation());
                    employee.setAllocationEmail(asignation.getAllocationEmail());
                    employee.setAllocationSalary(asignation.getAllocationSalary());
                    employee.setEvaluation(asignation.getEvaluation());
                }

// endregion
//region packge compensation
                List<CompensationPackageTO> compenration = this.employeeService.getEmployeeCompensationByIdUser(idUser);
                if (compenration != null)
                {
                    for (CompensationPackageTO serv : compenration
                    ) {
//region case
                        switch (serv.getDsName()) {
                            case "Sueldo_bruto_mensual":
                                employee.setSueldo_bruto_mensual(serv.getValor());
                                break;
                            case "Automovil":
                                employee.setAutomovil(serv.getValor());
                                break;
                            case "Gastos_Automovil":
                                employee.setGastos_Automovil(serv.getValor());
                                break;
                            case "Opcion_Compra":
                                employee.setOpcion_Compra(serv.getValor());
                                break;
                            case "Bono_Mensual":
                                employee.setBono_Mensual(serv.getValor());
                                break;
                            case "Cantidad_Bono_Mensual":
                                employee.setCantidad_Bono_Mensual(serv.getValor());
                                break;
                            case "Bono_Bimestral":
                                employee.setBono_Bimestral(serv.getValor());
                                break;
                            case "Cantidad_Bono_Bimestral":
                                employee.setCantidad_Bono_Bimestral(serv.getValor());
                                break;
                            case "Bono_Trimestral":
                                employee.setBono_Trimestral(serv.getValor());
                                break;
                            case "Cantidad_Bono_Trimestral":
                                employee.setCantidad_Bono_Trimestral(serv.getValor());
                                break;
                            case "Bono_Anual":
                                employee.setBono_Anual(serv.getValor());
                                break;
                            case "Cantidad_Bono_Anual":
                                employee.setCantidad_Bono_Anual(serv.getValor());
                                break;
                            case "Metricas_Otorgamiento_Bono":
                                employee.setMetricas_Otorgamiento_Bono(serv.getValor());
                                break;
                            case "Fondo_de_Ahorro":
                                employee.setFondo_de_Ahorro(serv.getValor());
                                break;
                            case "Cantidad_Fondo_de_Ahorro":
                                employee.setCantidad_Fondo_de_Ahorro(serv.getValor());
                                break;
                            case "Vales_de_Despensa":
                                employee.setVales_de_Despensa(serv.getValor());
                                break;
                            case "Cantidad_Vales_de_Despensa":
                                employee.setCantidad_Vales_de_Despensa(serv.getValor());
                                break;
                            case "Vales_Restaurante":
                                employee.setVales_Restaurante(serv.getValor());
                                break;
                            case "Cantidad_Vales_Restaurante":
                                employee.setCantidad_Vales_Restaurante(serv.getValor());
                                break;
                            case "Vales_Gasolina":
                                employee.setVales_Gasolina(serv.getValor());
                                break;
                            case "Cantidad_Vales_Gasolina":
                                employee.setCantidad_Vales_Gasolina(serv.getValor());
                                break;
                            case "Aguinaldo":
                                employee.setAguinaldo(serv.getValor());
                                break;
                            case "Dias_Aguinaldo":
                                employee.setDias_Aguinaldo(serv.getValor());
                                break;
                            case "Cuantos_dias_de_vacaciones":
                                employee.setCuantos_dias_de_vacaciones(serv.getValor());
                                break;
                            case "Porcentaje_prima_vacacional":
                                employee.setPorcentaje_prima_vacacional(serv.getValor());
                                break;
                            case "Seguro_GM_Mayores":
                                employee.setSeguro_GM_Mayores(serv.getValor());
                                break;
                            case "Seguro_GM_Menores":
                                employee.setSeguro_GM_Menores(serv.getValor());
                                break;
                            case "Seguro_de_vida":
                                employee.setSeguro_de_vida(serv.getValor());
                                break;
                            case "Meses_de_Cobertura_por_Muerte":
                                employee.setMeses_de_Cobertura_por_Muerte(serv.getValor());
                                break;
                            case "Reparto_de_utilidades":
                                employee.setReparto_de_utilidades(serv.getValor());
                                break;
                            case "Ultimo_monto_recibido":
                                employee.setUltimo_monto_recibido(serv.getValor());
                                break;
                            case "Plan_de_pensiones":
                                employee.setPlan_de_pensiones(serv.getValor());
                                break;
                            case "Otra_prestacion":
                                employee.setOtra_prestacion(serv.getValor());
                                break;
                            case "Ingreso_mensual_bruto_integrado":
                                employee.setIngreso_mensual_bruto_integrado(serv.getValor());
                                break;
                            case "Ingreso_anual_bruto_estimado":
                                employee.setIngreso_anual_bruto_estimado(serv.getValor());
                                break;
                        }
                        //endregion
                    }
                }
                //endregion

                userData.add(employee);
            }
        }
        catch (Exception e)
        {
        e.printStackTrace();
        }
        return userData;
    }

}
