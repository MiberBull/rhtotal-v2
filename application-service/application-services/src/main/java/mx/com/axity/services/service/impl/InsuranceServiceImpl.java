package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.*;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.EventualyDO;
import mx.com.axity.model.InsuranceDO;
import mx.com.axity.model.InsuranceTypeDO;
import mx.com.axity.model.PlanCoverageDO;
import mx.com.axity.persistence.*;
import mx.com.axity.services.service.IInsuranceService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static mx.com.axity.commons.util.Constants.*;
import static mx.com.axity.commons.util.Constants.CADENA_VACIA;

@Service
public class InsuranceServiceImpl implements IInsuranceService {

    @Autowired
    InsuranceTypeDAO insuranceTypeDAO;

    @Autowired
    NotificationAssignmentDAO assignmentDAO;

    @Autowired
    InsuranceCarrierDAO insuranceCarrierDAO;

    @Autowired
    InsuranseDAO insuranseDAO;
    @Autowired
    EventualyDAO eventualyDAO;
    @Autowired
    PlanCoverageDAO planCoverageDAO;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public InsuranceTableTO getOneInsurance(int insurance) {
        InsuranceDO insuranceDO = this.insuranseDAO.findById((long) insurance).get();
        LocalDateTime lastModification = insuranceDO.getPublicationTime();
        //String date = lastModification.format(DateTimeFormatter.ofPattern(FORMAT_DATE));
        String time = lastModification.format(DateTimeFormatter.ofPattern(FORMAT_TIME));

        InsuranceTableTO insuranceTO = new InsuranceTableTO();

        insuranceTO.setIdInsurance(insuranceDO.getIdInsurance());
        insuranceTO.setIdTypeInsurance(insuranceDO.getInsurangeType().getIdInsurangeType());
        insuranceTO.setPolicy(insuranceDO.getPolicy());
        insuranceTO.setCoverage(insuranceDO.getCoverage());
        insuranceTO.setNoCertificate(insuranceDO.getCertificateNumber());
        insuranceTO.setTypePolicy(insuranceDO.getTypePolicy());
        insuranceTO.setUrlInsuranceCarrier(insuranceDO.getUrl());
        insuranceTO.setInsuranceCarrier(insuranceDO.getInsuranceCarrier());
        insuranceTO.setPhoneInsuranceCarrier(insuranceDO.getPhones());
        insuranceTO.setStartDate(insuranceDO.getStartDate());
        insuranceTO.setEndDate(insuranceDO.getEndDate());
        insuranceTO.setSum(insuranceDO.getSum());
        insuranceTO.setInsurancePolicyPdf(insuranceDO.getContractPdf());
        insuranceTO.setFileName(insuranceDO.getFileName());
        insuranceTO.setTimePublication(LocalTime.parse(time));
        insuranceTO.setNotificationTime(insuranceDO.getNotificationTime());
        insuranceTO.setNotificationTitle(insuranceDO.getNotificationTitle());
        insuranceTO.setNotificationDetail(insuranceDO.getNotificationDetail());
        insuranceTO.setStatus(insuranceDO.getStatus());
        insuranceTO.setVehicleDescription(insuranceDO.getVehicleDescription());
        insuranceTO.setTypeVehicle(insuranceDO.getTypeVehicle());
        insuranceTO.setSerialNumber(insuranceDO.getSerialNumber());
        insuranceTO.setPlates(insuranceDO.getPlates());
        insuranceTO.setYear(insuranceDO.getYear());
        insuranceTO.setMarca(insuranceDO.getBrand());
        insuranceTO.setModel(insuranceDO.getModel());
        insuranceTO.setUse(insuranceDO.getUse());
        insuranceTO.setService(insuranceDO.getService());
        insuranceTO.setDescription(insuranceDO.getDescription());

        return insuranceTO;
    }

