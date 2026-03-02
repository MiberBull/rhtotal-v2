package mx.com.axity.services.service.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.AnswerSICOTO;
import mx.com.axity.commons.to.InfoExcelTO;
import mx.com.axity.commons.to.totree.ExcelGenericFormatExportTO;
import mx.com.axity.commons.to.HeadersGenericTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.commons.util.enumerators.EnumTableGeneric;
import mx.com.axity.model.*;
import mx.com.axity.model.annotations.ExelAnnotations;
import mx.com.axity.persistence.*;
import mx.com.axity.services.service.IEmployeesExcelService;
import mx.com.axity.services.service.IGenericTasksService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import static mx.com.axity.commons.util.Constants.*;

@Service
public class GenericTasksServiceImpl implements IGenericTasksService {


    @Autowired
    DiscountDAO discountDAO;

    @Autowired
    BannerDAO bannerDAO;

    @Autowired
    ParameterDAO parameterDAO;

    @Autowired
    NotificationDAO notificationDAO;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    HeaderDisplayDAO headerDisplayDAO;

    @Autowired
    ProjectDAO projectDAO;

    @Autowired
    ContratingDataDAO contratingDataDAO;

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    FintechDAO fintechAdvanceDAO;

    @Autowired
    FintechVeloCashDAO fintechVeloCashDAO;

    @Autowired
    InsuranseDAO insuranceServiceDAO;

    @Autowired
    IEmployeesExcelService employeesExcelService;

    private static Gson g;

