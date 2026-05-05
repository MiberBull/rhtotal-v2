package mx.com.axity.web.rest;

import mx.com.axity.commons.to.*;
import mx.com.axity.commons.to.totree.BenefitsDiscountTreeTO;
import mx.com.axity.commons.to.totree.CountRowTO;
import mx.com.axity.commons.to.totree.DiscountImageTO;
import mx.com.axity.commons.to.totree.DiscountTableTO;
import mx.com.axity.services.facade.IDiscountFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(originPatterns = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("discount")
public class DiscountController {

    final static Logger LOG = LogManager.getLogger(DiscountController.class);

    @Autowired
    IDiscountFacade discountFacade;

    @RequestMapping(value = "/saveOrUpdateDiscount", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Boolean> saveOrUpdateDiscount(@RequestBody BenefitsDiscountTreeTO discount) {
        LOG.info("init saveOrUpdateDiscount " + discount.toString());
        LOG.info("fecha modificacion " + discount.getDiscount().getLastUserModifier());

        var discountUpdateRest = this.discountFacade.saveOrUpdateDiscount(discount);
        LOG.info("saveOrUpdateDiscount finalizado correctamente");
        return new ResponseEntity<>(discountUpdateRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/getDiscount", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<DiscountImageTO> getDiscount(@RequestParam(value = "id") int id) {
        LOG.info("init getDiscount " + id);
        var discountRest = this.discountFacade.getDiscount(id);
        LOG.info("getDiscount finalizado correctamente");
        return new ResponseEntity<>(discountRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/getPagedDiscount", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<DiscountTableTO>> getPagedDiscount(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "supplier", defaultValue = "", required = false) String supplier,
                                                                  @RequestParam(value = "autor", defaultValue = "", required = false) String autor,
                                                                  @RequestParam(value = "startdate", defaultValue = "", required = false) String startdate,
                                                                  @RequestParam(value = "enddate", defaultValue = "", required = false) String enddate) {
        LOG.info("init getPagedDiscount");
        var pageDiscounts = this.discountFacade.getPagedDiscount(page, supplier.toUpperCase(), autor.toUpperCase(), startdate, enddate);
        LOG.info("getPagedDiscount finalizado correctamente");
        return new ResponseEntity<>(pageDiscounts, HttpStatus.OK);
    }


    @RequestMapping(value = "/getNumberRow", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CountRowTO> getNumberRow(@RequestParam(value = "supplier", defaultValue = "", required = false) String supplier,
                                                   @RequestParam(value = "autor", defaultValue = "", required = false) String autor,
                                                   @RequestParam(value = "startdate", defaultValue = "", required = false) String startdate,
                                                   @RequestParam(value = "enddate", defaultValue = "", required = false) String enddate) {
        LOG.info("init getNumberRow");
        var RoleCountRowTO = this.discountFacade.getNumberRow(supplier.toUpperCase(), autor.toUpperCase(), startdate, enddate);
        LOG.info("getNumberRow finalizado correctamente");
        return new ResponseEntity<>(RoleCountRowTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/getCategory", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<CategoryTO>> getCategory() {
        LOG.info("init getCategory ");
        var categoryRest = this.discountFacade.getCategory();
        LOG.info("getCategory finalizado correctamente");
        return new ResponseEntity<>(categoryRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/getCategoryIdUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<CategoryTO>> getCategoryIdUser(@RequestParam("idUser")Long idUser,@RequestParam("typeDiscount") String typeDiscount) {
        LOG.info("init getCategory por id user");
        var categoryRest = this.discountFacade.getCategoryIDUser(idUser,typeDiscount);
        LOG.info("getCategory por id user finalizado correctamente");
        return new ResponseEntity<>(categoryRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/getSubCategory", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<SubCategoryTO>> getSubCategory(@RequestParam("idCategory") Long idCategory) {
        LOG.info("init getSubCategory ");
        var subCategoryRest = this.discountFacade.getSubcategory(idCategory);
        LOG.info("getSubCategory finalizado correctamente");
        return new ResponseEntity<>(subCategoryRest, HttpStatus.OK);
    }

    @RequestMapping(value = "/getSubCategoryIdUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<SubCategoryTO>> getSubCategoryIdUser(@RequestParam("idCategory") Long idCategory,@RequestParam("idUser")Long idUser,@RequestParam("typeDiscount") String typeDiscount) {
        LOG.info("init getSubCategory ");
        var subCategoryRest = this.discountFacade.getSubcategoryIdUser(idCategory,idUser,typeDiscount);
        LOG.info("getSubCategory finalizado correctamente");
        return new ResponseEntity<>(subCategoryRest, HttpStatus.OK);
    }



    @RequestMapping(value = "/saveCategory", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity saveCategory(@RequestBody CategoryTO discount) {
        LOG.info("init saveCategory " + discount.toString());
            this.discountFacade.saveCategory(discount);
        LOG.info("saveCategory finalizado correctamente");
    return new ResponseEntity<>(HttpStatus.OK);

    }


    @RequestMapping(value = "/saveSubCategory", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity saveSubCategory(@RequestBody SubCategoryTO discount) {
        LOG.info("init saveOrUpdateCategory " + discount.toString());
         this.discountFacade.saveSubcategory(discount);
        LOG.info("saveSubCategory finalizado correctamente");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getImageDiscountByUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ImageDiscountTO>> getImageDiscountByUser(@RequestParam(value = "page") int page,@RequestParam(value = "idUser") Long idUser,@RequestParam(value = "idCategory",required = false) Long idCategory,@RequestParam(value = "idSubcatecory",required = false) Long idSubcatecory,@RequestParam(value = "typeNotification") String typeNotification,@RequestParam(value = "typeImage") String typeImage,@RequestParam(value = "typeDiscount") String typeDiscount){
        return  new ResponseEntity<>(this.discountFacade.getImageDiscountByUser(page,idUser,idCategory,idSubcatecory,typeNotification,typeImage,typeDiscount),HttpStatus.OK);
    }

    @RequestMapping(value = "/getImagesSecundary", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ImageDiscountTO>> getImagesSecundary(@RequestParam(value = "idDiscount") Long idDiscount,@RequestParam(value = "typeImage") String typeImage){
        return  new ResponseEntity<>(this.discountFacade.getImagesSecundary(idDiscount,typeImage),HttpStatus.OK);
    }



    @RequestMapping(value = "/verifyHourPublication", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Boolean> verifyHourPublication(@RequestParam(value = "id") Long id) {
        LOG.info("init verifyHourPublication " + id);
        var discountRest = this.discountFacade.verifyHourPublication(id);
        LOG.info("verifyHourPublication finalizado correctamente");
        return new ResponseEntity<>(discountRest, HttpStatus.OK);
    }


    @RequestMapping(value = "/getLevelDiscount", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Map<String,Long>> getLevelDiscount(@RequestParam(value = "id") Long id) {
        LOG.info("init getLevelDiscount " + id);
        var discountRest = this.discountFacade.getLevelDiscount(id);
        LOG.info("getLevelDiscount finalizado correctamente");
        return new ResponseEntity<>(discountRest, HttpStatus.OK);
    }


}
