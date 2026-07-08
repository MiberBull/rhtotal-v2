package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.IncidentDO;
import mx.com.axity.persistence.IncidentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidentServiceImpl {

    @Autowired private IncidentDAO incidentDAO;

    @Transactional
    public IncidentDO save(IncidentDO incident) {
        return incidentDAO.save(incident);
    }

    public IncidentDO findById(Long id) {
        return incidentDAO.findById(id)
            .orElseThrow(() -> new BusinessException(404, "Incidencia no encontrada: " + id));
    }

    public List<IncidentDO> findByEmployee(Long idEmployee, String tenantId) {
        return incidentDAO.findAllByIdEmployeeAndTenantId(idEmployee, tenantId);
    }

    public List<IncidentDO> findByPeriod(String tenantId, LocalDate from, LocalDate to) {
        return incidentDAO.findAllByTenantIdAndDtIncidentDateBetween(tenantId, from, to);
    }

    @Transactional
    public IncidentDO validate(Long id, String approvedBy) {
        IncidentDO inc = findById(id);
        inc.setDsStatus(Constants.INC_VALIDADA);
        inc.setDsApprovedBy(approvedBy);
        inc.setDtApprovedDate(LocalDateTime.now());
        return incidentDAO.save(inc);
    }

    @Transactional
    public IncidentDO reject(Long id, String approvedBy) {
        IncidentDO inc = findById(id);
        inc.setDsStatus(Constants.INC_RECHAZADA);
        inc.setDsApprovedBy(approvedBy);
        inc.setDtApprovedDate(LocalDateTime.now());
        return incidentDAO.save(inc);
    }
}
