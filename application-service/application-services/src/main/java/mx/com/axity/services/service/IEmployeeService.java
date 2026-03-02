package mx.com.axity.services.service;

import mx.com.axity.commons.to.*;
import mx.com.axity.model.ContratingDataDO;
import mx.com.axity.model.EmployeeComplementaryDO;
import mx.com.axity.model.EmployeeDO;
import mx.com.axity.model.UserDO;

import java.time.LocalDateTime;
import java.util.List;

public interface IEmployeeService {

    //////
    List<CivilStatusTO> getCivilStatus();

    ////
    EmployeeAddressTO getEmployeeAdressByIdUser(Long idUser);

    ///////
    ContratingDataTO  getContratingDataByIdUser(Long idUser);

    //////
    List<CompensationPackageTO> getEmployeeCompensationByIdUser(Long idUser);

    ////
    AsignationDataTO  getEmployeeAsignationByIdUser(Long idUser);

    /////////
    List<EmployeeComplementaryTO> getUserData();

}
