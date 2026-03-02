package mx.com.axity.services.facade;

import mx.com.axity.commons.to.BannerImageTO;
import mx.com.axity.commons.to.BannerTO;
import mx.com.axity.commons.to.ImageBannerTO;
import mx.com.axity.commons.to.totree.BenefitsBannersTreeTO;
import mx.com.axity.commons.to.totree.CountRowTO;

import java.util.List;

public interface IBannerFacade {

    BannerImageTO getBanner(int idBanner);

    CountRowTO getNumberRow(String title,String autor,String startDate,String enddate);

    Boolean saveOrUpdateBanner(BenefitsBannersTreeTO notification);

    List<BannerTO> getPagedBanner(int banner, String title, String autor, String startdate, String enddate);

    List<String> getImagesBannerForMobile(int idUser);

}
