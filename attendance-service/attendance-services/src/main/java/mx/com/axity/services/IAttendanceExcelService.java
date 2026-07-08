package mx.com.axity.services;

import java.time.LocalDate;

public interface IAttendanceExcelService {

    byte[] exportExcel(Long projectId, LocalDate from, LocalDate to, String tenantId);
}
