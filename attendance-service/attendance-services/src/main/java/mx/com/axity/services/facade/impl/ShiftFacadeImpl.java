package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.EmployeeShiftTO;
import mx.com.axity.commons.to.ShiftTO;
import mx.com.axity.model.EmployeeShiftDO;
import mx.com.axity.model.ShiftDO;
import mx.com.axity.services.IShiftService;
import mx.com.axity.services.facade.IShiftFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShiftFacadeImpl implements IShiftFacade {

    @Autowired
    private IShiftService shiftService;

    @Override
    public ShiftTO createShift(ShiftTO shiftTO, String tenantId) {
        ShiftDO do_ = toModel(shiftTO);
        do_.setTenantId(tenantId);
        return toTO(shiftService.save(do_));
    }

    @Override
    public ShiftTO updateShift(ShiftTO shiftTO, String tenantId) {
        ShiftDO existing = shiftService.findById(shiftTO.getIdShift(), tenantId)
            .orElseThrow(() -> new BusinessException(404, "Shift not found: " + shiftTO.getIdShift()));
        existing.setDsName(shiftTO.getDsName());
        existing.setDsType(shiftTO.getDsType());
        existing.setDtStartTime(shiftTO.getDtStartTime());
        existing.setDtEndTime(shiftTO.getDtEndTime());
        if (shiftTO.getNbToleranceMinutes() != null) existing.setNbToleranceMinutes(shiftTO.getNbToleranceMinutes());
        if (shiftTO.getFgActive() != null) existing.setFgActive(shiftTO.getFgActive());
        return toTO(shiftService.save(existing));
    }

    @Override
    public ShiftTO getShift(Long id, String tenantId) {
        return shiftService.findById(id, tenantId)
            .map(this::toTO)
            .orElseThrow(() -> new BusinessException(404, "Shift not found: " + id));
    }

    @Override
    public List<ShiftTO> getAllShifts(String tenantId) {
        return shiftService.findAllByTenant(tenantId).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    @Override
    public EmployeeShiftTO assignShiftToEmployee(EmployeeShiftTO employeeShiftTO, String tenantId) {
        EmployeeShiftDO do_ = toEmployeeModel(employeeShiftTO);
        do_.setTenantId(tenantId);
        EmployeeShiftDO saved = shiftService.assignToEmployee(do_);
        return toEmployeeTO(saved);
    }

    @Override
    public ShiftTO getEmployeeCurrentShift(Long employeeId, String tenantId) {
        return shiftService.getCurrentShift(employeeId, tenantId)
            .map(this::toTO)
            .orElseThrow(() -> new BusinessException(404, "No active shift for employee: " + employeeId));
    }

    private ShiftTO toTO(ShiftDO d) {
        ShiftTO to = new ShiftTO();
        to.setIdShift(d.getIdShift());
        to.setTenantId(d.getTenantId());
        to.setDsName(d.getDsName());
        to.setDsType(d.getDsType());
        to.setDtStartTime(d.getDtStartTime());
        to.setDtEndTime(d.getDtEndTime());
        to.setNbToleranceMinutes(d.getNbToleranceMinutes());
        to.setFgActive(d.getFgActive());
        return to;
    }

    private ShiftDO toModel(ShiftTO to) {
        ShiftDO d = new ShiftDO();
        d.setDsName(to.getDsName());
        d.setDsType(to.getDsType());
        d.setDtStartTime(to.getDtStartTime());
        d.setDtEndTime(to.getDtEndTime());
        if (to.getNbToleranceMinutes() != null) d.setNbToleranceMinutes(to.getNbToleranceMinutes());
        return d;
    }

    private EmployeeShiftTO toEmployeeTO(EmployeeShiftDO d) {
        EmployeeShiftTO to = new EmployeeShiftTO();
        to.setIdEmployeeShift(d.getIdEmployeeShift());
        to.setTenantId(d.getTenantId());
        to.setIdEmployee(d.getIdEmployee());
        to.setIdShift(d.getIdShift());
        to.setDtEffectiveDate(d.getDtEffectiveDate());
        to.setDtEndDate(d.getDtEndDate());
        to.setFgActive(d.getFgActive());
        return to;
    }

    private EmployeeShiftDO toEmployeeModel(EmployeeShiftTO to) {
        EmployeeShiftDO d = new EmployeeShiftDO();
        d.setIdEmployee(to.getIdEmployee());
        d.setIdShift(to.getIdShift());
        d.setDtEffectiveDate(to.getDtEffectiveDate());
        d.setDtEndDate(to.getDtEndDate());
        return d;
    }
}