    @Override
    public EventualyTO getOneEventualy(int eventualy) {
        var eventualityOneDO = this.eventualyDAO.findById((long) eventualy).get();
        EventualyTO eventualyOneTO = new EventualyTO();
        eventualyOneTO.setIdEventualy(eventualityOneDO.getIdEventualy());
        eventualyOneTO.setIdInsurance(eventualityOneDO.getIdInsurance().getIdInsurance());
        eventualyOneTO.setTitleEventualy(eventualityOneDO.getTitleEventualy());
        eventualyOneTO.setDescription(eventualityOneDO.getDescription());
        eventualyOneTO.setSumAssured(eventualityOneDO.getSumAssured());
        eventualyOneTO.setSecuredPremium(eventualityOneDO.getSecuredPremium());
        eventualyOneTO.setDeductibles(eventualityOneDO.getDeductibles());
        eventualyOneTO.setStatus(eventualityOneDO.getStatus());
        return eventualyOneTO;
    }

    @Override
    public PlanCoverageTO getOnePlanCoverage(int coverage) {
        var coverageOneDO = this.planCoverageDAO.findById((long) coverage).get();
        PlanCoverageTO  planCoverageTO = new PlanCoverageTO();
        planCoverageTO.setIdCobertura(coverageOneDO.getIdCobertura());
        planCoverageTO.setIdInsurance(coverageOneDO.getIdInsurance().getIdInsurance());
        planCoverageTO.setTitleEventualy(coverageOneDO.getTitleEventualy());
        planCoverageTO.setDescription(coverageOneDO.getDescription());
        planCoverageTO.setLevel(coverageOneDO.getLevel());
        planCoverageTO.setSumAssured(coverageOneDO.getSumAssured());
        planCoverageTO.setDeductibles(coverageOneDO.getDeductibles());
        planCoverageTO.setCoInsurance(coverageOneDO.getCoInsurance());
        planCoverageTO.setStatus(coverageOneDO.getStatus());
        return planCoverageTO;
    }

    @Override
    public List<EventualyTO> getAllEventualy(int page, int idInsurance) {
        var listEventualDO = this.eventualyDAO.findAllEventualy(PageRequest.of(page, Constants.LIMIT_PAGE),(long) idInsurance);

        List<EventualyTO> listEventualyTO = new ArrayList<>();

        for(EventualyDO eventualy : listEventualDO.getContent()){
            EventualyTO eventualyTO = new EventualyTO();
            eventualyTO.setIdEventualy(eventualy.getIdEventualy());
            eventualyTO.setIdInsurance(eventualy.getIdInsurance().getIdInsurance());
            eventualyTO.setTitleEventualy(eventualy.getTitleEventualy());
            eventualyTO.setDescription(eventualy.getDescription());
            eventualyTO.setSumAssured(eventualy.getSumAssured());
            eventualyTO.setSecuredPremium(eventualy.getSecuredPremium());
            eventualyTO.setDeductibles(eventualy.getDeductibles());
            eventualyTO.setStatus(eventualy.getStatus());
            listEventualyTO.add(eventualyTO);
        }

        return listEventualyTO;
    }

    @Override
    public List<PlanCoverageTO> getAllPlanCoverage(int page, int idInsurance) {
        var listCoverageDO = this.planCoverageDAO.findAllCoverage(PageRequest.of(page, Constants.LIMIT_PAGE),(long) idInsurance);

        List<PlanCoverageTO> listCoverageTO = new ArrayList<>();

        for(PlanCoverageDO coverageDO : listCoverageDO.getContent()){
            PlanCoverageTO planCoverageTO = new PlanCoverageTO();
            planCoverageTO.setIdCobertura(coverageDO.getIdCobertura());
            planCoverageTO.setIdInsurance(coverageDO.getIdInsurance().getIdInsurance());
            planCoverageTO.setTitleEventualy(coverageDO.getTitleEventualy());
            planCoverageTO.setDescription(coverageDO.getDescription());
            planCoverageTO.setLevel(coverageDO.getLevel());
            planCoverageTO.setSumAssured(coverageDO.getSumAssured());
            planCoverageTO.setDeductibles(coverageDO.getDeductibles());
            planCoverageTO.setStatus(coverageDO.getStatus());
            planCoverageTO.setCoInsurance(coverageDO.getCoInsurance());

            listCoverageTO.add(planCoverageTO);
        }

        return listCoverageTO;
    }

