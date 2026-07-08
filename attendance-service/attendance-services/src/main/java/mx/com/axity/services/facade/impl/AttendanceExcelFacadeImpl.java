package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.services.IAttendanceExcelService;
import mx.com.axity.services.facade.IAttendanceExcelFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AttendanceExcelFacadeImpl implements IAttendanceExcelFacade {

    @Autowired
    private IAttendanceExcelService attendanceExcelService;

    @Override
    public byte[] exportExcel(Long projectId, LocalDate from, LocalDate to, String tenantId) {
        try {
            return attendanceExcelService.exportExcel(projectId, from, to, tenantId);
        } catch (Exception e) {
            throw new BusinessException(500, e.getMessage());
        }
    }
}
