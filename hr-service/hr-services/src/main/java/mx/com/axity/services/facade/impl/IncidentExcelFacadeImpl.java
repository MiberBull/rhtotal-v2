package mx.com.axity.services.facade.impl;

import mx.com.axity.services.IIncidentExcelService;
import mx.com.axity.services.facade.IIncidentExcelFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class IncidentExcelFacadeImpl implements IIncidentExcelFacade {

    @Autowired private IIncidentExcelService incidentExcelService;

    @Override
    public byte[] exportExcel(String tenantId, LocalDate from, LocalDate to) {
        return incidentExcelService.exportExcel(tenantId, from, to);
    }
}
