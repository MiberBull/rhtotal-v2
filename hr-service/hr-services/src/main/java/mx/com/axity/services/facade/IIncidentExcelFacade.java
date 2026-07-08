package mx.com.axity.services.facade;

import java.time.LocalDate;

public interface IIncidentExcelFacade {
    byte[] exportExcel(String tenantId, LocalDate from, LocalDate to);
}