    @Override
    public Boolean saveUpdateEventualy(EventualyTO eventualyTO) {
        if(eventualyTO.getIdEventualy() == null){
            EventualyDO eventualyOneDO = new EventualyDO();

            eventualyOneDO.setIdInsurance(this.insuranseDAO.findById((long) eventualyTO.getIdInsurance()).get());
            eventualyOneDO.setTitleEventualy(eventualyTO.getTitleEventualy());
            eventualyOneDO.setDescription(eventualyTO.getDescription());
            eventualyOneDO.setSumAssured(eventualyTO.getSumAssured());
            eventualyOneDO.setSecuredPremium(eventualyTO.getSecuredPremium());
            eventualyOneDO.setDeductibles(eventualyTO.getDeductibles());
            eventualyOneDO.setStatus(eventualyTO.getStatus());
            eventualyOneDO.setLastUserModifier(eventualyTO.getLastUserModifier());
            eventualyOneDO.setLastModification(LocalDateTime.now());
            eventualyOneDO.setCreationDate(eventualyTO.getCreationDate());
            eventualyOneDO.setFgActive(eventualyTO.getFgActive());
            this.eventualyDAO.save(eventualyOneDO);
            return true;
        }

        EventualyDO eventualyOneDO = new EventualyDO();

        eventualyOneDO.setIdEventualy(eventualyTO.getIdEventualy());
        eventualyOneDO.setIdInsurance(this.insuranseDAO.findById((long) eventualyTO.getIdInsurance()).get());
        eventualyOneDO.setTitleEventualy(eventualyTO.getTitleEventualy());
        eventualyOneDO.setDescription(eventualyTO.getDescription());
        eventualyOneDO.setSumAssured(eventualyTO.getSumAssured());
        eventualyOneDO.setSecuredPremium(eventualyTO.getSecuredPremium());
        eventualyOneDO.setDeductibles(eventualyTO.getDeductibles());
        eventualyOneDO.setStatus(eventualyTO.getStatus());
        eventualyOneDO.setLastUserModifier(eventualyTO.getLastUserModifier());
        eventualyOneDO.setLastModification(LocalDateTime.now());
        eventualyOneDO.setCreationDate(eventualyTO.getCreationDate());
        eventualyOneDO.setFgActive(eventualyTO.getFgActive());
        this.eventualyDAO.save(eventualyOneDO);

        return true;
    }

    @Override
    public Boolean saveUpdatePlanCoverage(PlanCoverageTO planCoverageTO) {

        if(planCoverageTO == null){

            PlanCoverageDO planCoverageDO = new PlanCoverageDO();
            planCoverageDO.setIdInsurance(this.insuranseDAO.findById((long) planCoverageTO.getIdInsurance()).get());
            planCoverageDO.setTitleEventualy(planCoverageTO.getTitleEventualy());
            planCoverageDO.setDescription(planCoverageTO.getDescription());
            planCoverageDO.setLevel(planCoverageTO.getLevel());
            planCoverageDO.setSumAssured(planCoverageTO.getSumAssured());
            planCoverageDO.setDeductibles(planCoverageTO.getDeductibles());
            planCoverageDO.setCoInsurance(planCoverageTO.getCoInsurance());
            planCoverageDO.setStatus(planCoverageTO.getStatus());
            planCoverageDO.setLastUserModifier(planCoverageTO.getLastUserModifier());
            planCoverageDO.setLastModification(LocalDateTime.now());
            planCoverageDO.setCreationDate(planCoverageTO.getCreationDate());
            planCoverageDO.setFgActive(planCoverageTO.getFgActive());

            this.planCoverageDAO.save(planCoverageDO);
            return true;

        }

        PlanCoverageDO planCoverageDO = new PlanCoverageDO();
        planCoverageDO.setIdCobertura(planCoverageTO.getIdCobertura());
        planCoverageDO.setIdInsurance(this.insuranseDAO.findById((long) planCoverageTO.getIdInsurance()).get());
        planCoverageDO.setTitleEventualy(planCoverageTO.getTitleEventualy());
        planCoverageDO.setDescription(planCoverageTO.getDescription());
        planCoverageDO.setLevel(planCoverageTO.getLevel());
        planCoverageDO.setSumAssured(planCoverageTO.getSumAssured());
        planCoverageDO.setDeductibles(planCoverageTO.getDeductibles());
        planCoverageDO.setCoInsurance(planCoverageTO.getCoInsurance());
        planCoverageDO.setStatus(planCoverageTO.getStatus());
        planCoverageDO.setLastUserModifier(planCoverageTO.getLastUserModifier());
        planCoverageDO.setLastModification(LocalDateTime.now());
        planCoverageDO.setCreationDate(planCoverageTO.getCreationDate());
        planCoverageDO.setFgActive(planCoverageTO.getFgActive());

        this.planCoverageDAO.save(planCoverageDO);

        return true;
    }

