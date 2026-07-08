package mx.com.axity.services;

import java.time.LocalDate;

public interface IIncidentExcelService {
    byte[] exportExcel(String tenantId, LocalDate from, LocalDate to);
}
