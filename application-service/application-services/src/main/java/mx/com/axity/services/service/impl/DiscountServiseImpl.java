package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.*;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.commons.to.totree.DiscountImageTO;
import mx.com.axity.commons.to.totree.DiscountTableTO;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.CategoryDO;
import mx.com.axity.model.DiscountDO;
import mx.com.axity.model.ImageDiscountDO;
import mx.com.axity.model.SubCategoryDO;
import mx.com.axity.persistence.*;
import mx.com.axity.services.service.IDiscountService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static mx.com.axity.commons.util.Constants.*;

@Service
public class DiscountServiseImpl implements IDiscountService {

    @Autowired
    DiscountDAO discountDAO;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    SubCategoryDAO subCategoryDAO;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    NotificationAssignmentDAO assignmentDAO;

    @Autowired
    ImageDiscountDAO imageDiscountDAO;

    @Override
    public List<CategoryTO> getCategory() {
        return (List<CategoryTO>) this.modelMapper.map(this.categoryDAO.getAllOrderCategory(), new TypeToken<List<CategoryTO>>() {
        }.getType());
    }

    @Override
    public List<CategoryTO> getCategoryIDUser(Long idUser,String typeDiscunt) {
        return (List<CategoryTO>) this.modelMapper.map(this.categoryDAO.getOrderCategoryIdUser(idUser,typeDiscunt), new TypeToken<List<CategoryTO>>() {
        }.getType());
    }

    @Override
    public List<SubCategoryTO> getSubcategory( Long idCategory) {
        return (List<SubCategoryTO>) this.modelMapper.map(this.subCategoryDAO.finByAllSubCategoryByIdCategory(idCategory),new TypeToken<List<SubCategoryTO>>(){}.getType());
    }

    @Override
    public List<SubCategoryTO> getSubcategoryIdUser(Long idCategory, Long idUser, String typeDiscount) {
        return (List<SubCategoryTO>) this.modelMapper.map(this.subCategoryDAO.finByAllSubCategoryByIdCategoryAndUser(idCategory,idUser,typeDiscount),new TypeToken<List<SubCategoryTO>>(){}.getType());
    }


    @Override
    public DiscountImageTO getDiscount(int idDiscount) {
        DiscountImageTO discountImageTO = new DiscountImageTO();
        discountImageTO.setDiscount(this.modelMapper.map(this.discountDAO.findById((long) idDiscount).get(), DiscountTO.class));
        discountImageTO.setImageDiscountTO( this.modelMapper.map(this.imageDiscountDAO.findAllByIdDiscount((long) idDiscount),new TypeToken<List<ImageDiscountTO>>(){}.getType()));

        return discountImageTO;
    }

    @Override
    public DiscountDO saveOrUpdateDiscount(DiscountTO discount) {
        return this.discountDAO.save(this.modelMapper.map(discount,DiscountDO.class));
    }

    @Override
    public List<DiscountTableTO> getPagedDiscount(int page, String supplier, String autor, String startDate, String endDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

        var pageDiscount = this.discountDAO.findAllByOrderByLastModificationDesc(PageRequest.of(page, Constants.LIMIT_PAGE), CADENA_VACIA.equals(supplier) ? null : supplier, CADENA_VACIA.equals(autor) ? null : autor,
                CADENA_VACIA.equals(startDate) ? null : LocalDate.parse(startDate, inputFormatter).atStartOfDay(),
                CADENA_VACIA.equals(endDate) ? null : LocalDate.parse(endDate, inputFormatter).atStartOfDay().plus(1, ChronoUnit.DAYS));
        return pageDiscount.getContent().stream().map(x -> {
            var discountTableTO = new DiscountTableTO();
            discountTableTO.setCategory(x.getCategory().getCategory());
            discountTableTO.setSubCategory(x.getSubCategory().getSubcategory());
            discountTableTO.setSupplier(x.getSupplier());

            discountTableTO.setStatus(x.getStatus().equals(BANNERS_STATUS_ACTIVE) ? BANNERS_STATUS_ACTIVE_VIEW : x.getStatus().equals(BANNERS_STATUS_INACTIVO) ? BANNERS_STATUS_INACTIVO_VIEW : BANNERS_SIN_STATUS_VIEW);
            discountTableTO.setStartDate(FORMAT_TABLE.format(LocalDate.parse(x.getStartDate().format((DateTimeFormatter.ofPattern(FORMAT_DATE))))));
            discountTableTO.setEndDate(FORMAT_TABLE.format(LocalDate.parse(x.getEndDate().format((DateTimeFormatter.ofPattern(FORMAT_DATE))))));

            discountTableTO.setTitle(x.getTitle());
            discountTableTO.setViewCount(x.getViewCount());
            discountTableTO.setIdDiscount(x.getIdDiscount());
            discountTableTO.setState(x.getState());
            discountTableTO.setDescription(x.getDescription());
            discountTableTO.setLinkUrl(x.getLinkUrl());
            discountTableTO.setTermsConditions(x.getTermsConditions());
            discountTableTO.setDescriptionPreview(x.getDescriptionPreview());
            discountTableTO.setNotificationTime(x.getNotificationTime());
            discountTableTO.setNotificationDetail(x.getNotificationDetail());
            discountTableTO.setPublicationTime(x.getPublicationTime());
            discountTableTO.setTypeDiscount(x.getTypeDiscount());
            discountTableTO.setCost(x.getCost());
            discountTableTO.setLastUserModifier(x.getLastUserModifier());
            return discountTableTO;
        }).collect(Collectors.toList());

    }