    @Override
    public List<InsuranceTableTO> showAllInsurance(int page, String status, String insuranceCarrier, String startDate, String endDate, String author) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);



        var listInsuranceTO = this.insuranseDAO.findAllInsuranse(PageRequest.of(page, Constants.LIMIT_PAGE),
                                                               "".equals(insuranceCarrier) ? null : insuranceCarrier,
                                                               "".equals(startDate) ? null : LocalDate.parse(startDate, inputFormatter).atStartOfDay(),
                                                               "".equals(endDate) ? null : LocalDate.parse(endDate, inputFormatter).atStartOfDay().plus(1, ChronoUnit.DAYS),
                                                               "".equals(author) ? null : author);
        List<InsuranceTableTO> listInsuranceTableTO = new ArrayList<>();

        for( InsuranceDO insuranceTableDO : listInsuranceTO.getContent() ) {

            LocalDateTime lastModification = insuranceTableDO.getPublicationTime();
            String time = lastModification.format(DateTimeFormatter.ofPattern(FORMAT_TIME));

            InsuranceTableTO insuranceTableOneTO = new InsuranceTableTO();
            insuranceTableOneTO.setIdInsurance(insuranceTableDO.getIdInsurance());
            insuranceTableOneTO.setIdTypeInsurance(insuranceTableDO.getInsurangeType().getIdInsurangeType());
            insuranceTableOneTO.setNameTypeInsurance(insuranceTableDO.getInsurangeType().getInsurangeType());
            insuranceTableOneTO.setPolicy(insuranceTableDO.getPolicy());
            insuranceTableOneTO.setInsuranceCarrier(insuranceTableDO.getInsuranceCarrier());
            insuranceTableOneTO.setUrlInsuranceCarrier(insuranceTableDO.getUrl());
            insuranceTableOneTO.setPhoneInsuranceCarrier(insuranceTableDO.getPhones());
            insuranceTableOneTO.setStartDate(insuranceTableDO.getStartDate());
            insuranceTableOneTO.setEndDate(insuranceTableDO.getEndDate());
            insuranceTableOneTO.setSum(insuranceTableDO.getSum());
            insuranceTableOneTO.setStatus(insuranceTableDO.getStatus());
            insuranceTableOneTO.setTimePublication(LocalTime.parse(time));
            insuranceTableOneTO.setNotificationTime(insuranceTableDO.getNotificationTime());
            insuranceTableOneTO.setNotificationTitle(insuranceTableDO.getNotificationTitle());
            insuranceTableOneTO.setNotificationDetail(insuranceTableDO.getNotificationDetail());

            listInsuranceTableTO.add(insuranceTableOneTO);
        }

        return listInsuranceTableTO;
    }

    @Override
    public InsuranceDO saveOrUpdateNotification(InsuranceTableTO insuranceTO) {
        InsuranceDO insuranceDO = null;
        if(insuranceTO.getIdTypeInsurance() == 1){
            insuranceDO = this.getInsuranceVehicular(insuranceTO);
        }else if(insuranceTO.getIdTypeInsurance() == 2){
            insuranceDO = this.lifeInsurance(insuranceTO);
        }else if(insuranceTO.getIdTypeInsurance() == 3){
            insuranceDO = this.insuranceMajorMedicalExpenses(insuranceTO);
        }else if(insuranceTO.getIdTypeInsurance() == 4){
            insuranceDO = this.insuranceMajorMedicalExpenses(insuranceTO);
        }
        return insuranceDO;
    }

    @Override
    public CountRowTO getNumberRow(String insuranceCarrier, String startDate, String endDate, String author) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        return new CountRowTO(this.insuranseDAO.getNumberRow("".equals(insuranceCarrier) ? null : insuranceCarrier,
                                                            "".equals(startDate) ? null : LocalDate.parse(startDate, inputFormatter).atStartOfDay(),
                                                            "".equals(endDate) ? null : LocalDate.parse(endDate, inputFormatter).atStartOfDay().plus(1, ChronoUnit.DAYS),
                                                            "".equals(author) ? null : author));
    }

    @Override
    public CountRowTO getNumberRowEventualy(int idInsurance) {
        return new CountRowTO(this.eventualyDAO.getNumberRowEventualy((long) idInsurance));
    }

    @Override
    public CountRowTO getNumberRowCoverage(int idInsurance) {
        return new CountRowTO(this.planCoverageDAO.getNumberRowCoverage((long) idInsurance));
    }

    @Override
    public List<Map<String,String>> getInsuranseUser(List<Long> idUser) {

        List<Object[]> objects = this.insuranseDAO.geAllInsurangeUser(idUser,LocalDateTime.now(),LocalDate.now());
        List<Map<String,String>> insuranceUser = new ArrayList<>();
        Map<String,String> data ;
        for (Object[] object :objects) {
            data = new HashMap<>();
            data.put("insurangeType",String.valueOf(object[0]));
            data.put("policy",String.valueOf(object[1]));
            data.put("insuranceCarrier",String.valueOf(object[2]));
            data.put("idInsurance",String.valueOf(object[3]));
            data.put("idInsurangeType",String.valueOf(object[4]));
            insuranceUser.add(data);
        }
        return insuranceUser;
    }

    @Override
    public Map<String, String> geInsurangeByInsurance(Long idInsurance) {
        List<Object[]> objects = this.insuranseDAO.geInsurangeByInsurance(idInsurance);
        Map<String,String> insurance = null ;
        for (Object[] object : objects){
            insurance = new HashMap<>();
            insurance.put("idInsurance",String.valueOf(object[0]));
            insurance.put("insurangeType",String.valueOf(object[1]));
            insurance.put("policy",String.valueOf(object[2]));
            insurance.put("insuranceCarrier",String.valueOf(object[3]));
            insurance.put("url",String.valueOf(object[4]));
            insurance.put("phones",String.valueOf(object[5]));
            insurance.put("coverage",String.valueOf(object[6]));
            insurance.put("endDate",String.valueOf(object[7]));
            insurance.put("contractPdf",String.valueOf(object[8]));
            insurance.put("individualCertificate",String.valueOf(object[9]));
            insurance.put("typePolicy",String.valueOf(object[10]));
            insurance.put("startDate",String.valueOf(object[11]));
        }
        return insurance;

    }

    @Override
    public List<Long> getInsurangeUserAssigment(Long idUser) {
        return this.assignmentDAO.getInsurangeUserAssigment(idUser);
    }

    @Override
    public List<Map<String, String>> getCoverageInsurance(Long idInsurance) {
        List<Object[]> coverageInsurance = this.planCoverageDAO.getCoverageInsurance(idInsurance);
        List<Map<String,String>> mapCoverage = new ArrayList<>();
        Map<String,String> map;
        for (Object[] object : coverageInsurance) {
            map = new HashMap<>();
            map.put("description",String.valueOf(object[0]));
            map.put("level",String.valueOf(object[1]));
            map.put("sumAssured",String.valueOf(object[2]));
            map.put("deductibles",String.valueOf(object[3]));
            map.put("coInsurance",String.valueOf(object[4]));
            map.put("titleDescription","Descripción plan/Cobertura");
            map.put("titleLevel","Nivel hospitalario");
            map.put("titleSumAssured","Suma asegurada");
            map.put("titleDeductibles","Deducible");
            map.put("titleCoInsurance","Coaseguro");
            mapCoverage.add(map);
        }
        return mapCoverage;
    }

    public InsuranceDO getInsuranceVehicular(InsuranceTableTO insuranceTO){
        if(insuranceTO.getIdInsurance() == null){
            var insurance = new InsuranceDO();
            insurance.setInsurangeType(this.insuranceTypeDAO.findById(insuranceTO.getIdTypeInsurance()).get());
            insurance.setPolicy(insuranceTO.getPolicy());
            insurance.setCoverage(insuranceTO.getCoverage());
            insurance.setUrl(insuranceTO.getUrlInsuranceCarrier());
            insurance.setInsuranceCarrier(insuranceTO.getInsuranceCarrier());
            insurance.setPhones(insuranceTO.getPhoneInsuranceCarrier());
            insurance.setStartDate(insuranceTO.getStartDate());
            insurance.setEndDate(insuranceTO.getEndDate());
            insurance.setSum(insuranceTO.getSum());
            insurance.setContractPdf(insuranceTO.getInsurancePolicyPdf());
            insurance.setFileName(insuranceTO.getFileName());
            insurance.setNotificationTitle(insuranceTO.getNotificationTitle());
            insurance.setStatus(insuranceTO.getStatus());
            insurance.setVehicleDescription(insuranceTO.getVehicleDescription());
            insurance.setTypeVehicle(insuranceTO.getTypeVehicle());
            insurance.setSerialNumber(insuranceTO.getSerialNumber());
            insurance.setPlates(insuranceTO.getPlates());
            insurance.setYear(insuranceTO.getYear());
            insurance.setBrand(insuranceTO.getMarca());
            insurance.setModel(insuranceTO.getModel());
            insurance.setService(insuranceTO.getService());
            insurance.setUse(insuranceTO.getUse());
            insurance.setNotificationDetail(insuranceTO.getNotificationDetail());
            insurance.setPublicationTime(LocalDateTime.of(LocalDate.now(), insuranceTO.getTimePublication()));
            insurance.setNotificationTime(insuranceTO.getNotificationTime());
            insurance.setDescription(insuranceTO.getDescription());
            insurance.setLastUserModification(insuranceTO.getLastUserModifier());
            insurance.setCreationDate(insuranceTO.getCreationDate());
            insurance.setLastModification(insuranceTO.getLastModifier());
            insurance.setActive(true);

            return this.insuranseDAO.save(insurance);
        }

        InsuranceDO insurance = new InsuranceDO();
        insurance.setIdInsurance(insuranceTO.getIdInsurance());
        insurance.setInsurangeType(this.insuranceTypeDAO.findById(insuranceTO.getIdTypeInsurance()).get());
        insurance.setPolicy(insuranceTO.getPolicy());
        insurance.setCoverage(insuranceTO.getCoverage());
        insurance.setUrl(insuranceTO.getUrlInsuranceCarrier());
        insurance.setInsuranceCarrier(insuranceTO.getInsuranceCarrier());
        insurance.setPhones(insuranceTO.getPhoneInsuranceCarrier());
        insurance.setStartDate(insuranceTO.getStartDate());
        insurance.setEndDate(insuranceTO.getEndDate());
        insurance.setSum(insuranceTO.getSum());
        insurance.setContractPdf(insuranceTO.getInsurancePolicyPdf());
        insurance.setFileName(insuranceTO.getFileName());
        insurance.setStatus(insuranceTO.getStatus());
        insurance.setVehicleDescription(insuranceTO.getVehicleDescription());
        insurance.setTypeVehicle(insuranceTO.getTypeVehicle());
        insurance.setSerialNumber(insuranceTO.getSerialNumber());
        insurance.setPlates(insuranceTO.getPlates());
        insurance.setYear(insuranceTO.getYear());
        insurance.setBrand(insuranceTO.getMarca());
        insurance.setModel(insuranceTO.getModel());
        insurance.setService(insuranceTO.getService());
        insurance.setUse(insuranceTO.getUse());
        insurance.setNotificationDetail(insuranceTO.getNotificationDetail());
        insurance.setPublicationTime(LocalDateTime.of(LocalDate.now(), insuranceTO.getTimePublication()));
        insurance.setNotificationTime(insuranceTO.getNotificationTime());
        insurance.setDescription(insuranceTO.getDescription());
        insurance.setLastUserModification(insuranceTO.getLastUserModifier());
        insurance.setCreationDate(insuranceTO.getCreationDate());
        insurance.setLastModification(insuranceTO.getLastModifier());
        insurance.setActive(true);
        insurance.setNotificationTitle(insuranceTO.getNotificationTitle());

        insurance.setActive(true);
        return this.insuranseDAO.save(insurance);
    }

    public InsuranceDO lifeInsurance(InsuranceTableTO insuranceTO){

        if(insuranceTO.getIdInsurance() == null){
            InsuranceDO insurance = new InsuranceDO();
            insurance.setInsurangeType(this.insuranceTypeDAO.findById(insuranceTO.getIdTypeInsurance()).get());
            insurance.setPolicy(insuranceTO.getPolicy());
            insurance.setCertificateNumber(insuranceTO.getNoCertificate());
            insurance.setUrl(insuranceTO.getUrlInsuranceCarrier());
            insurance.setInsuranceCarrier(insuranceTO.getInsuranceCarrier());
            insurance.setPhones(insuranceTO.getPhoneInsuranceCarrier());
            insurance.setStartDate(insuranceTO.getStartDate());
            insurance.setEndDate(insuranceTO.getEndDate());
            insurance.setSum(insuranceTO.getSum());
            insurance.setContractPdf(insuranceTO.getInsurancePolicyPdf());
            insurance.setFileName(insuranceTO.getFileName());
            insurance.setStatus(insuranceTO.getStatus());
            insurance.setNotificationDetail(insuranceTO.getNotificationDetail());
            insurance.setPublicationTime(LocalDateTime.of(LocalDate.now(), insuranceTO.getTimePublication()));
            insurance.setNotificationTime(insuranceTO.getNotificationTime());
            insurance.setLastUserModification(insuranceTO.getLastUserModifier());
            insurance.setCreationDate(insuranceTO.getCreationDate());
            insurance.setLastModification(insuranceTO.getLastModifier());
            insurance.setActive(true);
            insurance.setNotificationTitle(insuranceTO.getNotificationTitle());
            return this.insuranseDAO.save(insurance);
        }
        InsuranceDO insurance = new InsuranceDO();
        insurance.setIdInsurance(insuranceTO.getIdInsurance());
        insurance.setInsurangeType(this.insuranceTypeDAO.findById(insuranceTO.getIdTypeInsurance()).get());
        insurance.setPolicy(insuranceTO.getPolicy());
        insurance.setCertificateNumber(insuranceTO.getNoCertificate());
        insurance.setUrl(insuranceTO.getUrlInsuranceCarrier());
        insurance.setInsuranceCarrier(insuranceTO.getInsuranceCarrier());
        insurance.setPhones(insuranceTO.getPhoneInsuranceCarrier());
        insurance.setStartDate(insuranceTO.getStartDate());
        insurance.setEndDate(insuranceTO.getEndDate());
        insurance.setSum(insuranceTO.getSum());
        insurance.setContractPdf(insuranceTO.getInsurancePolicyPdf());
        insurance.setFileName(insuranceTO.getFileName());
        insurance.setStatus(insuranceTO.getStatus());
        insurance.setNotificationDetail(insuranceTO.getNotificationDetail());
        insurance.setPublicationTime(LocalDateTime.of(LocalDate.now(), insuranceTO.getTimePublication()));
        insurance.setNotificationTime(insuranceTO.getNotificationTime());
        insurance.setLastUserModification(insuranceTO.getLastUserModifier());
        insurance.setCreationDate(insuranceTO.getCreationDate());
        insurance.setLastModification(insuranceTO.getLastModifier());
        insurance.setActive(true);
        insurance.setNotificationTitle(insuranceTO.getNotificationTitle());
        return this.insuranseDAO.save(insurance);
    }


    public InsuranceDO insuranceMajorMedicalExpenses(InsuranceTableTO insuranceTO){
        if(insuranceTO.getIdInsurance() == null){
            var insurance = new InsuranceDO();
            insurance.setInsurangeType(this.insuranceTypeDAO.findById(insuranceTO.getIdTypeInsurance()).get());
            insurance.setPolicy(insuranceTO.getPolicy());
            insurance.setTypePolicy(insuranceTO.getTypePolicy());
            insurance.setUrl(insuranceTO.getUrlInsuranceCarrier());
            insurance.setInsuranceCarrier(insuranceTO.getInsuranceCarrier());
            insurance.setPhones(insuranceTO.getPhoneInsuranceCarrier());
            insurance.setStartDate(insuranceTO.getStartDate());
            insurance.setEndDate(insuranceTO.getEndDate());
            insurance.setSum(insuranceTO.getSum());
            insurance.setContractPdf(insuranceTO.getInsurancePolicyPdf());
            insurance.setFileName(insuranceTO.getFileName());
            insurance.setStatus(insuranceTO.getStatus());
            insurance.setNotificationDetail(insuranceTO.getNotificationDetail());
            insurance.setPublicationTime(LocalDateTime.of(LocalDate.now(), insuranceTO.getTimePublication()));
            insurance.setNotificationTime(insuranceTO.getNotificationTime());
            insurance.setActive(insuranceTO.getActive());
            insurance.setLastUserModification(insuranceTO.getLastUserModifier());
            insurance.setCreationDate(insuranceTO.getCreationDate());
            insurance.setLastModification(insuranceTO.getLastModifier());
            insurance.setActive(true);
            insurance.setNotificationTitle(insuranceTO.getNotificationTitle());
            insurance.setLastUserModification(insuranceTO.getLastUserModifier());
            insurance.setCreationDate(insuranceTO.getCreationDate());
            insurance.setLastModification(insuranceTO.getLastModifier());
            return this.insuranseDAO.save(insurance);
        }

        var insurance = new InsuranceDO();
        insurance.setIdInsurance(insuranceTO.getIdInsurance());
        insurance.setInsurangeType(this.insuranceTypeDAO.findById(insuranceTO.getIdTypeInsurance()).get());
        insurance.setPolicy(insuranceTO.getPolicy());
        insurance.setTypePolicy(insuranceTO.getTypePolicy());
        insurance.setUrl(insuranceTO.getUrlInsuranceCarrier());
        insurance.setInsuranceCarrier(insuranceTO.getInsuranceCarrier());
        insurance.setPhones(insuranceTO.getPhoneInsuranceCarrier());
        insurance.setStartDate(insuranceTO.getStartDate());
        insurance.setEndDate(insuranceTO.getEndDate());
        insurance.setSum(insuranceTO.getSum());
        insurance.setContractPdf(insuranceTO.getInsurancePolicyPdf());
        insurance.setFileName(insuranceTO.getFileName());
        insurance.setStatus(insuranceTO.getStatus());
        insurance.setNotificationDetail(insuranceTO.getNotificationDetail());
        insurance.setPublicationTime(LocalDateTime.of(LocalDate.now(), insuranceTO.getTimePublication()));
        insurance.setNotificationTime(insuranceTO.getNotificationTime());
        insurance.setActive(insuranceTO.getActive());
        insurance.setLastUserModification(insuranceTO.getLastUserModifier());
        insurance.setCreationDate(insuranceTO.getCreationDate());
        insurance.setLastModification(insuranceTO.getLastModifier());
        insurance.setActive(true);
        insurance.setNotificationTitle(insuranceTO.getNotificationTitle());
        insurance.setLastUserModification(insuranceTO.getLastUserModifier());
        insurance.setCreationDate(insuranceTO.getCreationDate());
        insurance.setLastModification(insuranceTO.getLastModifier());

        return this.insuranseDAO.save(insurance);
    }

    @Override
    public Map<String,Object> verifyHourPublication(Long id) {

        Map<String,Object> response = new HashMap<>();

        InsuranceDO insuranceDO = this.insuranseDAO.findById(id).get();
        LocalDate startDate = insuranceDO.getStartDate().toLocalDate();
        LocalTime publicationTime = insuranceDO.getPublicationTime().toLocalTime();
        LocalDateTime timePublication = LocalDateTime.of(startDate, publicationTime);

        response.put(Constants.ACCESS_INSURANCE,timePublication.compareTo(LocalDateTime.now()) < 0 ? Boolean.TRUE : Boolean.FALSE);
        response.put(Constants.ID_TYPE_INSURANCE,insuranceDO.getInsurangeType().getIdInsurangeType());

        return response;
    }
}