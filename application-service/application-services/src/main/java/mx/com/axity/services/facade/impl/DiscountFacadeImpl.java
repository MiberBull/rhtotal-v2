package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.*;
import mx.com.axity.commons.to.totree.BenefitsDiscountTreeTO;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.commons.to.totree.DiscountImageTO;
import mx.com.axity.commons.to.totree.DiscountTableTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.DiscountDO;
import mx.com.axity.model.NotificationRepositoryDO;
import mx.com.axity.services.facade.IDiscountFacade;
import mx.com.axity.services.facade.INotificationAssignmentFacade;
import mx.com.axity.services.service.IDiscountService;
import mx.com.axity.services.service.INotificationRepositoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Executable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static mx.com.axity.commons.util.Constants.TYPE_DISCOUNT;

@Component
public class DiscountFacadeImpl implements IDiscountFacade {
    @Autowired
    IDiscountService discountService;

    @Autowired
    INotificationAssignmentFacade assignmentFacade;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    INotificationRepositoryService notificationRepositoryService;

    @Override
    public List<CategoryTO> getCategory() {
        try {
            return this.discountService.getCategory();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }

    }

    @Override
    public List<CategoryTO> getCategoryIDUser(Long idUser,String typeDiscunt) {
        try {
            return this.discountService.getCategoryIDUser(idUser,typeDiscunt);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }

    }

