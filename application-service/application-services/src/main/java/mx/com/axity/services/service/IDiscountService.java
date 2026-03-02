package mx.com.axity.services.service;

import mx.com.axity.commons.to.*;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.commons.to.totree.DiscountImageTO;
import mx.com.axity.commons.to.totree.DiscountTableTO;
import mx.com.axity.model.DiscountDO;
import mx.com.axity.model.ImageDO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IDiscountService {

    List<CategoryTO> getCategory();

    List<CategoryTO> getCategoryIDUser(Long idUser,String typeDiscunt);

    List<SubCategoryTO> getSubcategory( Long idCategory);

    List<SubCategoryTO> getSubcategoryIdUser(Long idCategory,Long idUser,String typeDiscount);

    DiscountImageTO getDiscount(int idDiscount);

    DiscountDO saveOrUpdateDiscount(DiscountTO discount);

    List<DiscountTableTO> getPagedDiscount(int page, String supplier, String autor, String startDate, String endDate);

    void saveCategory(CategoryTO category);

    void saveSubcategory(SubCategoryTO subcategory);

    List<ImageDiscountTO> getImagesSecundary(Long idDiscount,String typeImage);

    void saveOrUpdateImageDiscount(List<ImageDiscountTO> images);

    CountRowTO getNumberRow(String supplier, String autor, String startDate, String endDate);

    List<Long> getIdDiscountByParameters(String typeDiscount, LocalDate todayDate, Long idCategory, Long idSubCategory, List<Long> idDiscount);

    List<Long> getNotificationAssignmentByIdUserAndTypeNotification(Long idUser,String typeNotification);

    List<ImageDiscountTO> getImagePageByIdDiscount(int page,List<Long> idDiscount,  String typeImage);

    Boolean verifyHourPublication(Long id);

    Long getLevelDiscount(Long id);
}
