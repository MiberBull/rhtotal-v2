package mx.com.axity.services.service;

import mx.com.axity.commons.to.BannerImageTO;
import mx.com.axity.commons.to.BannerTO;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.commons.to.ImageBannerTO;
import mx.com.axity.model.BannerDO;


import java.util.List;

public interface IBannerService  {

    CountRowTO getNumberRow(String title,String autor,String startDate,String enddate);

    BannerImageTO getBanner(int idBanner);

    BannerDO saveOrUpdateBanner(BannerTO banner);

    void saveOrUpdateImageBanner(List<ImageBannerTO> imageTO);

    List<BannerTO> getPagedBanner(int page,String title,String autor,String startDate,String enddate);

    List<String> getImagesBannerForMobile(int idUser);

}
