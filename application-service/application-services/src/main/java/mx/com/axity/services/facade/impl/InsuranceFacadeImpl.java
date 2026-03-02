package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.*;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.InsuranceDO;
import mx.com.axity.model.NotificationRepositoryDO;
import mx.com.axity.services.facade.IInsuranceFacade;
import mx.com.axity.services.facade.INotificationAssignmentFacade;
import mx.com.axity.services.service.IInsuranceService;
import mx.com.axity.services.service.INotificationRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static mx.com.axity.commons.util.Constants.FORMAT_TIME;
import static mx.com.axity.commons.util.Constants.TYPE_INSURANCE;


@Component
public class InsuranceFacadeImpl implements IInsuranceFacade {

    @Autowired
    IInsuranceService insuranceServise;

    @Autowired
    INotificationRepositoryService notificationRepositoryService;

    @Autowired
    INotificationAssignmentFacade notificationFacade;


    @Override
    public InsuranceTableTO getOneInsurance(int insurance) {
        try{
            Optional.of(insurance).map(t -> t > 0).orElseThrow();
            return this.insuranceServise.getOneInsurance(insurance);
        } catch(Exception e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public EventualyTO getOneEventualy(int eventualy) {
        try{
            Optional.of(eventualy).map(t -> t > 0).orElseThrow();
            return this.insuranceServise.getOneEventualy(eventualy);
        }catch(Exception e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public PlanCoverageTO getOnePlanCoverage(int coverage) {
        try{
            Optional.of(coverage).map(t -> t > 0).orElseThrow();
            return this.insuranceServise.getOnePlanCoverage(coverage);
        }catch(Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<EventualyTO> getAllEventualy(int page, int idInsurance) {
        try{
            Optional.of(idInsurance).map(t -> t > 0).orElseThrow();
            return this.insuranceServise.getAllEventualy(page, idInsurance);
        }catch(Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<PlanCoverageTO> getAllPlanCoverage(int page, int idInsurance) {
        try{
            Optional.of(idInsurance).map(t -> t > 0).orElseThrow();
            return this.insuranceServise.getAllPlanCoverage(page, idInsurance);
        }catch(Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public Boolean saveUpdateEventualy(EventualyTO eventualyTO) {
        try{
            return this.insuranceServise.saveUpdateEventualy(eventualyTO);
        }catch(Exception e){
            throw new BusinessException(e.getMessage(), e);
        }

    }

    @Override
    public Boolean saveUpdatePlanCoverage(PlanCoverageTO planCoverageTO) {
        try{
            return this.insuranceServise.saveUpdatePlanCoverage(planCoverageTO);
        }catch(Exception e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<InsuranceTableTO> showAllInsurance(int page, String nameTab, String insuranceCarrier, String startDate, String endDate, String author) {
        try{
            return this.insuranceServise.showAllInsurance(page,nameTab,insuranceCarrier,startDate,endDate,author);
        }catch(Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public InsuranceTableTO saveUpdateInsuranceNotification(BenefitsInsuranceNotificationsTreeTO benefitsInsuranceTreeTO) {
        try{
            Optional.ofNullable(benefitsInsuranceTreeTO).orElseThrow();



            LocalDate starDate = benefitsInsuranceTreeTO.getInsuranceTO().getStartDate().toLocalDate();
            LocalDate endDate = benefitsInsuranceTreeTO.getInsuranceTO().getStartDate().toLocalDate();

            var oDateNow = LocalDate.now();

            if(starDate.isBefore(oDateNow))
            {
                throw new IllegalArgumentException("La fecha inicio, no puede ser menor a la fecha actual");
            }

            if(starDate.isEqual(oDateNow))
            {
                var hNow = LocalTime.now();
                var hR=benefitsInsuranceTreeTO.getInsuranceTO().getTimePublication();
                if (!hR.isAfter(hNow))
                {
                   throw new IllegalArgumentException("Validar hora de publicación, hora actual  "+ DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now()));
                }
            }

            if(endDate.isEqual(oDateNow))
            {
                var hNow = LocalTime.now();
                var hR=benefitsInsuranceTreeTO.getInsuranceTO().getNotificationTime();
                if (!hR.isAfter(hNow))
                {
                   throw new IllegalArgumentException("Validar hora de notificación, hora actual "+ DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now()));
                }
            }

            var insuranceDO = this.insuranceServise.saveOrUpdateNotification(benefitsInsuranceTreeTO.getInsuranceTO());
            var insuranceTO = this.coverterInsuranceTO(insuranceDO);
            var benefitsNotificationsTO = benefitsInsuranceTreeTO.getBenefitsNotificationsTO();
            benefitsNotificationsTO.setIdNotificacion(insuranceDO.getIdInsurance());
            benefitsNotificationsTO.setLastUserModifier(insuranceDO.getLastUserModification());
            benefitsNotificationsTO.setLastModification(insuranceDO.getLastModification());
            benefitsNotificationsTO.setCreationUser(insuranceDO.getCreationUser());
            benefitsNotificationsTO.setCreationDate(insuranceDO.getCreationDate());
            benefitsNotificationsTO.setActive(insuranceDO.getActive());
            benefitsNotificationsTO.setTypeNotification(TYPE_INSURANCE);

            sendNotoficationRepository(insuranceDO);
            notificationFacade.saveAssignmentBenefitsNotifications(benefitsNotificationsTO);
            return insuranceTO;
        }catch(Exception e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public CountRowTO getNumberRow(String insuranceCarrier, String startDate, String endDate, String author) {
        try{
            return this.insuranceServise.getNumberRow(insuranceCarrier, startDate, endDate, author);
        }catch(Exception e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public CountRowTO getNumberRowEventualy(int idInsurance) {
        try{
            return this.insuranceServise.getNumberRowEventualy(idInsurance);
        }catch(Exception e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public CountRowTO getNumberRowCoverage(int idInsurance) {
        try{
            return this.insuranceServise.getNumberRowCoverage(idInsurance);
        }catch(Exception e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String,String>> getInsuranseUser(Long idUser) {

        try {
            List<Long> insurangeUserAssigment = this.insuranceServise.getInsurangeUserAssigment(idUser);
            Optional.ofNullable(insurangeUserAssigment.size() > 0 ? insurangeUserAssigment :null).orElseThrow();
            List<Map<String,String>> insuranseUser = this.insuranceServise.getInsuranseUser(insurangeUserAssigment);
            Optional.ofNullable(insuranseUser.size() > 0 ?insuranseUser : null).orElseThrow();
            return  insuranseUser;

        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public Map<String, String> geInsurangeByInsurance(Long idInsurance) {
        try{
            Map<String, String> insuranceMap = this.insuranceServise.geInsurangeByInsurance(idInsurance);
            Optional.ofNullable(insuranceMap).orElseThrow();
            return insuranceMap;
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public List<Map<String, String>> getCoverageInsurance(Long idInsurance) {
       return this.insuranceServise.getCoverageInsurance(idInsurance);
    }

    public void sendNotoficationRepository(InsuranceDO insuranceDO){
       /* var notfRepo = this.notificationRepositoryService.findByIds(insuranceDO.getIdInsurance(), Constants.TYPE_INSURANCE);
        if(notfRepo == null){
            notfRepo= new NotificationRepositoryDO();
        }*/

        this.notificationRepositoryService.deleteNotificaRepository(Constants.TYPE_INSURANCE,insuranceDO.getIdInsurance());
        var notfRepo= new NotificationRepositoryDO();
        notfRepo.setIdElement(insuranceDO.getIdInsurance());
        notfRepo.setDescription(insuranceDO.getNotificationDetail());
        notfRepo.setDescriptionSmall(insuranceDO.getNotificationDetail());
        notfRepo.setStatus(insuranceDO.getStatus());
        notfRepo.setDateNotification(LocalDateTime.of(LocalDate.from(insuranceDO.getStartDate()), insuranceDO.getNotificationTime()));
        notfRepo.setSubcategory("");
        notfRepo.setTitle(insuranceDO.getNotificationTitle());
        notfRepo.setType(Constants.TYPE_INSURANCE);
        notfRepo.setCreationDate(insuranceDO.getCreationDate());
        notfRepo.setCreationUser(insuranceDO.getCreationUser());
        notfRepo.setLastModification(insuranceDO.getLastModification());
        notfRepo.setLastUserModifier(insuranceDO.getLastUserModification());
        notfRepo.setFgActive(insuranceDO.getActive());

        this.notificationRepositoryService.registerNotificationBanner(notfRepo);
    }


    public InsuranceTableTO coverterInsuranceTO(InsuranceDO insuranceDO){

        LocalDateTime lastModification = insuranceDO.getPublicationTime();
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
    public Map<String,Object> verifyHourPublication(Long id) {
        try {
            return this.insuranceServise.verifyHourPublication(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }
    }
}
