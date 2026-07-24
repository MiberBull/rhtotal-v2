package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.*;
import mx.com.axity.model.*;
import mx.com.axity.persistence.*;
import mx.com.axity.procedures.IProcedureInvoker;
import mx.com.axity.services.service.IEmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImp implements IEmployeeService {

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    EmployeeComplementaryDAO employeeComplementaryDAO;

    @Autowired
    EmployeeAddressDAO employeeAddressDAO;

    @Autowired
    AsignationDataDAO asignationDAO;

    @Autowired
    CompesationPackageDAO compensationPackageDAO;

    @Autowired
    ContratingDataDAO contratingDataDAO;

    @Autowired
    HistoryEmployeeDAO historyEmployeeDAO;

    @Autowired
    EmployeeSocialNetworkDAO socialNetworkDAO;

    @Autowired
    CityDAO cityDAO;

    @Autowired
    StateDAO stateDAO;

    @Autowired
    CivilStatusDAO civilStatusDAO;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserDAO userDAO;

    @Autowired
    MycvDAO mycvDAO;

    @Autowired
    IProcedureInvoker procedureInvoker;

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    ProjectDAO projectDAO;

    @Autowired
    EmergencyContactDAO emergencyContactDAO;

    @Autowired
    TenantDAO tenantDAO;


    final static Logger LOG = LogManager.getLogger(EmployeeServiceImp.class);

    @Override
    public EmployeeDO saveOrUpdateEmployee(EmployeeTO employee) {
        if (employee.getId() == null) {
            employee.setCreationDate(LocalDateTime.now());
        } else {
            employee.setCreationDate(employee.getCreationDate());
        }
        employee.setLastModification(LocalDateTime.now());
        employee.setActive(Boolean.TRUE);
        var doEmployee=this.modelMapper.map(employee, EmployeeDO.class);
        return this.employeeDAO.save(doEmployee);
    }

    @Override
    public boolean saveOrUpdateEmployeeAdress(EmployeeAddressTO emplAdress) {
        if (emplAdress.getId() != null) {
            emplAdress.setCreationDate(emplAdress.getCreationDate());
        } else {
            emplAdress.setCreationDate(LocalDateTime.now());
        }

        emplAdress.setLastModification(LocalDateTime.now());
        emplAdress.setActive(true);
        var save = this.employeeAddressDAO.save(this.modelMapper.map(emplAdress, EmployeeAddressDO.class));
        Optional.ofNullable(save).orElseThrow();
        return true;
    }

    @Override
    public EmployeeComplementaryDO saveOrUpdateEmployeeComplementary(EmployeeComplementaryTO empComplem) {
        if (empComplem.getId() == null) {
            empComplem.setCreationDate(LocalDateTime.now());
        } else {
            empComplem.setCreationDate(empComplem.getCreationDate());
        }
        empComplem.setLastModification(LocalDateTime.now());
        empComplem.setActive(true);

        return this.employeeComplementaryDAO.save(this.modelMapper.map(empComplem, EmployeeComplementaryDO.class));
    }

    @Override
    public AssigationDataDO saveOrUpdateAsignationData(AsignationDataTO asigancion) {
        if (asigancion.getIdDataAssigment() > 0) {
            asigancion.setCreationDate(asigancion.getCreationDate());
        } else {
            asigancion.setCreationDate(LocalDateTime.now());
        }
        asigancion.setLastModification(LocalDateTime.now());
        asigancion.setActive(true);
        return this.asignationDAO.save(this.modelMapper.map(asigancion, AssigationDataDO.class));
    }

    @Override
    public Boolean saveOrUpdateCompesation(List<CompensationPackageTO> compensation) {

        for (CompensationPackageTO item : compensation) {
            if ( item.getIdCompetation()==null || item.getIdCompetation() < 1) {
                item.setCreationDate(LocalDateTime.now());
            } else {
                item.setCreationDate(item.getCreationDate());
            }
            item.setLastModification(LocalDateTime.now());
            item.setActive(true);
        }

        Type listType = new TypeToken<List<CompesationPackageDO>>() {
        }.getType();

        this.compensationPackageDAO.saveAll(this.modelMapper.map(compensation, listType));

        return true;
    }

    @Override
    public ContratingDataDO saveOrUpdateContrating(ContratingDataTO contrating) {
        if (contrating.getIdContrating() > 0) {
            contrating.setCreationDate(contrating.getCreationDate());
        } else {
            contrating.setCreationDate(LocalDateTime.now());
        }
        contrating.setLastModification(LocalDateTime.now());
        contrating.setActive(true);
        return this.contratingDataDAO.save(this.modelMapper.map(contrating, ContratingDataDO.class));
    }

    @Override
    public HistoryEmployeeDO saveOrUpdateHistory(HistoryEmployeeTO history) {
        if (history.getIdEmployeeHis()==null || history.getIdEmployeeHis() < 1) {
            history.setCreationDate(LocalDateTime.now());
            history.setActive(true);
        }
        history.setLastModification(LocalDateTime.now());
        history.setActive(true);
        return this.historyEmployeeDAO.save(this.modelMapper.map(history, HistoryEmployeeDO.class));
    }


    @Override
    public Boolean saveOrUpdateSocialNetwork(List<SocialNetworkTO> socialNetworks) {

        for (SocialNetworkTO item : socialNetworks) {
            if (item.getIdSocialNet() < 1) {
                item.setCreationDate(LocalDateTime.now());
            } else {
                item.setCreationDate(item.getCreationDate());
            }
            item.setLastModification(LocalDateTime.now());
            item.setActive(Boolean.TRUE);

            if(item.getNameRedSocial().equalsIgnoreCase("Google")){
                item.setNameRedSocial("Google+");
            }
        }

        Type listType = new TypeToken<List<SocialNetworkDO>>() {
        }.getType();

        Iterable<SocialNetworkDO> socialNetworkDOS = this.socialNetworkDAO.saveAll(this.modelMapper.map(socialNetworks, listType));
        Optional.ofNullable(socialNetworkDOS).orElseThrow();
        return true;
    }

    @Override
    public List<ClientTO> getClient() {
        return (List<ClientTO>) this.modelMapper.map(this.clientDAO.getClientOrderByName(), new TypeToken<List<ClientTO>>() {
        }.getType());

    }

    @Override
    public List<ProjectTO> getProject(Long idClient) {
        return (List<ProjectTO>) this.modelMapper.map(this.projectDAO.getProjectOrderByName(idClient), new TypeToken<List<ProjectTO>>() {
        }.getType());

    }



    @Override
    public List<CityTO> getCity(Long idState) {
        return (List<CityTO>) this.modelMapper.map(this.cityDAO.getCityOrderByCity(idState), new TypeToken<List<CityTO>>() {
        }.getType());

    }

    @Override
    public List<StateTO> getState() {
        return (List<StateTO>) this.modelMapper.map(this.stateDAO.getStateOrderByName(), new TypeToken<List<StateTO>>() {
        }.getType());

    }

    @Override
    public List<CivilStatusTO> getCivilStatus() {
        return (List<CivilStatusTO>) this.modelMapper.map(this.civilStatusDAO.getCivilStatusOrderByName(), new TypeToken<List<CivilStatusTO>>() {
        }.getType());
    }
    @Override
    public EmployeeTO getEmployeeByIdUser(Long idUser)
    {
        var employeeByIdUser = this.employeeDAO.getEmployeeByIdUser(idUser);

        if (null != employeeByIdUser){
            return this.modelMapper.map(employeeByIdUser,EmployeeTO.class);
        }
        return new EmployeeTO() ;
    }

    @Override
    public EmployeeComplementaryTO getEmployeeComplementaryName(String name,String lastName, LocalDateTime birthDate)
    {
        var employeeByName= this.employeeComplementaryDAO.getEmployeeComplementaryByName(name,lastName,birthDate);
        if (null != employeeByName){
            return this.modelMapper.map(employeeByName,EmployeeComplementaryTO.class);
        }
        return new EmployeeComplementaryTO() ;
    }

    @Override

    public List<EmployeeComplementaryTO> getAllEmployees()
    {
        var employees= this.employeeComplementaryDAO.getAllEmployees();
        if (null != employees){
            return this.modelMapper.map(employees,new TypeToken<List<EmployeeComplementaryTO>>(){}.getType());
        }
        return null ;
    }

    @Override
    public EmployeeAddressTO getEmployeeAdressByIdUser(Long idUser)
    {
        var employeeAdressByIdUser = this.employeeAddressDAO.getEmployeeAdressByIdEmployee(idUser);
        if(null !=employeeAdressByIdUser)
        {
            return this.modelMapper.map(employeeAdressByIdUser,EmployeeAddressTO.class);
        }
       return null;
    }

    @Override
    public List<SocialNetworkTO> getSocialNetworkByIdUser(Long idUser)
    {
        var socialNetworkByIdUser = this.socialNetworkDAO.getEmployeeSocialNetworkByIdUser(idUser).isEmpty() ? null:this.socialNetworkDAO.getEmployeeSocialNetworkByIdUser(idUser);
if(null !=socialNetworkByIdUser)
{
    return this.modelMapper.map(socialNetworkByIdUser,new TypeToken<List<SocialNetworkTO>>(){}.getType());
}
return null;

    }

    @Override
    public ContratingDataTO  getContratingDataByIdUser(Long idUser)
    {
        var contratingDataByIdUser = this.contratingDataDAO.getEmployeeContratingDataByIdUser(idUser);
        if (null != contratingDataByIdUser){
            return this.modelMapper.map(contratingDataByIdUser,ContratingDataTO.class);
        }
        return null ;
    }

    @Override
    public HistoryEmployeeTO  getEmployeeHistoryByIdUser(Long idUser)
    {
        var employeeHistoryByIdUser = this.historyEmployeeDAO.getEmployeeHistoryByIdUser(idUser);
        if (null != employeeHistoryByIdUser){
            return this.modelMapper.map(employeeHistoryByIdUser,HistoryEmployeeTO.class);
        }
        return new HistoryEmployeeTO() ;
    }

    @Override
    public List<CompensationPackageTO>  getEmployeeCompensationByIdUser(Long idUser)
    {
        var employeeCompensationByIdUser = this.compensationPackageDAO.getCompesationPackageByIdUser(idUser);
        if (null != employeeCompensationByIdUser){
            return this.modelMapper.map(employeeCompensationByIdUser,new TypeToken<List<CompensationPackageTO>>(){}.getType());
        }
        return null ;
    }

    @Override
    public AsignationDataTO  getEmployeeAsignationByIdUser(Long idUser)
    {
        var employeeAsignationDataByIdUser = this.asignationDAO.getAsignationDataByIdUser(idUser);

        if (null != employeeAsignationDataByIdUser){
            return this.modelMapper.map(employeeAsignationDataByIdUser,AsignationDataTO.class);
        }
        return null ;
    }

    public UserDO AddUser(UserTO user)
    {
        return this.userDAO.save(this.modelMapper.map(user,UserDO.class));
    }

    public EmployeeComplementaryTO getUserRegisterSico(String name, String lastName, LocalDateTime birthDate) {
        var userRegisterSico = this.employeeComplementaryDAO.getUserRegisterSico(name, lastName, birthDate);
        Optional.ofNullable(userRegisterSico).orElseThrow();
        return  this.modelMapper.map(userRegisterSico, EmployeeComplementaryTO.class);

    }

    @Override
    public void saveOrUpdateUser(UserTO user) {
        this.userDAO.save(this.modelMapper.map(user, UserDO.class));

    }

    @Override
    public EmployeeComplementaryTO getUserRegisterById(Long id) {
        var userRegisterById = this.employeeComplementaryDAO.getUserRegisterById(id);
        Optional.ofNullable(userRegisterById).orElseThrow();
        return  this.modelMapper.map(userRegisterById, EmployeeComplementaryTO.class);

    }

    @Override
    public List<EmployeeComplementaryTO> getUserData(String curp, String client, String project) {
      return this.modelMapper.map(this.employeeComplementaryDAO.findEmployeeByClientProyectCurp(curp,null,project),new TypeToken<List<EmployeeComplementaryTO>>(){}.getType());
    }

    @Override
    public List<EmployeesClientProjectTO> getAllUserData(String email,String curp, String client, String project) {
        client = client == "" ? client : "%"+client+"%";
        project = project == "" ? project:"%"+project+"%";
        email = email == "" ? email : "%"+email+"%";
        curp = "%"+curp+"%";
        return this.procedureInvoker.ProcedureGetEmployeeByIdUser(email,curp, client, project);
    }

    @Override
    public MycvTO saveOrUpdateMyCv(MycvTO mycv) {
        var save = this.mycvDAO.save(this.modelMapper.map(mycv, MycvDO.class));
        var result = save.getIdUser() != null ? save :null ;
        Optional.ofNullable(result).orElseThrow();
        return this.modelMapper.map(result,MycvTO.class);
    }

    @Override
    public MycvTO getCvByEmail(String email) {
        var map = this.modelMapper.map(this.mycvDAO.findBynameCv(email), MycvTO.class);
        Optional.ofNullable(map.getIdMycv() != null ? map : null).orElseThrow();
        return map;
    }

    @Override
    public MycvTO getCvByIdUser(Long idUser) {
      var cv =  this.mycvDAO.findByIdUser(idUser);
        if(cv!=null)
        {
            return this.modelMapper.map(cv, MycvTO.class);
        }
        return null;
    }



    @Override
    public List<TabUserTO> getTabUser(Long idUser)
    {
       return this.procedureInvoker.ProcedureTabUser(idUser);
    }

    @Override
    public List<PonderationMobileTO> getPonderationSection(Long idUser) {
        List<Object[]> ponderationSection = this.employeeComplementaryDAO.getPonderationSection(idUser);
        List<PonderationMobileTO> mobileTOList = new ArrayList<>();
        PonderationMobileTO ponderationMobileTO ;
        for(Object[] obj : ponderationSection){
           ponderationMobileTO =  new PonderationMobileTO();
            ponderationMobileTO.setNameNivel(String.valueOf(obj[0]));
            ponderationMobileTO.setTotalUser(Integer.parseInt(String.valueOf(obj[1])));
            ponderationMobileTO.setTotal(Integer.parseInt(String.valueOf(obj[2])));
            ponderationMobileTO.setPorcentaje(Integer.parseInt(String.valueOf(obj[3])));
            ponderationMobileTO.setPorcentajeGrafica(Integer.parseInt(String.valueOf(obj[4])));
            ponderationMobileTO.setPromedioFinal(Integer.parseInt(String.valueOf(obj[5])));
            ponderationMobileTO.setPromedioFinalGrafica(Integer.parseInt(String.valueOf(obj[6])));
            mobileTOList.add(ponderationMobileTO);
        }

        return mobileTOList;
    }



    // --- Sprint 15: Ficha del Colaborador ---

    @Override
    public EmployeeTO getEmployeeById(Long idEmployee) {
        var opt = employeeDAO.findOptionalById(idEmployee);
        if (opt.isPresent()) {
            return modelMapper.map(opt.get(), EmployeeTO.class);
        }
        return null;
    }

    @Override
    public EmergencyContactDO saveOrUpdateEmergencyContact(EmergencyContactTO emergency) {
        var existing = emergencyContactDAO.findByIdEmployeeAndTenantIdAndFgActiveTrue(
                emergency.getIdEmployee(), emergency.getTenantId());
        EmergencyContactDO entity;
        if (existing.isPresent()) {
            entity = existing.get();
        } else {
            entity = new EmergencyContactDO();
            entity.setIdEmployee(emergency.getIdEmployee());
            entity.setTenantId(emergency.getTenantId());
        }
        entity.setDsName(emergency.getDsName());
        entity.setDsRelationship(emergency.getDsRelationship());
        entity.setDsPhone(emergency.getDsPhone());
        entity.setFgActive(true);
        return emergencyContactDAO.save(entity);
    }

    @Override
    public EmergencyContactTO getEmergencyContactByEmployee(Long idEmployee, String tenantId) {
        var opt = emergencyContactDAO.findByIdEmployeeAndTenantIdAndFgActiveTrue(idEmployee, tenantId);
        if (opt.isPresent()) {
            return modelMapper.map(opt.get(), EmergencyContactTO.class);
        }
        return null;
    }

    @Override
    public void terminateEmployee(Long idEmployee, String reason, LocalDate terminationDate) {
        var employeeDO = employeeDAO.findOptionalById(idEmployee)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado: " + idEmployee));
        employeeDO.setDsTerminationReason(reason);
        employeeDO.setDtTerminationDate(terminationDate);
        employeeDO.setActive(false);
        employeeDO.setLastModification(LocalDateTime.now());
        employeeDAO.save(employeeDO);
    }

    @Override
    public String getTenantName(String tenantId) {
        return tenantDAO.findById(tenantId)
                .map(TenantDO::getName)
                .orElse(tenantId);
    }

    @Override
    public void saveOrUpdateEmployeeComplementaryMovil(EmployeePersonalTO  personal) {

        if (personal.getComplementary().getId() == null) {
            EmployeeTO employee = personal.getComplementary().getEmployee();
            employee.setLastModification(LocalDateTime.now());
            employee.setCreationDate(LocalDateTime.now());
            employee.setActive(Boolean.TRUE);
            EmployeeDO employeeId = this.employeeDAO.save(this.modelMapper.map(employee, EmployeeDO.class));

            if (employee.getUser().getUserType().equalsIgnoreCase("IN")){
                EmployeeAddressTO addressTO = personal.getAddressTO();
                addressTO.setEmployee(this.modelMapper.map(employeeId,EmployeeTO.class));
                addressTO.setLastModification(LocalDateTime.now());
                addressTO.setCreationDate(LocalDateTime.now());
                addressTO.setActive(Boolean.TRUE);
                this.employeeAddressDAO.save(this.modelMapper.map(addressTO,EmployeeAddressDO.class));

                this.userDAO.save(this.modelMapper.map(employee.getUser(), UserDO.class));
            }

            EmployeeComplementaryTO complementary = personal.getComplementary();
            complementary.setEmployee(this.modelMapper.map(employeeId,EmployeeTO.class));

            complementary.setLastModification(LocalDateTime.now());
            complementary.setCreationDate(LocalDateTime.now());
            complementary.setActive(Boolean.TRUE);
            this.employeeComplementaryDAO.save(this.modelMapper.map(complementary,EmployeeComplementaryDO.class));

            return;
        }

        EmployeeComplementaryTO complementary = personal.getComplementary();
        complementary.setLastModification(LocalDateTime.now());
        EmployeeTO employee = personal.getComplementary().getEmployee();
        complementary.setBirthDate(complementary.getBirthDate().toLocalDate().atStartOfDay());
        employee.setLastModification(LocalDateTime.now());
        this.employeeDAO.save(this.modelMapper.map(employee,EmployeeDO.class));
        this.employeeComplementaryDAO.save( this.modelMapper.map(complementary,EmployeeComplementaryDO.class));
    }




    }