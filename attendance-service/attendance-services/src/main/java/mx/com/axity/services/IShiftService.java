package mx.com.axity.services;

import mx.com.axity.model.EmployeeShiftDO;
import mx.com.axity.model.ShiftDO;

import java.util.List;
import java.util.Optional;

public interface IShiftService {

    ShiftDO save(ShiftDO shift);

    Optional<ShiftDO> findById(Long id, String tenantId);

    List<ShiftDO> findAllByTenant(String tenantId);

    EmployeeShiftDO assignToEmployee(EmployeeShiftDO employeeShift);

    Optional<ShiftDO> getCurrentShift(Long employeeId, String tenantId);
}
