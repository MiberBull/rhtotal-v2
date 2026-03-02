package mx.com.axity.services.service;

import mx.com.axity.commons.to.BannerTO;
import mx.com.axity.services.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class BannerServiceTest extends BaseTest {


    @Test
   public void saveOrUpdateBannerTest(){
        BannerTO bannerTO = new BannerTO();
        bannerTO.setActive(Boolean.TRUE);
        bannerTO.setCreationDate(LocalDateTime.now());
        bannerTO.setCreationUser("test");
        bannerTO.setEndDate(LocalDateTime.now());
      //bannerTO.setImage("base64");
        bannerTO.setInternalComments("test");
        bannerTO.setStartDate(LocalDateTime.now());
        bannerTO.setLastModification(LocalDateTime.now());
        bannerTO.setLastUserModifier("test");
        bannerTO.setStatus("jhjh");
        bannerTO.setTimePublication(LocalTime.now());
        bannerTO.setTimePublication(LocalTime.now());
        var isSave = this.bannerServiceTest.saveOrUpdateBanner(bannerTO);
        Assertions.assertNotNull(isSave);



    }

    @Test
    public void getBannerTest(){
        var banner = this.bannerServiceTest.getBanner(1);
        Assertions.assertNotNull(banner);
    }

    @Test
    public void getPagedBannerTest(){
        var  pagedBanner = this.bannerServiceTest.getPagedBanner(0,"","","","");
        Assertions.assertNotNull(pagedBanner);
    }
}
