package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.*;
import mx.com.axity.commons.util.SHA;
import mx.com.axity.model.EmployeeComplementaryDO;
import mx.com.axity.model.EmployeeDO;
import mx.com.axity.services.facade.IEmployeeFacade;
import mx.com.axity.services.service.IEmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeeFacade implements IEmployeeFacade {

    @Autowired
    IEmployeeService employeeService;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Boolean saveOrUpdateEmployee(EmployeeTO employee) {
       var resp=false;
       try {
           Optional.ofNullable(employee).orElseThrow();
           var employeeDO = this.employeeService.saveOrUpdateEmployee(employee);
            resp = true;
       }catch (Exception e){
           throw new BusinessException(e.getMessage(), e);

       }
        return resp;
    }



    @Transactional
    public void saveOrUpdateEmployeeComplementaryMovil(EmployeePersonalTO  personal){
        try{
            this.employeeService.saveOrUpdateEmployeeComplementaryMovil(personal);
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }
    }







    @Transactional
    @Override
    public EmployeeTO saveOrUpdateEmployeeComplemtaryWeb(EmployeeComplementaryTO employeeCom) {

        try {
            Optional.ofNullable(employeeCom).orElseThrow();
            var user = new UserTO();
            if (employeeCom.getEmployee().getUser().getId() == null || employeeCom.getEmployee().getUser().getId() < 1) {
// region if
                user.setEmail(employeeCom.getEmail());
                user.setActive(true);
                user.setPassword(SHA.encrypt(employeeCom.getEmail()));
                user.setLevel(0L);
                user.setUserType(employeeCom.getEmployee().getUser().getUserType());
                user.setUserStatus("A");
                user.setCreationDate(LocalDateTime.now());
                user.setLastModification(LocalDateTime.now());
                user.setCreationUser(employeeCom.getEmployee().getLastUserModifier());
                user.setLastUserModifier(employeeCom.getEmployee().getLastUserModifier());
                try {
                    user = this.modelMapper.map(this.employeeService.AddUser(user), UserTO.class);
                    employeeCom.getEmployee().getUser().setId(user.getId());
                    employeeCom.getEmployee().setCreationDate(LocalDateTime.now());
                    employeeCom.getEmployee().setLastModification(LocalDateTime.now());
                    employeeCom.setBirthDate(employeeCom.getBirthDate().toLocalDate().atStartOfDay());
                    employeeCom.getEmployee().setActive(true);
                    employeeCom.setCreationDate(LocalDateTime.now());
                    employeeCom.setLastModification(LocalDateTime.now());

                } catch (Exception e) {
                    throw new BusinessException(e.getMessage(), e);
                }
// endregion

            } else {
                employeeCom.getEmployee().getUser().setLastModification(LocalDateTime.now());
                employeeCom.getEmployee().getUser().setLastUserModifier(employeeCom.getEmployee().getLastUserModifier());
                try {
                    this.employeeService.AddUser(employeeCom.getEmployee().getUser());
                } catch (Exception e) {


                    if (e.getMessage().contains("user_ds_email")) {
                        throw new BusinessException("El correo electrónico que desea registrar ya existe, favor de validar", e);
                    }
                    else
                    {
                        throw new BusinessException(e.getMessage(), e);
                    }
                }

                employeeCom.getEmployee().setLastModification(LocalDateTime.now());
                employeeCom.setLastModification(LocalDateTime.now());
                employeeCom.setBirthDate(employeeCom.getBirthDate().toLocalDate().atStartOfDay());
            }

            var employee = this.employeeService.saveOrUpdateEmployee(employeeCom.getEmployee());
            var oEmployee = this.modelMapper.map(employee, EmployeeTO.class);
            employeeCom.setEmployee(oEmployee);
             this.employeeService.saveOrUpdateEmployeeComplementary(employeeCom);
            return employeeCom.getEmployee();
           }
        catch (Exception e) {
            if (e.getMessage().contains("k_employee_complementary_un_email")) {
                throw new BusinessException("El correo electrónico que desea registrar ya existe, favor de validar", e);
            }
            else
               {
                 throw new BusinessException(e.getMessage(), e);
               }
        }


    }


    @Override
    public Boolean saveOrUpdateEmployeeAdress(EmployeeAddressTO employeeAdress) {
        try {
            Optional.ofNullable(employeeAdress).orElseThrow();
            return this.employeeService.saveOrUpdateEmployeeAdress(employeeAdress);

        }catch (Exception e) {

            throw new BusinessException(e.getMessage(), e);
        }
    }


    @Override
    public Boolean saveOrUpdateAsignationData(AsignationDataTO asigancion) {
        var resp=false;
        try {
            Optional.ofNullable(asigancion).orElseThrow();

           if(asigancion.getIdProject()!=null && asigancion.getIdClient()!=null && asigancion.getIdClient() > 0 && asigancion.getIdProject() > 0 )
           {
               var oEmployee=this.employeeService.getEmployeeByIdUser(asigancion.getIdUser());
               oEmployee.setClient(new ClientTO());
               oEmployee.getClient().setIdClient(asigancion.getIdClient());
               oEmployee.setProject(new ProjectTO());
               oEmployee.getProject().setIdProject(asigancion.getIdProject());
               oEmployee.getProject().setIdClient(asigancion.getIdClient());
               this.employeeService.saveOrUpdateEmployee(oEmployee);
           }

            var empAsignation = this.employeeService.saveOrUpdateAsignationData(asigancion);
            resp = true;

        }catch (Exception e){
            resp=false;
        }
        return resp;
    }

    @Override
    public Boolean saveOrUpdateCompesation(List<CompensationPackageTO> compensation) {
        var resp=false;
        try {
            Optional.ofNullable(compensation);
            var empAsignation = this.employeeService.saveOrUpdateCompesation(compensation);
            resp = true;

        }catch (Exception e){
        }
        return resp;
    }

    @Override
    public Boolean saveOrUpdateContrating(ContratingDataTO contrating) {
        var resp=false;
        try {
            Optional.ofNullable(contrating);

         var cv=   this.employeeService.getCvByIdUser(contrating.getIdUser());
         var user =this.employeeService.getEmployeeByIdUser(contrating.getIdUser());
         if(cv==null)
         {
             cv = new MycvTO();
             cv.setActive(true);
             cv.setCreationDate(LocalDateTime.now());
             cv.setLastModification(LocalDateTime.now());
             cv.setCreationUser(contrating.getDsLastUserModifier());
             cv.setLastUserModifier(contrating.getDsLastUserModifier());
             cv.setIdUser(contrating.getIdUser());
             cv.setNameCv(user.getUser().getEmail());
             cv.setValue(contrating.getCvPdf());
         }
         else
         {
             cv.setLastUserModifier(contrating.getDsLastUserModifier());
             cv.setValue(contrating.getCvPdf());
         }
            if(cv.getValue()!=null)
                {
                  this.saveOrUpdateMyCv(cv);
                }

            var empAsignation = this.employeeService.saveOrUpdateContrating(contrating);
            resp = true;

        }catch (Exception e){
           throw new  BusinessException(e.getMessage(),e);
        }
        return resp;
    }

    @Override
    public Boolean saveOrUpdateHistory(HistoryEmployeeTO history) {
        var resp=false;
        try {
            Optional.ofNullable(history);
            var empAsignation = this.employeeService.saveOrUpdateHistory(history);
            resp = true;

        }catch (Exception e){}
        return resp;
    }

    @Override
    public Boolean saveOrUpdateSocialNetwork(List<SocialNetworkTO> socialNetwork) {
        var resp=false;
        try {
            Optional.ofNullable(socialNetwork).orElseThrow();
             this.employeeService.saveOrUpdateSocialNetwork(socialNetwork);
            resp = true;

        }catch (Exception e){
            resp =false;
        }
        return resp;
    }

    @Override
    public List<CityTO> getCity(Long idState)
    {
        try{
            return this.employeeService.getCity(idState);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    @Override
    public List<StateTO> getState()
    {
        try{
            return this.employeeService.getState();
        }
        catch (Exception e)
        {
            return null;
        }
    }
    @Override
    public List<CivilStatusTO> getCivilStatus()
    {
        try{
            return this.employeeService.getCivilStatus();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public EmployeeTO getEmployeeByIdUser(Long idUser)
    {
        try{
            return this.employeeService.getEmployeeByIdUser(idUser);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public EmployeeComplementaryTO getEmployeeComplementaryByName(String name, String lastName, LocalDateTime birthDate)
    {
       try{
           return this.employeeService.getEmployeeComplementaryName(name,lastName,birthDate);
       }
       catch (Exception e)
       {
           return null;
       }
    }
    @Override
    public List<EmployeeComplementaryTO> getAllEmployees()
    {
        try{
            return this.employeeService.getAllEmployees();
        }
        catch (Exception e)
        {
            return null;
        }
    }


   public  EmployeeAddressTO getEmployeeAdressByIdUser(Long idUser)
   {
       try{
           var employeeAdressByIdUser= this.employeeService.getEmployeeAdressByIdUser(idUser);
           Optional.ofNullable(employeeAdressByIdUser).orElseThrow();
           return employeeAdressByIdUser;
       }
       catch (Exception e)
       {
            throw new BusinessException(e.getMessage(),e);
       }
   }
    public List<SocialNetworkTO> getSocialNetworkByIdUser(Long idUser){
        try{
            var socialNetworkByIdUser= this.employeeService.getSocialNetworkByIdUser(idUser);
            Optional.ofNullable(socialNetworkByIdUser).orElseThrow();
            return  socialNetworkByIdUser;
        }
        catch (Exception e)
        {
           throw new BusinessException(e.getMessage(),e);
        }
    }
    public ContratingDataTO  getContratingDataByIdUser(Long idUser){
        try{

            var contratingData= this.employeeService.getContratingDataByIdUser(idUser);
            Optional.ofNullable(contratingData).orElseThrow();
            return contratingData;
        }
        catch (Exception e)
        {
            throw new BusinessException(e.getMessage(),e);
        }
    }
    public HistoryEmployeeTO  getEmployeeHistoryByIdUser(Long idUser){
        try{
            return this.employeeService.getEmployeeHistoryByIdUser(idUser);
        }
        catch (Exception e)
        {
            return null;
        }
    }

   public List<CompensationPackageTO>  getEmployeeCompensationByIdUser(Long idUser){

       var employeeCompensationByIdUser = this.employeeService.getEmployeeCompensationByIdUser(idUser);
       Optional.ofNullable(employeeCompensationByIdUser.size() > 0 ? employeeCompensationByIdUser : null).orElseThrow();
      return employeeCompensationByIdUser;
   }

   public  AsignationDataTO  getEmployeeAsignationByIdUser(Long idUser){
       try{
           var asignationData= this.employeeService.getEmployeeAsignationByIdUser(idUser);
           Optional.ofNullable(asignationData).orElseThrow();

           var oEmployee = this.employeeService.getEmployeeByIdUser(idUser);
           if(oEmployee==null || oEmployee.getClient()==null || oEmployee.getProject()==null)
           {
               return asignationData;
           }

           asignationData.setIdClient(oEmployee.getClient().getIdClient());
           asignationData.setIdProject(oEmployee.getProject().getIdProject());
           return asignationData;
       }
       catch (Exception e)
       {
           throw new BusinessException(e.getMessage(),e);
       }
   }

    public EmployeeComplementaryTO getUserRegisterSico(String name, String lastName, String birthDate) {
        try {
            Instant instant = Instant.parse(birthDate);
            return this.employeeService.getUserRegisterSico(name,lastName,LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId())));
        }catch (Exception e){
           throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public EmployeeComplementaryTO getUserRegisterById(Long id) {

        try {
            return this.employeeService.getUserRegisterById(id);
        }catch (Exception e){
            throw new BusinessException("error en getUserRegisterById",e);
        }
    }

    @Override
    public void saveOrUpdateUser(UserTO user) {
        try {
            this.employeeService.saveOrUpdateUser(user);
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public MycvTO saveOrUpdateMyCv(MycvTO mycv) {
        if (mycv.getIdMycv() == null){
            mycv.setLastModification(LocalDateTime.now());
            mycv.setCreationDate(LocalDateTime.now());
            mycv.setActive(Boolean.TRUE);
            return this.employeeService.saveOrUpdateMyCv(mycv);
        }
        mycv.setLastModification(LocalDateTime.now());
        return this.employeeService.saveOrUpdateMyCv(mycv);
    }

    @Override
    public MycvTO getCvByEmail(String email) {
        try {
            return this.employeeService.getCvByEmail(email);
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }

    }




    @Override
    public List<EmployeesClientProjectTO> getEmployeesByCurpClientProject(String email,String curp, String client, String project) throws NoSuchFieldException, NoSuchMethodException {
         List<EmployeesClientProjectTO> userData = new ArrayList<>();
         try {

             var employeeClientProject= this.employeeService.getAllUserData(email,curp,client,project);


             for (EmployeesClientProjectTO item : employeeClientProject) {
                 EmployeesClientProjectTO employee = new EmployeesClientProjectTO();
                 var idUser=item.getIdUser();
// region datos employee
                 employee.setIdEmployee(item.getIdEmployee());
                 employee.setIdUser(item.getIdUser());
                 employee.setUserType(item.getUserType());
                 employee.setCivilStatus(item.getCivilStatus());
                 employee.setName(item.getName());
                 employee.setLastName(item.getLastName());
                 employee.setLastMName(item.getLastMName());
                 employee.setGender(item.getGender());
                 employee.setIdClient(item.getIdClient());
                 employee.setIdProject(item.getIdProject());
                 employee.setWorkPermitConfirm(item.getWorkPermitConfirm());
                 employee.setStatus(item.getStatus());
                 employee.setClient(item.getClient());
                 employee.setProject(item.getProject());


//endregion
// region  complementary data

                 employee.setId(item.getId());
                 employee.setIdSwap(item.getIdSwap());
                 employee.setRfc(item.getRfc());
                 employee.setCurp(item.getCurp());
                 employee.setNss(item.getNss());
                 employee.setEmailClient(item.getEmailClient());
                 employee.setEmail(item.getEmail());
                 employee.setPhone(item.getPhone());
                 employee.setWorkPermit(item.getWorkPermit());
                 employee.setBirthDate(item.getBirthDate());
                 employee.setBirthState(item.getBirthState());
                 employee.setBirthCountry(item.getBirthCountry());
                 employee.setPassportNumber(item.getPassportNumber());
                 employee.setNationality(item.getNationality());
                 employee.setLastUserModifier(item.getLastUserModifier());
// endregion
// region domicile


                 var domicile = this.employeeService.getEmployeeAdressByIdUser(idUser);
                 if (domicile != null) {
                     employee.setIdStreet(domicile.getId());
                     employee.setStreet(domicile.getStreet());
                     employee.setInteriorNumber(domicile.getInteriorNumber());
                     employee.setOutDoorNumber(domicile.getOutDoorNumber());
                     employee.setColony(domicile.getColony());
                     employee.setPostalCode(domicile.getPostalCode());
                     employee.setCity(domicile.getCity());
                     employee.setState(domicile.getState());
                 }
// endregion
// region  contrating data
                 var contrating = this.employeeService.getContratingDataByIdUser(idUser);
                 if (contrating != null) {
                     employee.setIdContrating(contrating.getIdContrating());
                     employee.setSkill(contrating.getSkill());
                     employee.setQtSalary(contrating.getQtSalary());
                     employee.setDsArea(contrating.getDsArea());
                     employee.setJob(contrating.getJob());
                     if(contrating.getEndOfContract()!=null)
                     {
                         employee.setEndOfContract(contrating.getEndOfContract().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                     }

                 }
// endregion
// region asignation
                 var asignation = this.employeeService.getEmployeeAsignationByIdUser(idUser);
                 if (asignation != null) {
                     employee.setEmployeePosition(asignation.getEmployeePosition());
                     employee.setManager(asignation.getManager());
                     employee.setAsinationState(asignation.getState());
                     employee.setAsignationCity(asignation.getCity());
                     employee.setEmailDirectBoss(asignation.getEmailDirectBoss());
                     employee.setTelephoneDirectBoss(asignation.getTelephoneDirectBoss());
                     if(asignation.getStartAssigment()!=null)
                     {
                         employee.setStartAssigment(asignation.getStartAssigment().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                     }
                    if(asignation.getEndAllocation()!=null)
                    {
                       employee.setEndAllocation(asignation.getEndAllocation().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    }

                     employee.setAllocationEmail(asignation.getAllocationEmail());
                     employee.setAllocationSalary(asignation.getAllocationSalary());
                     employee.setEvaluation(asignation.getEvaluation());
                 }

// endregion
//region packge compensation
                 List<CompensationPackageTO> compenration = this.employeeService.getEmployeeCompensationByIdUser(idUser);
                 if (compenration != null)
                 {
                     for (CompensationPackageTO serv : compenration
                     ) {
//region case
                         switch (serv.getDsName()) {
                             case "Sueldo_bruto_mensual":
                                 employee.setSueldo_bruto_mensual(serv.getValor());
                                 break;
                             case "Automovil":
                                 employee.setAutomovil(serv.getValor());
                                 break;
                             case "Gastos_Automovil":
                                 employee.setGastos_Automovil(serv.getValor());
                                 break;
                             case "Opcion_Compra":
                                 employee.setOpcion_Compra(serv.getValor());
                                 break;
                             case "Bono_Mensual":
                                 employee.setBono_Mensual(serv.getValor());
                                 break;
                             case "Cantidad_Bono_Mensual":
                                 employee.setCantidad_Bono_Mensual(serv.getValor());
                                 break;
                             case "Bono_Bimestral":
                                 employee.setBono_Bimestral(serv.getValor());
                                 break;
                             case "Cantidad_Bono_Bimestral":
                                 employee.setCantidad_Bono_Bimestral(serv.getValor());
                                 break;
                             case "Bono_Trimestral":
                                 employee.setBono_Trimestral(serv.getValor());
                                 break;
                             case "Cantidad_Bono_Trimestral":
                                 employee.setCantidad_Bono_Trimestral(serv.getValor());
                                 break;
                             case "Bono_Anual":
                                 employee.setBono_Anual(serv.getValor());
                                 break;
                             case "Cantidad_Bono_Anual":
                                 employee.setCantidad_Bono_Anual(serv.getValor());
                                 break;
                             case "Metricas_Otorgamiento_Bono":
                                 employee.setMetricas_Otorgamiento_Bono(serv.getValor());
                                 break;
                             case "Fondo_de_Ahorro":
                                 employee.setFondo_de_Ahorro(serv.getValor());
                                 break;
                             case "Cantidad_Fondo_de_Ahorro":
                                 employee.setCantidad_Fondo_de_Ahorro(serv.getValor());
                                 break;
                             case "Vales_de_Despensa":
                                 employee.setVales_de_Despensa(serv.getValor());
                                 break;
                             case "Cantidad_Vales_de_Despensa":
                                 employee.setCantidad_Vales_de_Despensa(serv.getValor());
                                 break;
                             case "Vales_Restaurante":
                                 employee.setVales_Restaurante(serv.getValor());
                                 break;
                             case "Cantidad_Vales_Restaurante":
                                 employee.setCantidad_Vales_Restaurante(serv.getValor());
                                 break;
                             case "Vales_Gasolina":
                                 employee.setVales_Gasolina(serv.getValor());
                                 break;
                             case "Cantidad_Vales_Gasolina":
                                 employee.setCantidad_Vales_Gasolina(serv.getValor());
                                 break;
                             case "Aguinaldo":
                                 employee.setAguinaldo(serv.getValor());
                                 break;
                             case "Dias_Aguinaldo":
                                 employee.setDias_Aguinaldo(serv.getValor());
                                 break;
                             case "Cuantos_dias_de_vacaciones":
                                 employee.setCuantos_dias_de_vacaciones(serv.getValor());
                                 break;
                             case "Porcentaje_prima_vacacional":
                                 employee.setPorcentaje_prima_vacacional(serv.getValor());
                                 break;
                             case "Seguro_GM_Mayores":
                                 employee.setSeguro_GM_Mayores(serv.getValor());
                                 break;
                             case "Seguro_GM_Menores":
                                 employee.setSeguro_GM_Menores(serv.getValor());
                                 break;
                             case "Seguro_de_vida":
                                 employee.setSeguro_de_vida(serv.getValor());
                                 break;
                             case "Meses_de_Cobertura_por_Muerte":
                                 employee.setMeses_de_Cobertura_por_Muerte(serv.getValor());
                                 break;
                             case "Reparto_de_utilidades":
                                 employee.setReparto_de_utilidades(serv.getValor());
                                 break;
                             case "Ultimo_monto_recibido":
                                 employee.setUltimo_monto_recibido(serv.getValor());
                                 break;
                             case "Plan_de_pensiones":
                                 employee.setPlan_de_pensiones(serv.getValor());
                                 break;
                             case "Otra_prestacion":
                                 employee.setOtra_prestacion(serv.getValor());
                                 break;
                             case "Ingreso_mensual_bruto_integrado":
                                 employee.setIngreso_mensual_bruto_integrado(serv.getValor());
                                 break;
                             case "Ingreso_anual_bruto_estimado":
                                 employee.setIngreso_anual_bruto_estimado(serv.getValor());
                                 break;
                         }
                         //endregion
                     }
             }
 //endregion

                 userData.add(employee);
             }
         }
         catch (Exception e)
         {
             return null;
         }
         return userData;
     }

    @Override
    public List<TabUserTO> getTabUser(Long idUser) {
        try {
            return this.employeeService.getTabUser(idUser);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public CountRowTO getCountRow(String email,String curp, String client, String project) {

        try {
            var container = this.getEmployeesByCurpClientProject(email,curp, client, project);
            return new CountRowTO((long) container.size());
        }catch (Exception e){
            throw new BusinessException(e.getMessage(), e);
        }

    }

    @Override
    public List<EmployeesClientProjectTO> getPageEmployeesByCurpClientProject(int page,String email,String curp, String client, String proyect) {
        try{
            var pag = 10;

            var listEmployees = this.getEmployeesByCurpClientProject(email,curp, client, proyect);

             return listEmployees.subList(((pag*page) >= listEmployees.size() ? listEmployees.size() : (pag*page)),((pag*(page+1)) > listEmployees.size() ? listEmployees.size() : (pag*(page+1)) ));

        }catch(Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
    
    @Override
    public List<PonderationMobileTO> getPonderationSection(Long idUser) {
        return this.employeeService.getPonderationSection(idUser);
    }

    @Override
    public List<ClientTO> getClient()
    {
        try{
            return this.employeeService.getClient();
        }
        catch (Exception e)
        {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<ProjectTO> getProjectByIdClient(Long idClient)
    {
        try{
            return this.employeeService.getProject(idClient);
        }
        catch (Exception e)
        {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public ExcelGenericFormatExportTO sendExcel() {

        try{

            Gson g = new Gson();
            RestTemplate rest = new RestTemplate();

            var listEmployees = this.getEmployeesByCurpClientProject("","", "", "");

            InfoExcelTO info = new InfoExcelTO();
            info.setAllEmployeesTO(listEmployees);
            final String urlApplication = "http://application-service/generic/getExcelUsers";
            //final String urlApplication = "http://localhost:8091/generic/getExcelUsers";

            //var responseJson = rest.postForObject(urlApplication,info, ExcelGenericFormatExportTO.class);

            HttpHeaders headerFintech = new HttpHeaders();
            headerFintech.set("Content-Type", "application/json");

            var request = new HttpEntity<>(info, headerFintech);

            var resp = this.restTemplate.exchange(urlApplication, HttpMethod.POST, request, String.class);

            String excelBase64 = resp.getBody();
            ExcelGenericFormatExportTO excelTO = g.fromJson(excelBase64,ExcelGenericFormatExportTO.class);
            return excelTO;

        }catch(Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
}