    @Override
    public List<SubCategoryTO> getSubcategory(Long idCategory) {
        try {
            return this.discountService.getSubcategory(idCategory);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<SubCategoryTO> getSubcategoryIdUser(Long idCategory, Long idUser, String typeUser) {
        try {
            return this.discountService.getSubcategoryIdUser(idCategory,idUser,typeUser);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }


    @Override
    public DiscountImageTO getDiscount(int idDiscount) {
        try {
            Optional.of(idDiscount).map(t -> t > 0).orElseThrow();
            return this.discountService.getDiscount(idDiscount);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Boolean saveOrUpdateDiscount(BenefitsDiscountTreeTO discount) {
        try {
            Optional.ofNullable(discount).orElseThrow();
            LocalDate starDate = discount.getDiscount().getStartDate().toLocalDate();
            LocalDate endDate = discount.getDiscount().getEndDate().toLocalDate();
            var oDateNow=LocalDate.now();
            if(starDate.isBefore(oDateNow))
            {
               throw new IllegalArgumentException("La fecha inicio, no puede ser menor a la fecha actual");
            }
            if(starDate.isEqual(oDateNow))
            {
               var hNow = LocalTime.now();
               var hR=discount.getDiscount().getPublicationTime();
               if (!hR.isAfter(hNow))
               {
                   throw new IllegalArgumentException("Validar hora de publicación, hora actual  "+ DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now()));
               }
            }
            if(endDate.isEqual(oDateNow))
            {
                var hNow = LocalTime.now();
                var hR=discount.getDiscount().getNotificationTime();
                if (!hR.isAfter(hNow))
                {
                    throw new IllegalArgumentException("Validar hora de notificación, hora actual "+ DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now()));
                }
            }
                if(null == discount.getDiscount().getIdDiscount() || discount.getDiscount().getIdDiscount()<1)
                {
                   discount.getDiscount().setCreationDate(LocalDateTime.now());
                }
            discount.getDiscount().setLastModification(LocalDateTime.now());
            var discountDO = this.discountService.saveOrUpdateDiscount(discount.getDiscount());
            var images = discount.getImages().stream().map(t -> {
                t.setIdDiscount(this.modelMapper.map(discountDO, DiscountTO.class));
                return t;
            }).collect(Collectors.toList());
            this.discountService.saveOrUpdateImageDiscount(images);
            var benefitsNotificationsTO = discount.getBenefitsNotificationsTO();
            if (benefitsNotificationsTO == null) return true;
            benefitsNotificationsTO.setIdNotificacion(discountDO.getIdDiscount());
            benefitsNotificationsTO.setLastUserModifier(discountDO.getLastUserModifier());
            benefitsNotificationsTO.setLastModification(discountDO.getLastModification());

            benefitsNotificationsTO.setCreationUser(discountDO.getCreationUser());
            benefitsNotificationsTO.setCreationDate(discountDO.getCreationDate());

            benefitsNotificationsTO.setActive(discountDO.getActive());
            benefitsNotificationsTO.setTypeNotification(TYPE_DISCOUNT);

            this.sendNotoficationRepository(discountDO);
            return assignmentFacade.saveAssignmentBenefitsNotifications(benefitsNotificationsTO);
        }

        catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);

        }
    }

    @Override
    public List<DiscountTableTO> getPagedDiscount(int page, String supplier, String autor, String startdate, String enddate) {
        try {
            Optional.of(page).map(t -> t > 0).orElseThrow();
            return this.discountService.getPagedDiscount(page, supplier, autor, startdate, enddate);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<ImageDiscountTO> getImagesSecundary(Long idDiscount, String typeImage) {
        try {
            return this.discountService.getImagesSecundary(idDiscount,typeImage);
        }catch (Exception e){
            throw new BusinessException("No se pudo obtener las imagenes secundarias",e);
        }
    }

    @Override
    public void saveCategory(CategoryTO category) {
        try {
            Optional.ofNullable(category).orElseThrow();
            this.discountService.saveCategory(category);
        } catch (Exception e) {

            throw new BusinessException("No se pudo agregar la categoría, validar si ya existe", e);
        }
    }

    @Override
    public void saveSubcategory(SubCategoryTO subcategory) {
        try {
            Optional.ofNullable(subcategory).orElseThrow();
            this.discountService.saveSubcategory(subcategory);
        } catch (Exception e) {

            throw new BusinessException("No se pudo agregar la subcategoría, validar si ya existe", e);
        }
    }

    @Override
    public CountRowTO getNumberRow(String supplier, String autor, String startDate, String endDate) {
        try {
            return this.discountService.getNumberRow(supplier, autor, startDate, endDate);
        } catch (Exception e) {
            throw new BusinessException("error al obtener el numero de columnas", e);
        }
    }

    @Override
    public List<ImageDiscountTO> getImageDiscountByUser(int page, Long idUser, Long idCategory, Long idSubcatecory, String typeNotification, String typeImage, String typeDiscount) {
        try {
            var notificationAssignmentByIdUserAndTypeNotification = this.discountService.getNotificationAssignmentByIdUserAndTypeNotification(idUser, typeNotification);
            var idDiscountByParameters = this.discountService.getIdDiscountByParameters(typeDiscount, LocalDate.now(), idCategory, idSubcatecory, notificationAssignmentByIdUserAndTypeNotification);
            var imagePageByIdDiscount = this.discountService.getImagePageByIdDiscount(page, idDiscountByParameters, typeImage);
            Optional.ofNullable(imagePageByIdDiscount).orElseThrow();
            return imagePageByIdDiscount;

        } catch (Exception e) {
            throw new BusinessException("error al obtener el paginado de imagenes", e);
        }
    }

    @Override
    public Boolean verifyHourPublication(Long id) {
        try {
            return this.discountService.verifyHourPublication(id);
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public Map<String,Long> getLevelDiscount(Long id) {
        try {
            Map<String,Long> map = new HashMap<>();
            map.put("level",this.discountService.getLevelDiscount(id));
            return map;
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }
    }


    private void sendNotoficationRepository(DiscountDO discountDO){
       // var notfRepo = this.notificationRepositoryService.findByIds(discountDO.getIdDiscount().longValue(),Constants.TYPE_DISCOUNT);
       /* if(notfRepo == null ){
            notfRepo= new NotificationRepositoryDO();
        }*/
       this.notificationRepositoryService.deleteNotificaRepository(Constants.TYPE_DISCOUNT,discountDO.getIdDiscount().longValue());
            var notfRepo= new NotificationRepositoryDO();
            notfRepo.setIdElement(discountDO.getIdDiscount());
            notfRepo.setDescription(discountDO.getDescription());
            notfRepo.setDescriptionSmall(discountDO.getNotificationDetail());
            notfRepo.setStatus(discountDO.getStatus());
            notfRepo.setDateNotification(LocalDateTime.of(LocalDate.from(discountDO.getStartDate()), discountDO.getNotificationTime()));
            notfRepo.setSubcategory(discountDO.getTypeDiscount());
            notfRepo.setTitle(discountDO.getTitle());
            notfRepo.setType(Constants.TYPE_DISCOUNT);
            notfRepo.setCreationDate(discountDO.getCreationDate());
            notfRepo.setCreationUser(discountDO.getCreationUser());
            notfRepo.setLastModification(discountDO.getLastModification());
            notfRepo.setLastUserModifier(discountDO.getLastUserModifier());
            notfRepo.setFgActive(discountDO.getActive());

        this.notificationRepositoryService.registerNotificationDiscount(notfRepo);
    }

}

