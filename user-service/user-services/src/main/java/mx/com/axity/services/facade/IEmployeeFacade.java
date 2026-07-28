package mx.com.axity.services.facade;

import mx.com.axity.commons.to.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IEmployeeFacade {

    Boolean saveOrUpdateEmployee(EmployeeTO employee);
    EmployeeTO saveOrUpdateEmployeeComplemtaryWeb(EmployeeComplementaryTO employee);
    Boolean saveOrUpdateEmployeeAdress(EmployeeAddressTO employee);
    Boolean saveOrUpdateAsignationData(AsignationDataTO asigancion);
    Boolean saveOrUpdateCompesation(List<CompensationPackageTO> compensation);
    Boolean saveOrUpdateContrating(ContratingDataTO contrating);
    Boolean saveOrUpdateHistory(HistoryEmployeeTO history);
    Boolean saveOrUpdateSocialNetwork(List<SocialNetworkTO> socialNetwork);
    List<CityTO> getCity(Long idState);
    List<StateTO> getState();
    List<CivilStatusTO> getCivilStatus();
    EmployeeTO getEmployeeByIdUser(Long idUser);
    EmployeeComplementaryTO getEmployeeComplementaryByName(String name, String lastName, LocalDateTime birthDate);
    List<EmployeeComplementaryTO> getAllEmployees();
    EmployeeAddressTO getEmployeeAdressByIdUser(Long idUser);
    List<SocialNetworkTO> getSocialNetworkByIdUser(Long idUser);
    ContratingDataTO  getContratingDataByIdUser(Long idUser);
    HistoryEmployeeTO  getEmployeeHistoryByIdUser(Long idUser);
    List<CompensationPackageTO>  getEmployeeCompensationByIdUser(Long idUser);
    AsignationDataTO  getEmployeeAsignationByIdUser(Long idUser);
    EmployeeComplementaryTO getUserRegisterSico(String name, String lastName, String birthDate);
    List<EmployeesClientProjectTO> getEmployeesByCurpClientProject(String getEmployeesByCurpClientProject,String curp, String client, String proyect) throws NoSuchFieldException, NoSuchMethodException;
    EmployeeComplementaryTO getUserRegisterById(Long id);
    void saveOrUpdateUser(UserTO user);
    MycvTO saveOrUpdateMyCv(MycvTO mycv);
    MycvTO getCvByEmail(String email);
    List<TabUserTO> getTabUser(Long idUser);
    List<PonderationMobileTO> getPonderationSection( Long idUser);
    CountRowTO getCountRow(String email,String curp,String client,String project);
    List<EmployeesClientProjectTO> getPageEmployeesByCurpClientProject(int page,String email,String curp, String client, String proyect);
    List<ClientTO> getClient();
    List<ProjectTO> getProjectByIdClient(Long idClient);
    void saveOrUpdateEmployeeComplementaryMovil(EmployeePersonalTO  personal);
    ExcelGenericFormatExportTO sendExcel();

    // --- Sprint 15: Ficha del Colaborador ---
    EmployeeProfileTO getEmployeeProfile(Long idEmployee);
    void createFullEmployee(EmployeeProfileTO profile);
    void updatePersonalData(Long idEmployee, EmployeeTO data, EmployeeComplementaryTO complementary, EmployeeAddressTO address);
    void updateEmploymentData(Long idEmployee, ContratingDataTO contracting);
    void updateAssignmentData(Long idEmployee, AsignationDataTO assignment);
    void saveEmergencyContact(Long idEmployee, EmergencyContactTO emergency);
    void terminateEmployee(Long idEmployee, String reason, LocalDate terminationDate);

}