    @Override
    public void saveCategory(CategoryTO category) {
        this.categoryDAO.save(modelMapper.map(category, CategoryDO.class));
    }

    @Override
    public void saveSubcategory(SubCategoryTO subcategory) {
        this.subCategoryDAO.save(modelMapper.map(subcategory, SubCategoryDO.class));
    }

    @Override
    public List<ImageDiscountTO> getImagesSecundary(Long idDiscount,String typeImage) {
        return (List<ImageDiscountTO>) this.modelMapper.map(this.imageDiscountDAO.getImagesSecundary(idDiscount,typeImage),new TypeToken<List<ImageDiscountTO>>(){}.getType());
    }

    @Override
    public void saveOrUpdateImageDiscount(List<ImageDiscountTO> images) {
        this.imageDiscountDAO.saveAll(this.modelMapper.map(images, new TypeToken<List<ImageDiscountDO>>() {
        }.getType()));
    }

    @Override
    public CountRowTO getNumberRow(String supplier, String autor, String startDate, String endDate) {
        return new CountRowTO(this.discountDAO.getNumberRow(CADENA_VACIA.equals(supplier) ? null : supplier, CADENA_VACIA.equals(autor) ? null : autor,
                CADENA_VACIA.equals(startDate) ? null : LocalDateTime.ofInstant(Instant.parse(startDate), ZoneId.of(ZoneOffset.UTC.getId())),
                CADENA_VACIA.equals(endDate) ? null : LocalDateTime.ofInstant(Instant.parse(endDate), ZoneId.of(ZoneOffset.UTC.getId()))));
    }

    @Override
    public List<Long> getIdDiscountByParameters(String typeDiscount, LocalDate todayDate, Long idCategory, Long idSubCategory, List<Long> idDiscount) {
        return this.discountDAO.getIdDiscountByParameters(typeDiscount,todayDate,idCategory,idSubCategory,idDiscount);
    }

    @Override
    public List<Long> getNotificationAssignmentByIdUserAndTypeNotification(Long idUser, String typeNotification) {
        return this.assignmentDAO.getNotificationAssignmentByIdUserAndTypeNotification(idUser,typeNotification);
    }

    @Override
    public List<ImageDiscountTO> getImagePageByIdDiscount(int page,List<Long> idDiscount, String typeImage) {
     var listImage =  (List<ImageDiscountTO>)  this.modelMapper.map(this.imageDiscountDAO.getImagePageByIdDiscount(PageRequest.of(page,2),idDiscount,typeImage,LocalDate.now(),LocalDateTime.now()).getContent(),new TypeToken<List<ImageDiscountTO>>(){}.getType());
     return listImage.size() > 0 ? listImage :null;
    }

    @Override
    public Boolean verifyHourPublication(Long id) {

        DiscountDO discountDO = this.discountDAO.findById(id).get();
        LocalDate startDate = discountDO.getStartDate().toLocalDate();
        LocalTime publicationTime = discountDO.getPublicationTime();
        LocalDateTime timePublication = LocalDateTime.of(startDate, publicationTime);
       if (timePublication.compareTo(LocalDateTime.now()) < 0){
            return Boolean.TRUE;
       }
        return Boolean.FALSE;
    }

    @Override
    public Long getLevelDiscount(Long id) {
        return  this.discountDAO.getLevetDiscount(id);
    }
}
