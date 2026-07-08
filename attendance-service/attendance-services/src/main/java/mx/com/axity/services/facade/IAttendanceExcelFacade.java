package mx.com.axity.services.facade;

import java.time.LocalDate;

public interface IAttendanceExcelFacade {

    byte[] exportExcel(Long projectId, LocalDate from, LocalDate to, String tenantId);
}
