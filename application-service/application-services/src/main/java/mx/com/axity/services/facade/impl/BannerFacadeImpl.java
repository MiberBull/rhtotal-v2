package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.*;
import mx.com.axity.commons.to.totree.BenefitsBannersTreeTO;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.BannerDO;
import mx.com.axity.model.DiscountDO;
import mx.com.axity.model.NotificationRepositoryDO;
import mx.com.axity.services.facade.IBannerFacade;
import mx.com.axity.services.facade.INotificationAssignmentFacade;
import mx.com.axity.services.service.IBannerService;
import mx.com.axity.services.service.INotificationRepositoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static mx.com.axity.commons.util.Constants.TYPE_BANNERS;

@Component
public class BannerFacadeImpl implements IBannerFacade {

    @Autowired
    IBannerService bannerService;
    
    @Autowired
    INotificationAssignmentFacade notificationAssignmentFacade;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    INotificationRepositoryService iNotificationRepositoryService;

    @Override
    public BannerImageTO getBanner(int idBanner) {
        try {
            Optional.of(idBanner).map(t -> t > 0).orElseThrow();
            return this.bannerService.getBanner(idBanner);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public CountRowTO getNumberRow(String title, String autor, String startDate, String enddate) {
        return this.bannerService.getNumberRow(title,autor,startDate,enddate);
    }

    @Override
    @Transactional
    public Boolean saveOrUpdateBanner(BenefitsBannersTreeTO notification) {

        LocalDate starDate = notification.getNotificationTO().getStartDate().toLocalDate();
        LocalDate endDate = notification.getNotificationTO().getEndDate().toLocalDate();
        var oDateNow=LocalDate.now();
        if(starDate.isBefore(oDateNow))
        {
            throw new IllegalArgumentException("La fecha inicio, no puede ser menor a la fecha actual");
        }
        if(starDate.isEqual(oDateNow))
        {
            var hNow = LocalTime.now();
            var hR=notification.getNotificationTO().getTimePublication();
            if (!hR.isAfter(hNow))
            {
                throw new IllegalArgumentException("Validar hora de publicación, hora actual  "+ DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now()));
            }
        }
        if(endDate.isEqual(oDateNow))
        {
            var hNow = LocalTime.now();
            var hR=notification.getNotificationTO().getNotificationTime();
            if (!hR.isAfter(hNow))
            {
                throw new IllegalArgumentException("Validar hora de notificación, hora actual "+ DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now()));
            }
        }



        try {
            Optional.ofNullable(notification.getNotificationTO()).orElseThrow();
            var bannerDO = this.bannerService.saveOrUpdateBanner(notification.getNotificationTO());
            var imageList = notification.getImages().stream().map(t-> {
                t.setIdBanner(this.modelMapper.map(bannerDO,BannerTO.class));
                return t;
            }).collect(Collectors.toList());
            this.bannerService.saveOrUpdateImageBanner(imageList);
            var benefitsNotificationsTO = notification.getBenefitsNotificationsTO();
            benefitsNotificationsTO.setIdNotificacion(bannerDO.getIdBanner());
            benefitsNotificationsTO.setLastUserModifier(bannerDO.getLastUserModifier());
            benefitsNotificationsTO.setLastModification(bannerDO.getLastModification());
            benefitsNotificationsTO.setCreationUser(bannerDO.getCreationUser());
            benefitsNotificationsTO.setCreationDate(bannerDO.getCreationDate());
            benefitsNotificationsTO.setActive(bannerDO.getActive());
            benefitsNotificationsTO.setTypeNotification(TYPE_BANNERS);

            sendNotoficationRepository(bannerDO);
          return this.notificationAssignmentFacade.saveAssignmentBenefitsNotifications(benefitsNotificationsTO);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<BannerTO> getPagedBanner(int banner, String title, String autor, String startdate, String enddate) {
        try {
            Optional.of(banner).map(t -> t > 0).orElseThrow();
            return this.bannerService.getPagedBanner(banner,title,autor,startdate,enddate);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<String> getImagesBannerForMobile(int idUser) {
        try {
            return this.bannerService.getImagesBannerForMobile(idUser);
        } catch (Exception e) {
            throw  new BusinessException(e.getMessage(),e);
        }
    }

    private void sendNotoficationRepository(BannerDO bannerDo){
       /* var notfRepo = this.iNotificationRepositoryService.findByIds(bannerDo.getIdBanner().longValue(),Constants.TYPE_BANNERS);
        if(notfRepo == null){
            notfRepo= new NotificationRepositoryDO();
        }*/
        this.iNotificationRepositoryService.deleteNotificaRepository(Constants.TYPE_BANNERS,bannerDo.getIdBanner().longValue());

        var notfRepo = new NotificationRepositoryDO();
        notfRepo.setIdElement(bannerDo.getIdBanner());
        notfRepo.setDescription(bannerDo.getNotificationDetail());
        notfRepo.setDescriptionSmall(bannerDo.getNotificationDetail());
        notfRepo.setStatus(bannerDo.getStatus());
        notfRepo.setDateNotification(LocalDateTime.of(LocalDate.from(bannerDo.getStartDate()), bannerDo.getNotificationTime()));
        notfRepo.setSubcategory("");
        notfRepo.setTitle(bannerDo.getTitle());
        notfRepo.setType(Constants.TYPE_BANNERS);
        notfRepo.setCreationDate(bannerDo.getCreationDate());
        notfRepo.setCreationUser(bannerDo.getCreationUser());
        notfRepo.setLastModification(bannerDo.getLastModification());
        notfRepo.setLastUserModifier(bannerDo.getLastUserModifier());
        notfRepo.setFgActive(bannerDo.getActive());

        this.iNotificationRepositoryService.registerNotificationBanner(notfRepo);
    }

}
