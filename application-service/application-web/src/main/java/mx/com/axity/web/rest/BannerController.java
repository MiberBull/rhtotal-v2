package mx.com.axity.web.rest;

import mx.com.axity.commons.to.*;
import mx.com.axity.commons.to.totree.BenefitsBannersTreeTO;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.services.facade.IBannerFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin(originPatterns = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("banner")
public class BannerController {

     static final Logger LOG = LogManager.getLogger(BannerController.class);

    @Autowired
    IBannerFacade bannerFacade;

    @RequestMapping(value = "/saveOrUpdateBanner" , method = RequestMethod.POST , produces = "application/json")
    public ResponseEntity<Boolean> saveOrUpdateBanner(@RequestBody BenefitsBannersTreeTO benefitsNotificationsTO){
        LOG.info("init saveOrUpdateBanner " + benefitsNotificationsTO.getNotificationTO().toString() );
        var bannerUpdateRest = this.bannerFacade.saveOrUpdateBanner(benefitsNotificationsTO);
        LOG.info("saveOrUpdateBanner finalizado correctamente");
        return new ResponseEntity<>(bannerUpdateRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/getBanner" , method = RequestMethod.GET , produces = "application/json")
    public ResponseEntity<BannerImageTO> getBanner(@RequestParam(value = "id") int id){
        LOG.info("init getBanner " + id );
        var bannerRest = this.bannerFacade.getBanner(id);
        LOG.info("getBanner finalizado correctamente");
        return new ResponseEntity<>(bannerRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPagedBanner", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<BannerTO>> getPagedBanner(@RequestParam(value = "page",defaultValue = "0")int page,
                                                         @RequestParam(value = "title" , defaultValue = "")String title,
                                                         @RequestParam(value = "autor", defaultValue = "")String autor,
                                                         @RequestParam(value = "startdate",defaultValue = "")String startdate,
                                                         @RequestParam(value = "enddate" ,defaultValue = "")String enddate) {
        LOG.info("init getPagedBanner");
        var pageBanners = this.bannerFacade.getPagedBanner(page,title.toUpperCase(),autor.toUpperCase(),startdate,enddate);
        LOG.info("getPagedBanner finalizado correctamente");
        return new ResponseEntity<>(pageBanners, HttpStatus.OK);
    }

    @RequestMapping(value = "/getNumberRow", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CountRowTO> getNumberRow(@RequestParam(value = "title" , defaultValue = "")String title,
                                                   @RequestParam(value = "autor", defaultValue = "")String autor,
                                                   @RequestParam(value = "startdate",defaultValue = "")String startdate,
                                                   @RequestParam(value = "enddate" ,defaultValue = "")String enddate) {
        LOG.info("init getNumberRow");
        var RoleCountRowTO = this.bannerFacade.getNumberRow(title.toUpperCase(),autor.toUpperCase(),startdate,enddate);
        LOG.info("getNumberRow finalizado correctamente");
        return new ResponseEntity<>(RoleCountRowTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/showbanners", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<String>> showBannersMobile( @RequestParam( value = "user",required = true) int idUser ){
        LOG.info("init showBannersMobile");
        var banners = this.bannerFacade.getImagesBannerForMobile( idUser );
        LOG.info("showBannersMobile finalizado correctamente");
        return new ResponseEntity<>(banners,HttpStatus.OK);
    }

}
