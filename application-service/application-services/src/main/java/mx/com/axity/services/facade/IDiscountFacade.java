package mx.com.axity.services.facade;

import mx.com.axity.commons.to.*;
import mx.com.axity.commons.to.totree.BenefitsDiscountTreeTO;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.commons.to.totree.DiscountImageTO;
import mx.com.axity.commons.to.totree.DiscountTableTO;
import mx.com.axity.services.facade.impl.DiscountFacadeImpl;

import java.util.List;
import java.util.Map;

public interface IDiscountFacade {

    List<CategoryTO> getCategory();

    List<CategoryTO> getCategoryIDUser(Long idUser,String typeDiscunt);

    List<SubCategoryTO> getSubcategory( Long idCategory);

    List<SubCategoryTO> getSubcategoryIdUser( Long idCategory,Long idUser,String typeUser);

    DiscountImageTO getDiscount(int idDiscount);

    Boolean saveOrUpdateDiscount (BenefitsDiscountTreeTO discount);

    List<DiscountTableTO> getPagedDiscount(int page, String supplier, String autor, String startdate, String enddate);

    List<ImageDiscountTO> getImagesSecundary(Long idDiscount,String typeImage);

    void saveCategory(CategoryTO category);

    void saveSubcategory(SubCategoryTO subcategory);

    CountRowTO getNumberRow(String supplier, String autor, String startDate, String endDate);

    List<ImageDiscountTO> getImageDiscountByUser(int page ,Long idUser,Long idCategory,Long idSubcatecory,String typeNotification,String typeImage,String typeDiscount);

    Boolean verifyHourPublication(Long id);

    Map<String,Long> getLevelDiscount(Long id);
}
