package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.model.EmployeeShiftDO;
import mx.com.axity.model.ShiftDO;
import mx.com.axity.persistence.EmployeeShiftDAO;
import mx.com.axity.persistence.ShiftDAO;
import mx.com.axity.services.IShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ShiftServiceImpl implements IShiftService {

    @Autowired
    private ShiftDAO shiftDAO;

    @Autowired
    private EmployeeShiftDAO employeeShiftDAO;

    @Override
    public ShiftDO save(ShiftDO shift) {
        return shiftDAO.save(shift);
    }

    @Override
    public Optional<ShiftDO> findById(Long id, String tenantId) {
        return shiftDAO.findByIdShiftAndTenantId(id, tenantId);
    }

    @Override
    public List<ShiftDO> findAllByTenant(String tenantId) {
        return shiftDAO.findAllByTenantId(tenantId);
    }

    @Override
    public EmployeeShiftDO assignToEmployee(EmployeeShiftDO employeeShift) {
        // Close previous active assignment if exists
        employeeShiftDAO.findByIdEmployeeAndTenantIdAndDtEndDateIsNull(
                employeeShift.getIdEmployee(), employeeShift.getTenantId())
            .ifPresent(prev -> {
                prev.setDtEndDate(LocalDate.now());
                employeeShiftDAO.save(prev);
            });
        return employeeShiftDAO.save(employeeShift);
    }

    @Override
    public Optional<ShiftDO> getCurrentShift(Long employeeId, String tenantId) {
        return employeeShiftDAO.findByIdEmployeeAndTenantIdAndDtEndDateIsNull(employeeId, tenantId)
            .flatMap(es -> shiftDAO.findByIdShiftAndTenantId(es.getIdShift(), tenantId));
    }
}
