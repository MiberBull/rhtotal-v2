package mx.com.axity.web.rest;

import mx.com.axity.commons.to.*;
import mx.com.axity.services.facade.IEmployeeFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("user")
public class EmployeeRegistry {

    static final Logger LOG = LogManager.getLogger(EmployeeRegistry.class);

    @Autowired
    IEmployeeFacade employeeFacade;
    @Autowired
    ModelMapper modelMapper;




    @RequestMapping(value = "/saveOrUpdateEmployee" , method = RequestMethod.POST , produces = "application/json")
    @ResponseBody
    public ResponseEntity<Boolean>  saveOrUpdateEmployee(@RequestBody EmployeeTO employee){
        LOG.info("Init SAVE Employee ");
        var employeeUpdate = this.employeeFacade.saveOrUpdateEmployee(employee);

        return new ResponseEntity<>(employeeUpdate,HttpStatus.OK);
    }


    @RequestMapping(value = "/saveOrUpdateUser" , method = RequestMethod.POST , produces = "application/json")
    public ResponseEntity  saveOrUpdateUser(@RequestBody UserTO user){
        LOG.info("Init SAVE Employee ");
        this.employeeFacade.saveOrUpdateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/saveOrUpdateEmployeeComplementary" , method = RequestMethod.POST , produces = "application/json")
    @ResponseBody
    public ResponseEntity  SaveOrUpdateEmployeeComplentary(@RequestBody EmployeePersonalTO empComp){
        LOG.info("Init SAVE Employee ");
         this.employeeFacade.saveOrUpdateEmployeeComplementaryMovil(empComp);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/saveOrUpdateEmployeeComplementaryWeb" , method = RequestMethod.POST , produces = "application/json")
    @ResponseBody
    public ResponseEntity<EmployeeTO>  SaveOrUpdateEmployeeComplentaryWeb(@RequestBody EmployeeComplementaryTO empComp){
        LOG.info("Init SAVE Employee ");
        var employeeComplentaryUpdate = this.employeeFacade.saveOrUpdateEmployeeComplemtaryWeb(empComp);
        return new ResponseEntity<>(employeeComplentaryUpdate,HttpStatus.OK);
    }


    @RequestMapping(value = "/saveOrUpdateEmployeeAddress", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Boolean> saveOrUpdateEmployeeAddress(@RequestBody EmployeeAddressTO employee){
        LOG.info("Init SAVE Employee ");
        var resutl = this.employeeFacade.saveOrUpdateEmployeeAdress(employee);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }


    @RequestMapping(value = "/saveOrUpdateAsignationData", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Boolean> saveOrUpdateAsignationData(@RequestBody AsignationDataTO asignation){
        LOG.info("Init SAVE Employee ");
        var resutl = this.employeeFacade.saveOrUpdateAsignationData(asignation);
        return new ResponseEntity<>(resutl, HttpStatus.OK);

    }


    @RequestMapping(value = "/saveOrUpdateCompesation", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Boolean> saveOrUpdateCompesation(@RequestBody List<CompensationPackageTO> compesation){
        LOG.info("Init SAVE Employee ");
        var resutl = this.employeeFacade.saveOrUpdateCompesation(compesation);
        return new ResponseEntity<>(resutl, HttpStatus.OK);

    }


    @RequestMapping(value = "/saveOrUpdateContrating", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Boolean> saveOrUpdateEmployee(@RequestBody ContratingDataTO contrating){
        LOG.info("Init SAVE Employee ");
        var resutl = this.employeeFacade.saveOrUpdateContrating(contrating);

        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }

    @RequestMapping(value = "/saveOrUpdateHistory", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Boolean> saveOrUpdateHistory(@RequestBody HistoryEmployeeTO history){
        LOG.info("Init SAVE Employee ");
        var resutl = this.employeeFacade.saveOrUpdateHistory(history);

        return new ResponseEntity<>(resutl, HttpStatus.OK);

    }

    @RequestMapping(value = "/saveOrUpdateSocialNetwork", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Boolean> saveOrUpdateSocialNetwork(@RequestBody List<SocialNetworkTO> socialNetwork){
        LOG.info("Init SAVE Employee social network");
        var resutl = this.employeeFacade.saveOrUpdateSocialNetwork(socialNetwork);

        return new ResponseEntity<>(resutl, HttpStatus.OK);

    }

    @RequestMapping(value = "/getCity", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<CityTO>> getCity(@RequestParam("idState") Long idState){
        LOG.info("Init getCity");
        var resutl = this.employeeFacade.getCity(idState);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }

    @RequestMapping(value = "/getState", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<StateTO>> getState(){
        LOG.info("Init getState");
        var resutl = this.employeeFacade.getState();
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }

    @RequestMapping(value = "/getCivilStatus", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<CivilStatusTO>> getCivilStatus(){
        LOG.info("Init getCivilStatus");
        var resutl = this.employeeFacade.getCivilStatus();
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }


    @RequestMapping(value = "/getEmployeeByIdUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<EmployeeTO> getEmployeeByIdUser(@RequestParam("idUser") Long idUser){
        LOG.info("Init getEmployeeByIdUser");
        var resutl = this.employeeFacade.getEmployeeByIdUser(idUser);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }
    @RequestMapping(value = "/getEmployeeByName", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<EmployeeComplementaryTO> getEmployeeByIdUser(@Param("name") String name,
                                                          @Param("lastName") String lastName,
                                                          @Param("birthDate") LocalDateTime birthDate){
        LOG.info("Init getEmployeeByIdName");
        var resutl = this.employeeFacade.getEmployeeComplementaryByName(name,lastName,birthDate);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAllEmployees", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<EmployeeComplementaryTO>> getAllEmployees(){
        LOG.info("Init getAllEmployees");
        var resutl = this.employeeFacade.getAllEmployees();
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }

    @RequestMapping(value = "/getEmployeeAdressByIdUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<EmployeeAddressTO> getEmployeeAdressByIdUser(@RequestParam("idUser") Long idUser){
        LOG.info("Init getEmployeeAdressByIdUser");
        var resutl = this.employeeFacade.getEmployeeAdressByIdUser(idUser);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }

    @RequestMapping(value = "/getSocialNetworkByIdUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<SocialNetworkTO>> getSocialNetworkByIdUser(@RequestParam("idUser") Long idUser){
        LOG.info("Init getSocialNetworkByIdUser");
        var resutl = this.employeeFacade.getSocialNetworkByIdUser(idUser);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }

    @RequestMapping(value = "/getContratingDataByIdUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ContratingDataTO> getContratingDataByIdUser(@RequestParam("idUser") Long idUser) {
        LOG.info("Init getContratingDataByIdUser");
        var resutl = this.employeeFacade.getContratingDataByIdUser(idUser);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }

    @RequestMapping(value = "/getEmployeeHistoryByIdUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<HistoryEmployeeTO> getEmployeeHistoryByIdUser(@RequestParam("idUser") Long idUser) {
        LOG.info("Init getEmployeeHistoryByIdUser");
        var resutl = this.employeeFacade.getEmployeeHistoryByIdUser(idUser);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }
    @RequestMapping(value = "/getEmployeeCompensationByIdUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<CompensationPackageTO>> getEmployeeCompensationByIdUser(@RequestParam("idUser") Long idUser){
        LOG.info("Init getEmployeeCompensationByIdUser");
        var resutl = this.employeeFacade.getEmployeeCompensationByIdUser(idUser);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }
    @RequestMapping(value = "/getEmployeeAsignationByIdUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<AsignationDataTO> getEmployeeAsignationByIdUser(@RequestParam("idUser") Long idUser){
        LOG.info("Init getEmployeeAsignationByIdUser");
        var resutl = this.employeeFacade.getEmployeeAsignationByIdUser(idUser);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }

    @RequestMapping(value = "/getUserRegisterSico", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<EmployeeComplementaryTO> getUserRegisterSico(@RequestParam("name") String name,@RequestParam("lastName") String lastName,@RequestParam("birthDate") String birthDate){
        LOG.info("Init getUserRegisterSico");
        var resutl = this.employeeFacade.getUserRegisterSico(name,lastName,birthDate);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }
    @RequestMapping(value = "/getEmployeesByCurpClientProject", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<EmployeesClientProjectTO>> getEmployeesByCurpClientProject(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(value = "nametab",defaultValue = "") String nameTab,
                                                                     @RequestParam("email") String email,
                                                                     @RequestParam("curp") String curp,
                                                                     @RequestParam("client") String client,
                                                                     @RequestParam("project") String project) throws NoSuchFieldException, NoSuchMethodException {
        LOG.info("Init getEmployeesByCurpClientProject");

        var result = this.employeeFacade.getPageEmployeesByCurpClientProject(page,email.toUpperCase(),curp.toUpperCase(), client.toUpperCase(), project.toUpperCase());
        //var resutl = this.employeeFacade.getEmployeesByCurpClientProject(curp.toUpperCase(), client.toUpperCase(), project.toUpperCase());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @RequestMapping(value = "/getUserRegisterById", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<EmployeeComplementaryTO> getUserRegisterById(@RequestParam("id") Long id){
        LOG.info("Init getUserRegisterById");
        var resutl = this.employeeFacade.getUserRegisterById(id);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }


    @RequestMapping(value = "/saveOrUpdateMyCv" , method = RequestMethod.POST , produces = "application/json")
    public ResponseEntity<MycvTO>  saveOrUpdateMyCv(@RequestBody MycvTO mycv){
        LOG.info("Init saveOrUpdateMyCv");
        MycvTO mycvTO = this.employeeFacade.saveOrUpdateMyCv(mycv);
        return new ResponseEntity<>(mycvTO,HttpStatus.OK);
    }


    @RequestMapping(value = "/getCvByEmail", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<MycvTO> getCvByEmail(@RequestParam("email") String email){
        LOG.info("Init getCvByEmail");
        var resutl = this.employeeFacade.getCvByEmail(email);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }

    @RequestMapping(value = "/getTabByIdUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<TabUserTO>> getTabByIdUser(@RequestParam("idUser") Long idUser){
        LOG.info("Init getTabByIdUser");
        var resutl = this.employeeFacade.getTabUser(idUser);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }

    @RequestMapping(value = "/getCountRow", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CountRowTO> getCountRow(@RequestParam("email") String email,
                                                  @RequestParam("curp") String curp,
                                                  @RequestParam("client") String client,
                                                  @RequestParam("project") String project){

        var countRowTO = this.employeeFacade.getCountRow(email.toUpperCase(),curp.toUpperCase(),client.toUpperCase(),project.toUpperCase());

        return new ResponseEntity<>(countRowTO, HttpStatus.OK);
    }

       @RequestMapping(value = "/getPonderationSection", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<PonderationMobileTO>> getPonderationSection(@RequestParam("idUser") Long idUser){
        LOG.info("Init getAllEmployees");
        var resutl = this.employeeFacade.getPonderationSection(idUser);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }


    @RequestMapping(value = "/getClient", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ClientTO>> getClient(){
        LOG.info("Init getClient");
        var resutl = this.employeeFacade.getClient();
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }

    @RequestMapping(value = "/getProjectByIdClient", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ProjectTO>> getProjectByIdClient(@RequestParam("idClient") Long idClient){
        LOG.info("Init getProjectByIdClient");
        var resutl = this.employeeFacade.getProjectByIdClient(idClient);
        return new ResponseEntity<>(resutl, HttpStatus.OK);
    }



}