    @SuppressWarnings("unchecked")
    @Override
    public ExcelGenericFormatExportTO sendExcel(int section) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {

        switch (section) {
            case Constants.USERS:
                var headerExcelUsers = Arrays.asList(this.parameterDAO.getParameterFromDb(EnumTableGeneric.USERS.getHeaderTitleExcel()).split(Constants.REGEX));
                try{
                    var listEmployees = this.employeesExcelService.getEmployeesByCurpClientProject();

                    var contentExcelUsers = listEmployees.stream().map(t -> {
                        EmployeesDataExcelDO employeeDataDO = new EmployeesDataExcelDO();

                        employeeDataDO.setCivilStatus(null == t.getCivilStatus() ? "" : t.getCivilStatus());
                        employeeDataDO.setName(null == t.getName()  ? "" : t.getName());
                        employeeDataDO.setLastName(null == t.getLastName() ? "" : t.getLastName());
                        employeeDataDO.setLastMName(null == t.getLastMName() ? "" : t.getLastMName());
                        employeeDataDO.setGender(null == t.getGender() ? "" : t.getGender());
                        employeeDataDO.setRfc(null == t.getRfc() ? "" : t.getRfc());
                        employeeDataDO.setCurp(null == t.getCurp() ? "" : t.getCurp());
                        employeeDataDO.setNss(null == t.getNss() ? "" : t.getNss());
                        employeeDataDO.setEmail(null == t.getEmail() ? "" : t.getEmail());
                        employeeDataDO.setPhone(null == t.getPhone() ? "" : t.getPhone());
                        employeeDataDO.setWorkPermit(null == t.getWorkPermit() ? "" : t.getWorkPermit());
                        employeeDataDO.setBirthDate(null == t.getBirthDate() ? "" : t.getBirthDate());
                        employeeDataDO.setBirthState(null == t.getBirthState() ? "" : t.getBirthState());
                        employeeDataDO.setBirthCountry(null == t.getBirthCountry() ? "" : t.getBirthCountry());
                        employeeDataDO.setPassportNumber(null == t.getPassportNumber() ? "" : t.getPassportNumber());
                        employeeDataDO.setStreet(null == t.getStreet() ? "" : t.getStreet());
                        employeeDataDO.setInteriorNumber(null == t.getInteriorNumber() ? "" : t.getInteriorNumber());
                        employeeDataDO.setOutDoorNumber(null == t.getOutDoorNumber() ? "" : t.getOutDoorNumber());
                        employeeDataDO.setColony(null == t.getColony() ? "" : t.getColony());
                        employeeDataDO.setPostalCode(null == t.getPostalCode() ? "" : t.getPostalCode());
                        employeeDataDO.setCity(null == t.getCity() ? "" : t.getCity());
                        employeeDataDO.setState(null == t.getState() ? "" : t.getState());
                        employeeDataDO.setQtSalary(0.0 == t.getQtSalary() ? "" : t.getQtSalary()+"");
                        employeeDataDO.setDsArea(null == t.getDsArea() ? "" : t.getDsArea());
                        employeeDataDO.setJob(null == t.getJob() ? "" : t.getJob());
                        employeeDataDO.setEndOfContract(null == t.getEndOfContract() ? "" :
                                (FORMAT_TABLE.format(LocalDate.parse(t.getEndOfContract().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                        employeeDataDO.setSueldo_bruto_mensual(null == t.getSueldo_bruto_mensual() ? "" : t.getSueldo_bruto_mensual());
                        employeeDataDO.setAutomovil(null == t.getAutomovil() ? "" : t.getAutomovil());
                        employeeDataDO.setGastos_Automovil(null == t.getGastos_Automovil() ? "" : t.getGastos_Automovil());
                        employeeDataDO.setOpcion_Compra(null == t.getOpcion_Compra() ? "" : t.getOpcion_Compra());
                        employeeDataDO.setBono_Mensual(null == t.getBono_Mensual() ? "" : t.getOpcion_Compra());
                        employeeDataDO.setCantidad_Bono_Mensual(null == t.getCantidad_Bono_Mensual()? "" : t.getCantidad_Bono_Mensual());
                        employeeDataDO.setBono_Bimestral(null == t.getBono_Bimestral() ? "" : t.getBono_Bimestral());
                        employeeDataDO.setCantidad_Bono_Bimestral(null == t.getCantidad_Bono_Bimestral() ? "" : t.getCantidad_Bono_Bimestral());
                        employeeDataDO.setBono_Trimestral(null == t.getBono_Trimestral() ? "" : t.getBono_Trimestral());
                        employeeDataDO.setCantidad_Bono_Trimestral(null == t.getCantidad_Bono_Trimestral() ? "" : t.getCantidad_Bono_Trimestral());
                        employeeDataDO.setBono_Anual(null == t.getBono_Anual() ? "" : t.getBono_Anual());
                        employeeDataDO.setCantidad_Bono_Anual(null == t.getCantidad_Bono_Anual() ? "" : t.getCantidad_Bono_Anual());
                        employeeDataDO.setMetricas_Otorgamiento_Bono(null == t.getMetricas_Otorgamiento_Bono() ? "" : t.getMetricas_Otorgamiento_Bono());
                        employeeDataDO.setFondo_de_Ahorro(null == t.getFondo_de_Ahorro() ? "" : t.getFondo_de_Ahorro());
                        employeeDataDO.setCantidad_Fondo_de_Ahorro(null == t.getCantidad_Fondo_de_Ahorro() ? "" : t.getCantidad_Fondo_de_Ahorro());
                        employeeDataDO.setVales_de_Despensa(null == t.getVales_de_Despensa() ? "" : t.getVales_de_Despensa());
                        employeeDataDO.setCantidad_Vales_de_Despensa(null == t.getCantidad_Vales_de_Despensa() ? "" : t.getCantidad_Vales_de_Despensa());
                        employeeDataDO.setVales_Restaurante(null == t.getVales_Restaurante() ? "" : t.getVales_Restaurante());
                        employeeDataDO.setCantidad_Vales_Restaurante(null == t.getCantidad_Vales_Restaurante() ? "" : t.getCantidad_Vales_Restaurante());
                        employeeDataDO.setVales_Gasolina(null == t.getVales_Gasolina() ? "" : t.getVales_Gasolina());
                        employeeDataDO.setCantidad_Vales_Gasolina(null == t.getCantidad_Vales_Gasolina() ? "" : t.getCantidad_Vales_Gasolina());
                        employeeDataDO.setAguinaldo(null == t.getAguinaldo() ? "" : t.getAguinaldo());
                        employeeDataDO.setDias_Aguinaldo(null == t.getDias_Aguinaldo() ? "" : t.getDias_Aguinaldo());
                        employeeDataDO.setCuantos_dias_de_vacaciones(null == t.getCuantos_dias_de_vacaciones() ? "" : t.getCuantos_dias_de_vacaciones());
                        employeeDataDO.setPorcentaje_prima_vacacional(null == t.getPorcentaje_prima_vacacional() ? "" : t.getPorcentaje_prima_vacacional());
                        employeeDataDO.setSeguro_GM_Mayores(null == t.getSeguro_GM_Mayores() ? "" : t.getSeguro_GM_Mayores());
                        employeeDataDO.setSeguro_GM_Menores(null == t.getSeguro_GM_Menores() ? "" : t.getSeguro_GM_Menores());
                        employeeDataDO.setSeguro_de_vida(null == t.getSeguro_de_vida() ? "" : t.getSeguro_de_vida());
                        employeeDataDO.setMeses_de_Cobertura_por_Muerte(null == t.getMeses_de_Cobertura_por_Muerte()  ? "" : t.getMeses_de_Cobertura_por_Muerte());
                        employeeDataDO.setReparto_de_utilidades(null == t.getReparto_de_utilidades() ? "" : t.getReparto_de_utilidades());
                        employeeDataDO.setUltimo_monto_recibido(null == t.getUltimo_monto_recibido() ? "" : t.getUltimo_monto_recibido());
                        employeeDataDO.setPlan_de_pensiones(null == t.getPlan_de_pensiones() ? "" : t.getPlan_de_pensiones());
                        employeeDataDO.setOtra_prestacion(null == t.getOtra_prestacion() ? "" : t.getOtra_prestacion());
                        employeeDataDO.setIngreso_mensual_bruto_integrado(null == t.getIngreso_mensual_bruto_integrado() ? "" : t.getIngreso_mensual_bruto_integrado());
                        employeeDataDO.setIngreso_anual_bruto_estimado(null == t.getIngreso_anual_bruto_estimado() ? "" : t.getIngreso_anual_bruto_estimado());
                        employeeDataDO.setEmployeePosition(null == t.getEmployeePosition() ? "" : t.getEmployeePosition());
                        employeeDataDO.setCompany(null == t.getCompany() ? "" : t.getCompany());
                        employeeDataDO.setProyect(null == t.getProyect() ? "" : t.getProyect());
                        employeeDataDO.setManager(null == t.getManager() ? "" : t.getManager());
                        employeeDataDO.setAsinationState(null == t.getAsinationState() ? "" : t.getAsinationState());
                        employeeDataDO.setAsignationCity(null == t.getAsignationCity() ? "" : t.getAsignationCity());
                        employeeDataDO.setEmailDirectBoss(null == t.getEmailDirectBoss() ? "" : t.getEmailDirectBoss());
                        employeeDataDO.setTelephoneDirectBoss(0 == t.getTelephoneDirectBoss() ? "" : t.getTelephoneDirectBoss()+"");
                        employeeDataDO.setStartAssigment(null == t.getStartAssigment() ? "" :
                                                    (FORMAT_TABLE.format(LocalDate.parse(t.getStartAssigment().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                        employeeDataDO.setEndAllocation(null == t.getEndAllocation() ? "" :
                                                   (FORMAT_TABLE.format(LocalDate.parse(t.getEndAllocation().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                        employeeDataDO.setAllocationEmail(null == t.getAllocationEmail() ? "" : t.getAllocationEmail());
                        employeeDataDO.setAllocationSalary(0.0 == t.getAllocationSalary() ? "" : t.getAllocationSalary()+"");

                        return employeeDataDO;
                    }).collect(Collectors.toList());

                    return new ExcelGenericFormatExportTO(createExelBase64(contentExcelUsers, headerExcelUsers, EnumTableGeneric.USERS.getHeaderPageExcel()));
                }catch (Exception e){
                    throw new BusinessException(e.getMessage(),e);
                }
            case Constants.DISCOUNTS:
                var headerExcelDiscount = Arrays.asList(this.parameterDAO.getParameterFromDb(EnumTableGeneric.DISCOUNTS.getHeaderTitleExcel()).split(Constants.REGEX));
                var listDiscount = (List<DiscountDO>) modelMapper.map(this.discountDAO.findAllDiscountDO(), new TypeToken<List<DiscountDO>>() {
                }.getType());
                var contentExcel = listDiscount.stream().map(t -> {
                    DiscountTableDO discountTableDO = new DiscountTableDO();
                    discountTableDO.setTypeDiscount(t.getTypeDiscount().equalsIgnoreCase("D")?"Descuento":"Beneficio");
                    discountTableDO.setCost(t.getTypeDiscount().equalsIgnoreCase("D")?"N/A":t.getCost()!=null && t.getCost()?"Si":"No");
                    discountTableDO.setCategory(t.getCategory().getCategory());
                    discountTableDO.setSubCategory(t.getSubCategory().getSubcategory());
                    discountTableDO.setSupplier(t.getSupplier());
                    discountTableDO.setTitle(t.getTitle());
                    discountTableDO.setStartDate(t.getStartDate());
                    discountTableDO.setEndDate(t.getEndDate());
                    discountTableDO.setPublicationTime(DateTimeFormatter.ofPattern("hh:mm a").format(t.getPublicationTime()));
                    discountTableDO.setNotificationTime(DateTimeFormatter.ofPattern("hh:mm a").format(t.getNotificationTime()));
                    discountTableDO.setNotificationDetail(t.getNotificationDetail());
                    discountTableDO.setLinkUrl(t.getLinkUrl());
                    discountTableDO.setDescription(t.getDescription());
                    discountTableDO.setDescriptionPreview(t.getDescriptionPreview());
                    discountTableDO.setTermsConditions(t.getTermsConditions());
                    discountTableDO.setStatus(t.getStatus());
                    discountTableDO.setLastUserModifier(t.getLastUserModifier());
                    return discountTableDO;
                }).collect(Collectors.toList());
                return new ExcelGenericFormatExportTO(createExelBase64(contentExcel, headerExcelDiscount, EnumTableGeneric.DISCOUNTS.getHeaderPageExcel()));
            case Constants.BANNERS:
                var headerExcelBanner = Arrays.asList(this.parameterDAO.getParameterFromDb(EnumTableGeneric.BANNERS.getHeaderTitleExcel()).split(Constants.REGEX));
                var contentExcelBanner = (List<BannerDO>) modelMapper.map(this.bannerDAO.findAllBannerDO(), new TypeToken<List<BannerDO>>() {
                }.getType());
                return new ExcelGenericFormatExportTO(createExelBase64(contentExcelBanner, headerExcelBanner, EnumTableGeneric.BANNERS.getHeaderPageExcel()));
            case Constants.PROGRAMMED_NOTIFICATIONS:
                var headerExcelProgrammed = Arrays.asList(this.parameterDAO.getParameterFromDb(EnumTableGeneric.PROGRAMMED_NOTIFICATIONS.getHeaderTitleExcel()).split(Constants.REGEX));
                List<String> programmed = new ArrayList<>();
                programmed.add(NOTIFICATION_STATUS_ACTIVE);
                programmed.add(NOTIFICATION_STATUS_INACTIVO);
                var contentExcelProgrammed = this.notificationDAO.getNotificationStatus(programmed);

                return new ExcelGenericFormatExportTO(createExelBase64(contentExcelProgrammed, headerExcelProgrammed, EnumTableGeneric.PROGRAMMED_NOTIFICATIONS.getHeaderPageExcel()));
            case Constants.SENT_NOTIFICATIONS:
                var headerExcelSend = Arrays.asList(this.parameterDAO.getParameterFromDb(EnumTableGeneric.SENT_NOTIFICATIONS.getHeaderTitleExcel()).split(Constants.REGEX));
                List<String> send = new ArrayList<>();
                send.add(NOTIFICATION_STATUS_ENVIADO);
                var contentExcelSend = this.notificationDAO.getNotificationStatus(send);
                return new ExcelGenericFormatExportTO(createExelBase64(contentExcelSend, headerExcelSend, EnumTableGeneric.SENT_NOTIFICATIONS.getHeaderPageExcel()));

            case Constants.SECURE:

                var headerExcelSecure = Arrays.asList(this.parameterDAO.getParameterFromDb(EnumTableGeneric.SECURE.getHeaderTitleExcel()).split(Constants.REGEX));
                //insuranceService
                var insurance = (List<InsuranceDO>) this.modelMapper.map(this.insuranceServiceDAO.findAllInsuranceExcel(), new TypeToken<List<InsuranceDO>>() {
                }.getType());


                var contentExcelInsurance = insurance.stream().map(t -> {
                    InsuranceTableDO insuranceTableDO = new InsuranceTableDO();

                    LocalDateTime lastModification = t.getPublicationTime();
                    String time = lastModification.format(DateTimeFormatter.ofPattern(FORMAT_TIME));

                    insuranceTableDO.setNameTypeInsurance(t.getInsurangeType().getInsurangeType());
                    insuranceTableDO.setPolicy(t.getPolicy());
                    insuranceTableDO.setInsuranceCarrier(t.getInsuranceCarrier());
                    insuranceTableDO.setUrlInsuranceCarrier(t.getUrl());
                    insuranceTableDO.setPhoneInsuranceCarrier(t.getPhones());
                    insuranceTableDO.setStartDate(t.getStartDate());
                    insuranceTableDO.setEndDate(t.getEndDate());
                    insuranceTableDO.setSum(t.getSum() == null ? "" : Constants.PESOS_SIGN+""+t.getSum());
                    insuranceTableDO.setStatus(t.getStatus());

                    insuranceTableDO.setTimePublication(DateTimeFormatter.ofPattern("hh:mm a").format(t.getPublicationTime()));
                    insuranceTableDO.setNotificationTime(DateTimeFormatter.ofPattern("hh:mm a").format(t.getNotificationTime()));

                    insuranceTableDO.setNotificationTitle(t.getNotificationTitle());
                    insuranceTableDO.setNotificationDetail(t.getNotificationDetail());


                    return insuranceTableDO;
                }).collect(Collectors.toList());

                return new ExcelGenericFormatExportTO(createExelBase64(contentExcelInsurance, headerExcelSecure, EnumTableGeneric.SECURE.getHeaderPageExcel()));

            case Constants.CUSTOMERS:
                var headerExcelCustomer = Arrays.asList(this.parameterDAO.getParameterFromDb(EnumTableGeneric.CUSTOMERS.getHeaderTitleExcel()).split(Constants.REGEX));
                var projects = (List<ProjectDO>) this.modelMapper.map(this.projectDAO.getProjectsExcelAll(), new TypeToken<List<ProjectDO>>() {
                }.getType());

                var contentExcelCustomers = projects.stream().map(t -> {

                    var countAndSum= (Object[]) this.employeeDAO.getCountEmployeeByIdProyect(t.getIdProject())[0];
                    var mountEmploye =this.contratingDataDAO.getSumEmployeeByIdProject(t.getIdProject());
                    CustomerTableDO customerTableDO = new CustomerTableDO();
                    customerTableDO.setCustomer(t.getIdClient().getName());
                    customerTableDO.setProject(t.getName());
                    customerTableDO.setEmployee(countAndSum[0].toString());
                    customerTableDO.setMonthlyIncome(mountEmploye!=null?mountEmploye:0L);
                    customerTableDO.setStatus(t.getStatus());

                    return customerTableDO;
                }).collect(Collectors.toList());



                return new ExcelGenericFormatExportTO(createExelBase64(contentExcelCustomers, headerExcelCustomer, EnumTableGeneric.CUSTOMERS.getHeaderPageExcel()));

            case Constants.FINTECH_ADVANCE_ES:

                var headerExcelFintech = Arrays.asList(this.parameterDAO.getParameterFromDb(EnumTableGeneric.FINTECH_ADVANCE_ADVANCES_ES.getHeaderTitleExcel()).split(Constants.REGEX));
                var fintechList = (List<FintechMyAdvanceDO>) this.modelMapper.map(this.fintechAdvanceDAO.findAllByOrderByLastModificationAsc(Constants.STATUS_ES),new TypeToken<List<FintechMyAdvanceDO>>() {}.getType());

                var contentExcelFintech = fintechList.stream().map(t -> {
                    FintechWaitAdvanceDO fintechWaitDO = new FintechWaitAdvanceDO();

                    if(t.getIdEmployee() != null){
                        fintechWaitDO.setName(t.getIdEmployee().getName() == null ? "" : t.getIdEmployee().getName());
                        fintechWaitDO.setLastName(t.getIdEmployee().getLastName() == null ? "" : t.getIdEmployee().getLastName());
                        fintechWaitDO.setmLastName(t.getIdEmployee().getmLastName() == null ? "" : t.getIdEmployee().getmLastName());
                        if(t.getIdEmployee().getIdCliente() != null){
                            fintechWaitDO.setNameClient(t.getIdEmployee().getIdCliente().getName());
                            fintechWaitDO.setEmailClient(t.getIdEmployee().getIdCliente().getEmail());
                        }else {
                            fintechWaitDO.setNameClient("");
                            fintechWaitDO.setEmailClient("");
                        }

                        if(t.getIdEmployee().getIdProject() != null){
                            fintechWaitDO.setNameProject(t.getIdEmployee().getIdProject().getName() == null ? "" : t.getIdEmployee().getIdProject().getName());
                            fintechWaitDO.setEmailProject(t.getIdEmployee().getIdProject().getEmail() == null ? "" : t.getIdEmployee().getIdProject().getEmail());
                        }else {
                            fintechWaitDO.setNameProject("");
                            fintechWaitDO.setEmailProject("");
                        }
                    }
                    fintechWaitDO.setPaymentPeriod(t.getPaymentPeriod() == null ? "" : t.getPaymentPeriod());

                    fintechWaitDO.setStartDatePeriod(t.getStartDatePP() == null ? "" :
                            (FORMAT_TABLE.format(LocalDate.parse(t.getStartDatePP().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));

                    fintechWaitDO.setSalary(t.getRequisitionAmount() == null ? null : Constants.PESOS_SIGN+""+t.getRequisitionAmount().toString());

                    fintechWaitDO.setRequisitionFolio(t.getRequisitionFolio() == null ? "" : t.getRequisitionFolio());

                    fintechWaitDO.setRequisitionAmount(t.getAmountSolicited() == null ? "" : Constants.PESOS_SIGN+""+t.getAmountSolicited().toString());

                    fintechWaitDO.setAmountDeposit(t.getDepositAmount() == null ? "" : Constants.PESOS_SIGN+""+t.getDepositAmount().toString());

                    fintechWaitDO.setPercentageRequested(t.getPorcSolicited() == null ? "" : t.getPorcSolicited().intValue()+""+Constants.PERCENT_SIGN);

                    fintechWaitDO.setCommissionPercentage(t.getPorcCommission() == null ? "" : t.getPorcCommission().toString()+""+Constants.PERCENT_SIGN);

                    fintechWaitDO.setCommissionLoans(t.getCommission() == null ? "" : Constants.PESOS_SIGN+""+t.getCommission().toString());

                    fintechWaitDO.setCommissionTotal(t.getLoanCommission() == null ? "" : Constants.PESOS_SIGN+""+t.getLoanCommission().toString());

                    fintechWaitDO.setIdEmployeeSico(t.getIdEmployeeSico() == null ? "" : t.getIdEmployeeSico().toString());

                    fintechWaitDO.setApplicationDate(t.getCreationDate() == null ? "" :
                            (FORMAT_TABLE.format(LocalDate.parse(t.getCreationDate().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));

                    fintechWaitDO.setRequestTime(t.getRequisitionTime() == null ? "" :
                                                        DateTimeFormatter.ofPattern("hh:mm:ss a").format(t.getRequisitionTime()));

                    fintechWaitDO.setNextPayment(t.getPaysheetNext() == null ? "" : Constants.PESOS_SIGN+""+t.getPaysheetNext().toString());

                    return fintechWaitDO;
                }).collect(Collectors.toList());

                return new ExcelGenericFormatExportTO(createExelBase64(contentExcelFintech,headerExcelFintech,EnumTableGeneric.FINTECH_ADVANCE_ADVANCES_ES.getHeaderPageExcel()));

            case Constants.FINTECH_VELOCASH_ES:
                var headerExcelFintechES = Arrays.asList(this.parameterDAO.getParameterFromDb(EnumTableGeneric.FINTECH_ADVANCE_VELOCASH_ES.getHeaderTitleExcel()).split(Constants.REGEX));
                var fintechAdvanceESList = (List<FintechVeloCashDO>) this.modelMapper.map(this.fintechVeloCashDAO.findAllByOrderByLastModificationAsc(Constants.STATUS_ES),new TypeToken<List<FintechVeloCashDO>>() {}.getType());

                var contExcelFintechES = fintechAdvanceESList.stream().map(t -> {
                    FintechWaitDO fintechWaitDO = new FintechWaitDO();

                    if(t.getIdEmployee() != null){
                        fintechWaitDO.setName(t.getIdEmployee().getName() == null ? "" : t.getIdEmployee().getName());
                        fintechWaitDO.setLastName(t.getIdEmployee().getLastName() == null ? "" : t.getIdEmployee().getLastName());
                        fintechWaitDO.setmLastName(t.getIdEmployee().getmLastName() == null ? "" : t.getIdEmployee().getmLastName());
                        if(t.getIdEmployee().getIdCliente() != null){
                            fintechWaitDO.setNameClient(t.getIdEmployee().getIdCliente().getName() == null ? "" : t.getIdEmployee().getIdCliente().getName());
                            fintechWaitDO.setEmailClient(t.getIdEmployee().getIdCliente().getEmail() == null ? "" : t.getIdEmployee().getIdCliente().getEmail());
                        }else {
                            fintechWaitDO.setNameClient("");
                            fintechWaitDO.setEmailClient("");
                        }

                        if(t.getIdEmployee().getIdProject() != null){
                            fintechWaitDO.setNameProject(t.getIdEmployee().getIdProject().getName() == null ? "" : t.getIdEmployee().getIdProject().getName());
                            fintechWaitDO.setEmailProject(t.getIdEmployee().getIdProject().getEmail() == null ? "" : t.getIdEmployee().getIdProject().getEmail());
                        }else {
                            fintechWaitDO.setNameProject("");
                            fintechWaitDO.setEmailProject("");
                        }
                    }

                    fintechWaitDO.setPaymentPeriod(t.getPaymentPeriod() == null ? "" : t.getPaymentPeriod());

                    fintechWaitDO.setStartDatePeriod(t.getStartDatePP() == null ? "" :
                                   (FORMAT_TABLE.format(LocalDate.parse(t.getStartDatePP().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));

                    fintechWaitDO.setSalary(t.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+t.getRequisitionAmount().toString());

                    fintechWaitDO.setWorkedDays(t.getWorkedDays() == null ? "" : t.getWorkedDays().toString() );

                    fintechWaitDO.setRequisitionFolio(t.getRequisitionFolio() == null ? "" : t.getRequisitionFolio());

                    fintechWaitDO.setRequisitionAmount(t.getAmountSolicited() == null ? "" : Constants.PESOS_SIGN+""+t.getAmountSolicited().toString());

                    fintechWaitDO.setPercentageRequested(t.getPorcSolicited() == null ? "" : t.getPorcSolicited().toString()+Constants.PERCENT_SIGN);

                    fintechWaitDO.setCommissionIVA(t.getCommission() == null ? "" : Constants.PESOS_SIGN+""+t.getCommission().toString());

                    fintechWaitDO.setCommissionLoans(t.getLoanComission() == null ? "" : Constants.PESOS_SIGN+""+t.getLoanComission().toString());

                    fintechWaitDO.setAmountDeposit(t.getQtDepositMount() == null ? "" : Constants.PESOS_SIGN+""+t.getQtDepositMount().toString());

                    fintechWaitDO.setNextPayment(t.getPaysheetNext() == null ? "" : Constants.PESOS_SIGN+""+t.getPaysheetNext().toString());

                    fintechWaitDO.setApplicationDate(t.getCreationDate() == null ? "" :
                             (FORMAT_TABLE.format(LocalDate.parse(t.getCreationDate().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));

                    fintechWaitDO.setRequestTime(t.getDt_requisition_time() == null ? "" :
                                                DateTimeFormatter.ofPattern("hh:mm:ss a").format(t.getDt_requisition_time()));

                    fintechWaitDO.setIdEmployeeSico(t.getIdEmployeeSico() == null ? "" : t.getIdEmployeeSico().toString());

                    return fintechWaitDO;
                }).collect(Collectors.toList());
                return new ExcelGenericFormatExportTO(createExelBase64(contExcelFintechES,headerExcelFintechES,EnumTableGeneric.FINTECH_ADVANCE_VELOCASH_ES.getHeaderPageExcel()));

            case Constants.FINTECH_ADVANCE_AP:
                g = new Gson();
                var headerExcelFintechAdvanceAP = Arrays.asList(this.parameterDAO.getParameterFromDb(EnumTableGeneric.FINTECH_ADVANCE_AP.getHeaderTitleExcel()).split(Constants.REGEX));
                var fintechAdvanceList = (List<FintechMyAdvanceDO>) this.modelMapper.map(this.fintechAdvanceDAO.findAllByOrderByLastModificationAsc(Constants.STATUS_AP),new TypeToken<List<FintechMyAdvanceDO>>() {}.getType());

                var contExcelFintech = fintechAdvanceList.stream().map(t -> {
                    FintechApprovedAdvance fintechWaitDO = new FintechApprovedAdvance();

                    if(t.getIdEmployee() != null){
                        fintechWaitDO.setName(t.getIdEmployee().getName() == null ? "" : t.getIdEmployee().getName());
                        fintechWaitDO.setLastName(t.getIdEmployee().getLastName() == null ? "" : t.getIdEmployee().getLastName());
                        fintechWaitDO.setmLastName(t.getIdEmployee().getmLastName() == null ? "" : t.getIdEmployee().getmLastName());
                        if(t.getIdEmployee().getIdCliente() != null){
                            fintechWaitDO.setNameClient(t.getIdEmployee().getIdCliente().getName() == null ? "" : t.getIdEmployee().getIdCliente().getName());
                            fintechWaitDO.setEmailClient(t.getIdEmployee().getIdCliente().getEmail() == null ? "" : t.getIdEmployee().getIdCliente().getEmail());
                        }else{
                            fintechWaitDO.setNameClient("");
                            fintechWaitDO.setEmailClient("");
                        }

                        if(t.getIdEmployee().getIdProject() != null){
                            fintechWaitDO.setNameProject(t.getIdEmployee().getIdProject().getName() == null ? "" : t.getIdEmployee().getIdProject().getName());
                            fintechWaitDO.setEmailProject(t.getIdEmployee().getIdProject().getEmail() == null ? "" : t.getIdEmployee().getIdProject().getEmail());
                        }else {
                            fintechWaitDO.setNameProject("");
                            fintechWaitDO.setEmailProject("");
                        }
                    }

                    fintechWaitDO.setPaymentPeriod(t.getPaymentPeriod() == null ? "" : t.getPaymentPeriod());
                    fintechWaitDO.setStartDatePeriod(t.getStartDatePP() == null ? "" :
                                              (FORMAT_TABLE.format(LocalDate.parse(t.getStartDatePP().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                    fintechWaitDO.setSalary(t.getPaysheetNext() == null ? "" : t.getPaysheetNext().toString());
                    fintechWaitDO.setRequisitionFolio(t.getRequisitionFolio() == null ? "" : t.getRequisitionFolio());
                    fintechWaitDO.setAuthorizedBy(t.getLastUserModifier() == null ? "" : t.getLastUserModifier());
                    fintechWaitDO.setRequisitionAmount(t.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+t.getRequisitionAmount().toString());
                    fintechWaitDO.setPercentageRequested(t.getPorcSolicited() == null ? "" : t.getPorcSolicited().intValue()+""+Constants.PERCENT_SIGN);
                    fintechWaitDO.setCommissionLoans(t.getCommission() == null ? "" : Constants.PESOS_SIGN+""+t.getCommission().toString());
                    fintechWaitDO.setAmountDeposit(t.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+t.getRequisitionAmount().toString());
                    fintechWaitDO.setApplicationDate(t.getCreationDate() == null ? "" :
                                                  (FORMAT_TABLE.format(LocalDate.parse(t.getCreationDate().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                    fintechWaitDO.setRequestTime(t.getRequisitionTime() == null ? "" :
                                               DateTimeFormatter.ofPattern("hh:mm:ss a").format(t.getRequisitionTime()));
                    fintechWaitDO.setDateApproval(t.getDateApprobation() == null ? "" :
                                    (FORMAT_TABLE.format(LocalDate.parse(t.getDateApprobation().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                    fintechWaitDO.setApprovalTime(t.getTimeApprobation() == null ? "" :
                                               DateTimeFormatter.ofPattern("hh:mm:ss a").format(t.getTimeApprobation()));
                    fintechWaitDO.setDateResponseSWAP(t.getDateResponseSwap() == null ? "" :
                             (FORMAT_TABLE.format(LocalDate.parse(t.getDateResponseSwap().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                    fintechWaitDO.setTimeResponseSWAP(t.getTimeResponseSwap() == null ? "" :
                                                       DateTimeFormatter.ofPattern("hh:mm:ss a").format(t.getTimeResponseSwap()));
                    fintechWaitDO.setNextPayment(t.getPaysheetNext() == null ? "" : Constants.PESOS_SIGN+""+t.getPaysheetNext().toString());



                    fintechWaitDO.setFolioSWAP(t.getFolioSwap() == null ? "" : t.getFolioSwap());
                    fintechWaitDO.setOperationDescriptionSWAP(t.getDescriptionApprobation() == null ? "" : t.getDescriptionApprobation());

                    var answerCadena = t.getFolioConfirmationSico();
                    AnswerSICOTO answerTO = g.fromJson(answerCadena, AnswerSICOTO.class);

                    if(answerCadena == null){
                        fintechWaitDO.setConfirmationFolioSICO("");
                    }else{
                        fintechWaitDO.setConfirmationFolioSICO(answerTO.getFolioConfirmacio() == null ? "" : answerTO.getFolioConfirmacio());
                    }

                    fintechWaitDO.setOperationresultSICO(t.getFolioConfirmationSico() == null ? "" : t.getFolioConfirmationSico());




                    return fintechWaitDO;
                }).collect(Collectors.toList());
                return new ExcelGenericFormatExportTO(createExelBase64(contExcelFintech,headerExcelFintechAdvanceAP,EnumTableGeneric.FINTECH_ADVANCE_AP.getHeaderPageExcel()));

            case Constants.FINTECH_VELOCASH_AP:
                g = new Gson();
                var headerExcelFintechVeloCashAP = Arrays.asList(this.parameterDAO.getParameterFromDb(EnumTableGeneric.FINTECH_VELOCASH_AP.getHeaderTitleExcel()).split(Constants.REGEX));
                var fintechListVeloCashAP = (List<FintechVeloCashDO>) this.modelMapper.map(this.fintechVeloCashDAO.findAllByOrderByLastModificationAsc(Constants.STATUS_AP),new TypeToken<List<FintechVeloCashDO>>() {}.getType());

                var contExcelVeloCashAP = fintechListVeloCashAP.stream().map(t -> {
                    FintechApprovedVeloCashDO fintechWaitDO = new FintechApprovedVeloCashDO();


                    if(t.getIdEmployee() != null){
                        fintechWaitDO.setName(t.getIdEmployee().getName() == null ? "" : t.getIdEmployee().getName());
                        fintechWaitDO.setLastName(t.getIdEmployee().getLastName() == null ? "" : t.getIdEmployee().getLastName());
                        fintechWaitDO.setmLastName(t.getIdEmployee().getmLastName() == null ? "" : t.getIdEmployee().getmLastName());
                        if(t.getIdEmployee().getIdCliente() != null){
                            fintechWaitDO.setNameClient(t.getIdEmployee().getIdCliente().getName() == null ? "" : t.getIdEmployee().getIdCliente().getName());
                            fintechWaitDO.setEmailClient(t.getIdEmployee().getIdCliente().getEmail() == null ? "" : t.getIdEmployee().getIdCliente().getEmail());
                        }else {
                            fintechWaitDO.setNameClient("");
                            fintechWaitDO.setEmailClient("");
                        }
                        if(t.getIdEmployee().getIdProject() != null){
                            fintechWaitDO.setNameProject(t.getIdEmployee().getIdProject().getName() == null ? "" : t.getIdEmployee().getIdProject().getName());
                            fintechWaitDO.setEmailProject(t.getIdEmployee().getIdProject().getEmail() == null ? "" : t.getIdEmployee().getIdProject().getEmail());
                        }else {
                            fintechWaitDO.setNameProject("");
                            fintechWaitDO.setEmailProject("");
                        }
                    }

                    fintechWaitDO.setPaymentPeriod(t.getPaymentPeriod() == null ? "" : t.getPaymentPeriod());
                    fintechWaitDO.setStartDatePeriod(t.getStartDatePP() == null ? "" :
                                       (FORMAT_TABLE.format(LocalDate.parse(t.getStartDatePP().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                    
                    fintechWaitDO.setRequisitionFolio(t.getRequisitionFolio());

                    fintechWaitDO.setAuthorizedBy(t.getLastUserModifier() == null ? "" : t.getLastUserModifier());
                    fintechWaitDO.setRequisitionAmount(t.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+t.getRequisitionAmount().toString());
                    fintechWaitDO.setPercentageRequested(t.getPorcSolicited() == null ? "" : t.getPorcSolicited().toString()+Constants.PERCENT_SIGN);
                    fintechWaitDO.setCommissionLoans(t.getCommission() == null ? "" : Constants.PESOS_SIGN+""+t.getCommission().toString());
                    fintechWaitDO.setAmountDeposit(t.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+t.getRequisitionAmount().toString());
                    fintechWaitDO.setApplicationDate(t.getCreationDate() == null ? "" :
                                             (FORMAT_TABLE.format(LocalDate.parse(t.getCreationDate().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                    fintechWaitDO.setRequestTime(t.getDt_requisition_time() == null ? "" :
                                                       DateTimeFormatter.ofPattern("hh:mm a").format(t.getDt_requisition_time()));
                    fintechWaitDO.setDateApproval(t.getDateApprobation() == null ? "" :
                                       (FORMAT_TABLE.format(LocalDate.parse(t.getDateApprobation().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                    fintechWaitDO.setApprovalTime(t.getTimeApprobation() == null ? "" :
                                                  DateTimeFormatter.ofPattern("hh:mm a").format(t.getTimeApprobation()));
                    fintechWaitDO.setDateResponseSWAP(t.getDateResponseSwap() == null ? "" :
                                             (FORMAT_TABLE.format(LocalDate.parse(t.getDateResponseSwap().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                    fintechWaitDO.setTimeResponseSWAP(t.getTimeResponseSwap() == null ? "" :
                                            DateTimeFormatter.ofPattern("hh:mm a").format(t.getTimeResponseSwap()));
                    fintechWaitDO.setNextPayment(t.getPaysheetNext() == null ? "" : Constants.PESOS_SIGN+""+t.getPaysheetNext().toString());

                    fintechWaitDO.setFolioSWAP(t.getFolioSwap() == null ? "" : t.getFolioSwap());
                    fintechWaitDO.setOperationDescriptionSWAP(t.getDescriptionApprobation() == null ? "" : t.getDescriptionApprobation());

                    var answerCadena = t.getFolioConfirmationSico();
                    AnswerSICOTO answerTO = g.fromJson(answerCadena, AnswerSICOTO.class);

                    if(answerCadena == null){
                        fintechWaitDO.setConfirmationFolioSICO("");
                    }else{
                        fintechWaitDO.setConfirmationFolioSICO(answerTO.getFolioConfirmacio() == null ? "" : answerTO.getFolioConfirmacio());
                    }

                    fintechWaitDO.setOperationresultSICO(answerCadena == null ? "" : answerCadena);

                    return fintechWaitDO;
                }).collect(Collectors.toList());

                return new ExcelGenericFormatExportTO(createExelBase64(contExcelVeloCashAP,headerExcelFintechVeloCashAP,EnumTableGeneric.FINTECH_VELOCASH_AP.getHeaderPageExcel()));

            case Constants.FINTECH_ADVANCE_R:
                var headerExcelFintechAdvanceR = Arrays.asList(this.parameterDAO.getParameterFromDb(EnumTableGeneric.FINTECH_ADVANCE_R.getHeaderTitleExcel()).split(Constants.REGEX));

                var fintechListVelo = (List<FintechMyAdvanceDO>) this.modelMapper.map(this.fintechAdvanceDAO.findAllByOrderByLastModificationAsc(Constants.STATUS_R),new TypeToken<List<FintechMyAdvanceDO>>() {}.getType());

                var listFintechR = fintechListVelo.stream().map(t -> {
                     FintechRejectedAdvanceDO fintechWaitDO = new FintechRejectedAdvanceDO();


                    if(t.getIdEmployee() != null){
                        fintechWaitDO.setName(t.getIdEmployee().getName() == null ? "" : t.getIdEmployee().getName());
                        fintechWaitDO.setLastName(t.getIdEmployee().getLastName() == null ? "" : t.getIdEmployee().getLastName());
                        fintechWaitDO.setmLastName(t.getIdEmployee().getmLastName() == null ? "" : t.getIdEmployee().getmLastName());
                        if(t.getIdEmployee().getIdCliente() != null){
                            fintechWaitDO.setNameClient(t.getIdEmployee().getIdCliente().getName() == null ? "" : t.getIdEmployee().getIdCliente().getName());
                            fintechWaitDO.setEmailClient(t.getIdEmployee().getIdCliente().getEmail() == null ? "" : t.getIdEmployee().getIdCliente().getEmail());
                        }else {
                            fintechWaitDO.setNameClient("");
                            fintechWaitDO.setEmailClient("");
                        }

                        if(t.getIdEmployee().getIdProject() != null){
                            fintechWaitDO.setNameProject(t.getIdEmployee().getIdProject().getName() == null ? "" : t.getIdEmployee().getIdProject().getName());
                            fintechWaitDO.setEmailProject(t.getIdEmployee().getIdProject().getEmail() == null ? "" : t.getIdEmployee().getIdProject().getEmail());
                        }else{
                            fintechWaitDO.setNameProject("");
                            fintechWaitDO.setEmailProject("");
                        }
                    }

                    fintechWaitDO.setPaymentPeriod(t.getPaymentPeriod() == null ? "" : t.getPaymentPeriod());
                    fintechWaitDO.setStartDatePeriod(t.getStartDatePP() == null ? "" :
                            (FORMAT_TABLE.format(LocalDate.parse(t.getStartDatePP().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                    fintechWaitDO.setSalary(t.getPaysheetNext() == null ? "" : Constants.PESOS_SIGN+""+t.getPaysheetNext().toString());
                    fintechWaitDO.setRejectedBy(t.getLastUserModifier() == null ? "" : t.getLastUserModifier());
                    fintechWaitDO.setRequisitionAmount(t.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+t.getRequisitionAmount().toString());

                    fintechWaitDO.setCommissionLoans(t.getCommission() == null ? "" : Constants.PESOS_SIGN+""+t.getCommission().toString());
                    fintechWaitDO.setAmountDeposit(t.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+t.getRequisitionAmount().toString());
                    fintechWaitDO.setApplicationDate(t.getCreationDate() == null ? "" :
                            (FORMAT_TABLE.format(LocalDate.parse(t.getCreationDate().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                    fintechWaitDO.setRequestTime(t.getRequisitionTime() == null ? "" :
                                           DateTimeFormatter.ofPattern("hh:mm:ss a").format(t.getRequisitionTime()));

                    fintechWaitDO.setDateResponseSWAP(t.getDateResponseSwap() == null ? "" :
                             (FORMAT_TABLE.format(LocalDate.parse(t.getDateResponseSwap().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                    fintechWaitDO.setTimeResponseSWAP(t.getTimeResponseSwap() == null ? "" :
                                               DateTimeFormatter.ofPattern("hh:mm:ss a").format(t.getTimeResponseSwap()));

                    fintechWaitDO.setReasonReject(t.getReasonReject() == null ? "" : t.getReasonReject());

                    fintechWaitDO.setEntity(t.getEntity() == null ? "" : t.getEntity());

                    return fintechWaitDO;
                }).collect(Collectors.toList());

                return new ExcelGenericFormatExportTO(createExelBase64(listFintechR,headerExcelFintechAdvanceR,EnumTableGeneric.FINTECH_ADVANCE_R.getHeaderPageExcel()));

            case Constants.FINTECH_VELOCASH_R:

                var headerExcelFintechVeloCashR = Arrays.asList(this.parameterDAO.getParameterFromDb(EnumTableGeneric.FINTECH_VELOCASH_R.getHeaderTitleExcel()).split(Constants.REGEX));

                var fintechListVeloCash = (List<FintechVeloCashDO>) this.modelMapper.map(this.fintechVeloCashDAO.findAllByOrderByLastModificationAsc(Constants.STATUS_R),new TypeToken<List<FintechVeloCashDO>>() {}.getType());

                var listFintechVeloCashR = fintechListVeloCash.stream().map(t -> {
                    FintechRejectedVeloCash fintechWaitDO = new FintechRejectedVeloCash();

                    if(t.getIdEmployee() != null){
                        fintechWaitDO.setName(t.getIdEmployee().getName() == null ? "" : t.getIdEmployee().getName());
                        fintechWaitDO.setLastName(t.getIdEmployee().getLastName() == null ? "" : t.getIdEmployee().getLastName());
                        fintechWaitDO.setmLastName(t.getIdEmployee().getmLastName() == null ? "" : t.getIdEmployee().getmLastName());
                        if(t.getIdEmployee().getIdCliente() != null){
                            fintechWaitDO.setNameClient(t.getIdEmployee().getIdCliente().getName() == null ? "" : t.getIdEmployee().getIdCliente().getName());
                            fintechWaitDO.setEmailClient(t.getIdEmployee().getIdCliente().getEmail() == null ? "" : t.getIdEmployee().getIdCliente().getEmail());
                        }else {
                            fintechWaitDO.setNameClient("");
                            fintechWaitDO.setEmailClient("");
                        }
                        if(t.getIdEmployee().getIdProject() != null){
                            fintechWaitDO.setNameProject(t.getIdEmployee().getIdProject().getName() == null ? "" : t.getIdEmployee().getIdProject().getName());
                            fintechWaitDO.setEmailProject(t.getIdEmployee().getIdProject().getEmail() == null ? "" : t.getIdEmployee().getIdProject().getEmail());
                        }else {
                            fintechWaitDO.setNameProject("");
                            fintechWaitDO.setEmailProject("");
                        }
                    }

                    fintechWaitDO.setPaymentPeriod(t.getPaymentPeriod() == null ? "" : t.getPaymentPeriod());
                    fintechWaitDO.setStartDatePeriod(t.getStartDatePP() == null ? "" :
                            (FORMAT_TABLE.format(LocalDate.parse(t.getStartDatePP().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));

                    fintechWaitDO.setSalary(t.getPaysheetNext() == null ? "" : Constants.PESOS_SIGN+""+t.getPaysheetNext().toString());
                    fintechWaitDO.setRejectedBy(t.getLastUserModifier());
                    fintechWaitDO.setRequisitionAmount(t.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+t.getRequisitionAmount().toString());

                    fintechWaitDO.setWorkedDays(t.getWorkedDays() == null ? "" : t.getWorkedDays().toString());

                    fintechWaitDO.setCommissionLoans(t.getCommission() == null ? "" : Constants.PESOS_SIGN+""+t.getCommission().toString());
                    fintechWaitDO.setAmountDeposit(t.getRequisitionAmount() == null ? "" : Constants.PESOS_SIGN+""+t.getRequisitionAmount().toString());

                    fintechWaitDO.setApplicationDate(t.getCreationDate() == null ? "" :
                            (FORMAT_TABLE.format(LocalDate.parse(t.getCreationDate().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                    fintechWaitDO.setRequestTime(t.getDt_requisition_time() == null ? "" :
                                          DateTimeFormatter.ofPattern("hh:mm:ss a").format(t.getDt_requisition_time()));

                    fintechWaitDO.setDateResponseSWAP(t.getDateResponseSwap() == null ? "" :
                            (FORMAT_TABLE.format(LocalDate.parse(t.getDateResponseSwap().format((DateTimeFormatter.ofPattern(FORMAT_DATE)))))));
                    fintechWaitDO.setTimeResponseSWAP(t.getTimeResponseSwap() == null ? "" :
                            DateTimeFormatter.ofPattern("hh:mm:ss a").format(t.getTimeResponseSwap()));
                    fintechWaitDO.setReasonRejection(t.getReasonReject() == null ? "" : t.getReasonReject());
                    fintechWaitDO.setEntity(t.getEntity() == null ? "" : t.getEntity());

                    return fintechWaitDO;
                }).collect(Collectors.toList());

                return new ExcelGenericFormatExportTO(createExelBase64(listFintechVeloCashR,headerExcelFintechVeloCashR,EnumTableGeneric.FINTECH_VELOCASH_R.getHeaderPageExcel()));
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String createExelBase64(List list, List headers, String namePage) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var getter = new ArrayList<>();
        var workbook = new XSSFWorkbook();
        var sheet = workbook.createSheet(namePage);
        var headerRow = sheet.createRow(0);
        int rowNum = 1;
        getMethodName(Arrays.asList(list.get(0).getClass().getDeclaredFields()), getter);
        headersExcel(headers, headerRow, getCellStyleExcel(workbook));
        bodyExcel(list, getter, sheet, rowNum);
        adjustCellsExcel(headers, sheet);

        return new org.apache.commons.codec.binary.Base64().encodeToString(getBytesExcel(workbook));
    }

    private void adjustCellsExcel(List headers, XSSFSheet sheet) {
        for (int i = 0; i < headers.size(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private XSSFCellStyle getCellStyleExcel(XSSFWorkbook workbook) {
        var headerFont = workbook.createFont();
        headerFont.setBold(Boolean.TRUE);
        headerFont.setFontHeightInPoints((short) 11);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        var headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        return headerCellStyle;
    }

    private byte[] getBytesExcel(XSSFWorkbook workbook) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
       // FileOutputStream fileOut = new FileOutputStream("hola1.xlsx");
        //workbook.write(fileOut);
        workbook.write(outputStream);

        byte[] bytes = outputStream.toByteArray();
        workbook.close();
        outputStream.close();
        return bytes;
    }

    private void getMethodName(List<Field> fieldList, ArrayList<Object> getter) {
        fieldList.forEach(t -> {
            if (!t.getAnnotation(ExelAnnotations.class).getMethod().equals("N/R"))
                getter.add(t.getAnnotation(ExelAnnotations.class).getMethod());
        });
    }

    private void headersExcel(List headers, XSSFRow headerRow, XSSFCellStyle headerCellStyle) {
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(String.valueOf(headers.get(i)));
            cell.setCellStyle(headerCellStyle);
        }
    }

    private void bodyExcel(List list, ArrayList<Object> getter, XSSFSheet sheet, int rowNum) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        for (Object typeClass : list) {
            Row row = sheet.createRow(rowNum++);

            for (int i = 0; i < getter.size(); i++) {
                Method getNameMethod = typeClass.getClass().getDeclaredMethod(String.valueOf(getter.get(i)));
                String value = String.valueOf(getNameMethod.invoke(typeClass, null));
                if(getNameMethod.getName().equalsIgnoreCase("getStartDate") ||
                        getNameMethod.getName().equalsIgnoreCase("getEndDate")
                        ){
                    LocalDateTime localTime = (LocalDateTime)getNameMethod.invoke(typeClass, null);
                    if(typeClass.getClass().getName().equalsIgnoreCase("mx.com.axity.model.NotificationDO")){
                        value = localTime.format(dateTimeFormatter).toString();
                    }else{
                        value = localTime.format(dateFormatter).toString();
                    }
                }
                if(getNameMethod.getName().equalsIgnoreCase("getStatus")){
                    value = (value.equalsIgnoreCase("A")?"Activo":
                                value.equalsIgnoreCase("I")?"Inactivo":
                                value.equalsIgnoreCase("Activo")?"Activo":
                                value.equalsIgnoreCase("Inactivo")?"Inactivo":
                                value.equalsIgnoreCase("E")?"Enviado":"Sin Estatus");
                }

                if((getNameMethod.getName().equalsIgnoreCase("getTimePublication") ||
                        getNameMethod.getName().equalsIgnoreCase("getNotificationTime")) &&(
                        !typeClass.getClass().getName().equalsIgnoreCase("mx.com.axity.model.DiscountTableDO")&&
                        !typeClass.getClass().getName().equalsIgnoreCase("mx.com.axity.model.InsuranceTableDO")
                        )){
                    LocalTime localTime = (LocalTime)getNameMethod.invoke(typeClass, null);
                    value = localTime.format(timeFormatter).toString();
                }
                row.createCell(i).setCellValue(value);
            }
        }
    }

    @Override
    public HeadersGenericTO getHeader(String nameHeader) {
        var headerRole = this.headerDisplayDAO.getNameHeader(nameHeader);
        return new HeadersGenericTO(headerRole);
    }

    @Override
    public ExcelGenericFormatExportTO getExcelUsers(InfoExcelTO infoExcelUsersTO) {

        var headerExcelUsers = Arrays.asList(this.parameterDAO.getParameterFromDb(EnumTableGeneric.USERS.getHeaderTitleExcel()).split(Constants.REGEX));
        try{
            var listEmployees = infoExcelUsersTO.getAllEmployeesTO();

            var contentExcelUsers = listEmployees.stream().map(t -> {
                EmployeesDataExcelDO employeeDataDO = new EmployeesDataExcelDO();

                employeeDataDO.setCivilStatus(null == t.getCivilStatus() ? "" : t.getCivilStatus());
                employeeDataDO.setName(null == t.getName()  ? "" : t.getName());
                employeeDataDO.setLastName(null == t.getLastName() ? "" : t.getLastName());
                employeeDataDO.setLastMName(null == t.getLastMName() ? "" : t.getLastMName());
                employeeDataDO.setGender(null == t.getGender() ? "" : t.getGender());
                employeeDataDO.setRfc(null == t.getRfc() ? "" : t.getRfc());
                employeeDataDO.setCurp(null == t.getCurp() ? "" : t.getCurp());
                employeeDataDO.setNss(null == t.getNss() ? "" : t.getNss());
                employeeDataDO.setEmail(null == t.getEmail() ? "" : t.getEmail());
                employeeDataDO.setPhone(null == t.getPhone() ? "" : t.getPhone());
                employeeDataDO.setWorkPermitConfirm(null == t.getWorkPermitConfirm() ? "" : t.getWorkPermitConfirm());
                employeeDataDO.setWorkPermit(null == t.getWorkPermit() ? "" : t.getWorkPermit());
                employeeDataDO.setBirthDate(null == t.getBirthDate() ? "" : t.getBirthDate());
                employeeDataDO.setBirthState(null == t.getBirthState() ? "" : t.getBirthState());
                employeeDataDO.setBirthCountry(null == t.getBirthCountry() ? "" : t.getBirthCountry());
                employeeDataDO.setPassportNumber(null == t.getPassportNumber() ? "" : t.getPassportNumber());
                employeeDataDO.setStreet(null == t.getStreet() ? "" : t.getStreet());
                employeeDataDO.setInteriorNumber(null == t.getInteriorNumber() ? "" : t.getInteriorNumber());
                employeeDataDO.setOutDoorNumber(null == t.getOutDoorNumber() ? "" : t.getOutDoorNumber());
                employeeDataDO.setColony(null == t.getColony() ? "" : t.getColony());
                employeeDataDO.setPostalCode(null == t.getPostalCode() ? "" : t.getPostalCode());
                employeeDataDO.setCity(null == t.getCity() ? "" : t.getCity());
                employeeDataDO.setState(null == t.getState() ? "" : t.getState());
                employeeDataDO.setQtSalary(0.0 == t.getQtSalary() ? "" : Constants.PESOS_SIGN+""+t.getQtSalary()+"");
                employeeDataDO.setDsArea(null == t.getDsArea() ? "" : t.getDsArea());
                employeeDataDO.setJob(null == t.getJob() ? "" : t.getJob());
                employeeDataDO.setEndOfContract(null == t.getEndOfContract() ? "" : t.getEndOfContract());
                employeeDataDO.setSkill(null == t.getSkill() ? "" : t.getSkill().toString());
                employeeDataDO.setSueldo_bruto_mensual(null == t.getSueldo_bruto_mensual() ? "" : Constants.PESOS_SIGN+""+t.getSueldo_bruto_mensual());
                employeeDataDO.setAutomovil(null == t.getAutomovil() ? "" : t.getAutomovil());
                employeeDataDO.setGastos_Automovil(null == t.getGastos_Automovil() ? "" : t.getGastos_Automovil());
                employeeDataDO.setOpcion_Compra(null == t.getOpcion_Compra() ? "" : t.getOpcion_Compra());
                employeeDataDO.setBono_Mensual(null == t.getBono_Mensual() ? "" : t.getBono_Mensual());
                employeeDataDO.setCantidad_Bono_Mensual(null == t.getCantidad_Bono_Mensual()? "" : t.getCantidad_Bono_Mensual());
                employeeDataDO.setBono_Bimestral(null == t.getBono_Bimestral() ? "" : t.getBono_Bimestral());
                employeeDataDO.setCantidad_Bono_Bimestral(null == t.getCantidad_Bono_Bimestral() ? "" : t.getCantidad_Bono_Bimestral());
                employeeDataDO.setBono_Trimestral(null == t.getBono_Trimestral() ? "" : t.getBono_Trimestral());
                employeeDataDO.setCantidad_Bono_Trimestral(null == t.getCantidad_Bono_Trimestral() ? "" : t.getCantidad_Bono_Trimestral());
                employeeDataDO.setBono_Anual(null == t.getBono_Anual() ? "" : t.getBono_Anual());
                employeeDataDO.setCantidad_Bono_Anual(null == t.getCantidad_Bono_Anual() ? "" : t.getCantidad_Bono_Anual());
                employeeDataDO.setMetricas_Otorgamiento_Bono(null == t.getMetricas_Otorgamiento_Bono() ? "" : t.getMetricas_Otorgamiento_Bono());
                employeeDataDO.setFondo_de_Ahorro(null == t.getFondo_de_Ahorro() ? "" : t.getFondo_de_Ahorro());
                employeeDataDO.setCantidad_Fondo_de_Ahorro(null == t.getCantidad_Fondo_de_Ahorro() ? "" : t.getCantidad_Fondo_de_Ahorro());
                employeeDataDO.setVales_de_Despensa(null == t.getVales_de_Despensa() ? "" : t.getVales_de_Despensa());
                employeeDataDO.setCantidad_Vales_de_Despensa(null == t.getCantidad_Vales_de_Despensa() ? "" : t.getCantidad_Vales_de_Despensa());
                employeeDataDO.setVales_Restaurante(null == t.getVales_Restaurante() ? "" : t.getVales_Restaurante());
                employeeDataDO.setCantidad_Vales_Restaurante(null == t.getCantidad_Vales_Restaurante() ? "" : t.getCantidad_Vales_Restaurante());
                employeeDataDO.setVales_Gasolina(null == t.getVales_Gasolina() ? "" : t.getVales_Gasolina());
                employeeDataDO.setCantidad_Vales_Gasolina(null == t.getCantidad_Vales_Gasolina() ? "" : t.getCantidad_Vales_Gasolina());
                employeeDataDO.setAguinaldo(null == t.getAguinaldo() ? "" : t.getAguinaldo());
                employeeDataDO.setDias_Aguinaldo(null == t.getDias_Aguinaldo() ? "" : t.getDias_Aguinaldo());
                employeeDataDO.setCuantos_dias_de_vacaciones(null == t.getCuantos_dias_de_vacaciones() ? "" : t.getCuantos_dias_de_vacaciones());
                employeeDataDO.setPorcentaje_prima_vacacional(null == t.getPorcentaje_prima_vacacional() ? "" : t.getPorcentaje_prima_vacacional());
                employeeDataDO.setSeguro_GM_Mayores(null == t.getSeguro_GM_Mayores() ? "" : t.getSeguro_GM_Mayores());
                employeeDataDO.setSeguro_GM_Menores(null == t.getSeguro_GM_Menores() ? "" : t.getSeguro_GM_Menores());
                employeeDataDO.setSeguro_de_vida(null == t.getSeguro_de_vida() ? "" : t.getSeguro_de_vida());
                employeeDataDO.setMeses_de_Cobertura_por_Muerte(null == t.getMeses_de_Cobertura_por_Muerte()  ? "" : t.getMeses_de_Cobertura_por_Muerte());
                employeeDataDO.setReparto_de_utilidades(null == t.getReparto_de_utilidades() ? "" : t.getReparto_de_utilidades());
                employeeDataDO.setUltimo_monto_recibido(null == t.getUltimo_monto_recibido() ? "" : t.getUltimo_monto_recibido());
                employeeDataDO.setPlan_de_pensiones(null == t.getPlan_de_pensiones() ? "" : t.getPlan_de_pensiones());
                employeeDataDO.setOtra_prestacion(null == t.getOtra_prestacion() ? "" : t.getOtra_prestacion());
                employeeDataDO.setIngreso_mensual_bruto_integrado(null == t.getIngreso_mensual_bruto_integrado() ? "" : t.getIngreso_mensual_bruto_integrado());
                employeeDataDO.setIngreso_anual_bruto_estimado(null == t.getIngreso_anual_bruto_estimado() ? "" : t.getIngreso_anual_bruto_estimado());
                employeeDataDO.setEmployeePosition(null == t.getEmployeePosition() ? "" : t.getEmployeePosition());
                employeeDataDO.setCompany(null == t.getClient() ? "" : t.getClient());
                employeeDataDO.setProyect(null == t.getProject() ? "" : t.getProject());
                employeeDataDO.setManager(null == t.getManager() ? "" : t.getManager());
                employeeDataDO.setAsinationState(null == t.getAsinationState() ? "" : t.getAsinationState());
                employeeDataDO.setAsignationCity(null == t.getAsignationCity() ? "" : t.getAsignationCity());
                employeeDataDO.setEmailDirectBoss(null == t.getEmailDirectBoss() ? "" : t.getEmailDirectBoss());
                employeeDataDO.setTelephoneDirectBoss(null == t.getTelephoneDirectBoss() ? "" : t.getTelephoneDirectBoss()+"");



                employeeDataDO.setStartAssigment(null == t.getStartAssigment() ? "" : t.getStartAssigment());
                employeeDataDO.setEndAllocation(null == t.getEndAllocation() ? "" : t.getEndAllocation());
                employeeDataDO.setAllocationEmail(null == t.getAllocationEmail() ? "" : t.getAllocationEmail());
                employeeDataDO.setUserType(null == t.getUserType() ? "" : t.getUserType());
                employeeDataDO.setAllocationSalary(0.0 == t.getAllocationSalary() ? "" : Constants.PESOS_SIGN+""+t.getAllocationSalary()+"");
                employeeDataDO.setEvaluation(null == t.getEvaluation() ? "" : t.getEvaluation());
                employeeDataDO.setStatus(null == t.getStatus() ? "" : t.getStatus());
                employeeDataDO.setLastUserModifier(null == t.getLastUserModifier() ? "" : t.getLastUserModifier());


                return employeeDataDO;
            }).collect(Collectors.toList());

            return new ExcelGenericFormatExportTO(createExelBase64(contentExcelUsers, headerExcelUsers, EnumTableGeneric.USERS.getHeaderPageExcel()));
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }

    }
}
