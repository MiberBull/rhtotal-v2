package mx.com.axity.web.rest;

import mx.com.axity.commons.to.BannerTO;
import mx.com.axity.web.BaseTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class BannerControllerTest extends BaseTest {
    @Test
    public void saveOrUpdateBannerTest(){
        BannerTO bannerTO = new BannerTO();
        bannerTO.setActive(Boolean.TRUE);
        bannerTO.setCreationDate(LocalDateTime.now());
        bannerTO.setCreationUser("test");
        bannerTO.setEndDate(LocalDateTime.now());
       // bannerTO.setImage("base64");
        bannerTO.setInternalComments("test");
        bannerTO.setStartDate(LocalDateTime.now());
        bannerTO.setLastModification(LocalDateTime.now());
        bannerTO.setLastUserModifier("test");
        bannerTO.setStatus("jhjh");
        bannerTO.setTimePublication(LocalTime.now());
        bannerTO.setTimePublication(LocalTime.now());
        //var isSave = this.bannerFacadeTest.saveOrUpdateBanner(bannerTO);
        // Assert.assertNotNull(isSave);



    }

    @Test
    public void getBannerTest(){
        var banner = this.bannerFacadeTest.getBanner(1);
        Assert.assertNotNull(banner);
    }

    @Test
    public void getPagedBannerTest(){
        var  pagedBanner = this.bannerFacadeTest.getPagedBanner(0,"","","","");
        Assert.assertNotNull(pagedBanner);
    }
}
