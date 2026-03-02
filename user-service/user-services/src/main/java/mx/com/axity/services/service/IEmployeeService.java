package mx.com.axity.services.service;

import mx.com.axity.commons.to.*;
import mx.com.axity.model.*;

import java.time.LocalDateTime;
import java.util.List;

public interface IEmployeeService {

    EmployeeDO saveOrUpdateEmployee(EmployeeTO employee);
    boolean saveOrUpdateEmployeeAdress(EmployeeAddressTO emplAdress);
    EmployeeComplementaryDO saveOrUpdateEmployeeComplementary(EmployeeComplementaryTO empComplem);
    AssigationDataDO saveOrUpdateAsignationData(AsignationDataTO asigancion);
    Boolean saveOrUpdateCompesation(List<CompensationPackageTO> compensation);
    ContratingDataDO saveOrUpdateContrating(ContratingDataTO contrating);
    HistoryEmployeeDO saveOrUpdateHistory(HistoryEmployeeTO history);
    Boolean saveOrUpdateSocialNetwork(List<SocialNetworkTO> socialNetworks);
    List<CityTO> getCity(Long idState);
    List<StateTO> getState();
    List<CivilStatusTO> getCivilStatus();
    EmployeeTO getEmployeeByIdUser(Long idUser);
    EmployeeComplementaryTO getEmployeeComplementaryName(String name,String lastName, LocalDateTime birthDate);
    List<EmployeeComplementaryTO> getAllEmployees();
    EmployeeAddressTO getEmployeeAdressByIdUser(Long idUser);
    List<SocialNetworkTO> getSocialNetworkByIdUser(Long idUser);
    ContratingDataTO  getContratingDataByIdUser(Long idUser);
    HistoryEmployeeTO  getEmployeeHistoryByIdUser(Long idUser);
    List<CompensationPackageTO>  getEmployeeCompensationByIdUser(Long idUser);
    AsignationDataTO  getEmployeeAsignationByIdUser(Long idUser);
    UserDO AddUser(UserTO user);
    EmployeeComplementaryTO getUserRegisterSico(String name, String lastName, LocalDateTime birthDate);
    List<EmployeeComplementaryTO> getUserData(String curp, String cliente, String proyecto);
    List<EmployeesClientProjectTO> getAllUserData(String email,String curp, String client, String project);
    void saveOrUpdateUser(UserTO user);
    EmployeeComplementaryTO getUserRegisterById(Long id);
    MycvTO saveOrUpdateMyCv(MycvTO mycv);
    MycvTO getCvByEmail(String email);
    List<TabUserTO> getTabUser(Long idUser);
    List<PonderationMobileTO> getPonderationSection( Long idUser);
    MycvTO getCvByIdUser(Long idUser);
    List<ClientTO> getClient();
    List<ProjectTO> getProject(Long idClient);
    void saveOrUpdateEmployeeComplementaryMovil(EmployeePersonalTO  personal);
}
