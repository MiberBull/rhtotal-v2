package mx.com.axity.services.facade;

import mx.com.axity.commons.to.EmployeeShiftTO;
import mx.com.axity.commons.to.ShiftTO;

import java.util.List;

public interface IShiftFacade {

    ShiftTO createShift(ShiftTO shiftTO, String tenantId);

    ShiftTO updateShift(ShiftTO shiftTO, String tenantId);

    ShiftTO getShift(Long id, String tenantId);

    List<ShiftTO> getAllShifts(String tenantId);

    EmployeeShiftTO assignShiftToEmployee(EmployeeShiftTO employeeShiftTO, String tenantId);

    ShiftTO getEmployeeCurrentShift(Long employeeId, String tenantId);
}
