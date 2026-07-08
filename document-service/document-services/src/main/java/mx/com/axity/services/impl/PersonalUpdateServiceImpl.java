package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.PersonalUpdateRequestDO;
import mx.com.axity.persistence.PersonalUpdateRequestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PersonalUpdateServiceImpl {

    @Autowired private PersonalUpdateRequestDAO personalUpdateRequestDAO;

    @Transactional
    public PersonalUpdateRequestDO request(PersonalUpdateRequestDO updateRequest) {
        updateRequest.setDsStatus(Constants.UPDATE_PENDIENTE);
        return personalUpdateRequestDAO.save(updateRequest);
    }

    public PersonalUpdateRequestDO findById(Long id) {
        return personalUpdateRequestDAO.findById(id)
            .orElseThrow(() -> new BusinessException(404, "Solicitud no encontrada: " + id));
    }

    public List<PersonalUpdateRequestDO> findByEmployee(Long idEmployee, String tenantId) {
        return personalUpdateRequestDAO.findAllByIdEmployeeAndTenantId(idEmployee, tenantId);
    }

    public List<PersonalUpdateRequestDO> findPending(String tenantId) {
        return personalUpdateRequestDAO.findAllByTenantIdAndDsStatus(tenantId, Constants.UPDATE_PENDIENTE);
    }

    @Transactional
    public PersonalUpdateRequestDO approve(Long id, String approvedBy) {
        PersonalUpdateRequestDO req = findById(id);
        req.setDsStatus(Constants.UPDATE_APROBADO);
        req.setDsApprovedBy(approvedBy);
        req.setDtApprovedDate(LocalDateTime.now());
        return personalUpdateRequestDAO.save(req);
    }

    @Transactional
    public PersonalUpdateRequestDO reject(Long id, String approvedBy, String reason) {
        PersonalUpdateRequestDO req = findById(id);
        req.setDsStatus(Constants.UPDATE_RECHAZADO);
        req.setDsApprovedBy(approvedBy);
        req.setDsRejectionReason(reason);
        req.setDtApprovedDate(LocalDateTime.now());
        return personalUpdateRequestDAO.save(req);
    }
}
