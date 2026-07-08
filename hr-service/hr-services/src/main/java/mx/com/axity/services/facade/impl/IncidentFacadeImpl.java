package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.to.IncidentTO;
import mx.com.axity.model.IncidentDO;
import mx.com.axity.services.impl.IncidentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IncidentFacadeImpl {

    @Autowired private IncidentServiceImpl incidentService;

    public IncidentTO create(IncidentTO incidentTO, String tenantId) {
        IncidentDO d = toDO(incidentTO);
        d.setTenantId(tenantId);
        return toTO(incidentService.save(d));
    }

    public IncidentTO getById(Long id) {
        return toTO(incidentService.findById(id));
    }

    public List<IncidentTO> getByEmployee(Long idEmployee, String tenantId) {
        return incidentService.findByEmployee(idEmployee, tenantId).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public List<IncidentTO> getByPeriod(String tenantId, LocalDate from, LocalDate to) {
        return incidentService.findByPeriod(tenantId, from, to).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public IncidentTO validate(Long id, String approvedBy) {
        return toTO(incidentService.validate(id, approvedBy));
    }

    public IncidentTO reject(Long id, String approvedBy) {
        return toTO(incidentService.reject(id, approvedBy));
    }

    private IncidentTO toTO(IncidentDO d) {
        IncidentTO to = new IncidentTO();
        to.setIdIncident(d.getIdIncident()); to.setTenantId(d.getTenantId());
        to.setIdEmployee(d.getIdEmployee()); to.setDsType(d.getDsType());
        to.setDtIncidentDate(d.getDtIncidentDate()); to.setDtEndDate(d.getDtEndDate());
        to.setDsNotes(d.getDsNotes()); to.setDsStatus(d.getDsStatus());
        to.setDsApprovedBy(d.getDsApprovedBy()); to.setDtApprovedDate(d.getDtApprovedDate());
        to.setDsDocumentRef(d.getDsDocumentRef()); to.setFgActive(d.getFgActive());
        return to;
    }

    private IncidentDO toDO(IncidentTO to) {
        IncidentDO d = new IncidentDO();
        d.setIdEmployee(to.getIdEmployee()); d.setDsType(to.getDsType());
        d.setDtIncidentDate(to.getDtIncidentDate()); d.setDtEndDate(to.getDtEndDate());
        d.setDsNotes(to.getDsNotes()); d.setDsDocumentRef(to.getDsDocumentRef());
        return d;
    }
}
