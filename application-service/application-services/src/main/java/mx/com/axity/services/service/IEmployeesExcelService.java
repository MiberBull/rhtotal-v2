package mx.com.axity.services.service;

import mx.com.axity.model.EmployeesDataTO;

import java.util.List;

public interface IEmployeesExcelService {
    List<EmployeesDataTO> getEmployeesByCurpClientProject() throws NoSuchFieldException, NoSuchMethodException;
}
