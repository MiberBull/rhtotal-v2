package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.model.EmployeeDocumentDO;
import mx.com.axity.persistence.EmployeeDocumentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeDocumentServiceImpl {

    @Autowired private EmployeeDocumentDAO employeeDocumentDAO;

    @Transactional
    public EmployeeDocumentDO upload(EmployeeDocumentDO document) {
        document.setDsStatus(Constants.DOC_PENDIENTE);
        return employeeDocumentDAO.save(document);
    }

    public EmployeeDocumentDO findById(Long id) {
        return employeeDocumentDAO.findById(id)
            .orElseThrow(() -> new BusinessException(404, "Documento no encontrado: " + id));
    }

    public List<EmployeeDocumentDO> findByEmployee(Long idEmployee, String tenantId) {
        return employeeDocumentDAO.findAllByIdEmployeeAndTenantIdAndFgActiveTrue(idEmployee, tenantId);
    }

    public List<EmployeeDocumentDO> findPending(String tenantId) {
        return employeeDocumentDAO.findAllByTenantIdAndDsStatus(tenantId, Constants.DOC_PENDIENTE);
    }

    @Transactional
    public EmployeeDocumentDO validate(Long id, String validatedBy) {
        EmployeeDocumentDO doc = findById(id);
        doc.setDsStatus(Constants.DOC_VALIDADO);
        doc.setDsValidatedBy(validatedBy);
        doc.setDtValidatedDate(LocalDateTime.now());
        return employeeDocumentDAO.save(doc);
    }

    @Transactional
    public EmployeeDocumentDO reject(Long id, String validatedBy, String reason) {
        EmployeeDocumentDO doc = findById(id);
        doc.setDsStatus(Constants.DOC_RECHAZADO);
        doc.setDsValidatedBy(validatedBy);
        doc.setDsRejectionReason(reason);
        doc.setDtValidatedDate(LocalDateTime.now());
        return employeeDocumentDAO.save(doc);
    }

    @Transactional
    public void delete(Long id) {
        EmployeeDocumentDO doc = findById(id);
        doc.setFgActive(false);
        employeeDocumentDAO.save(doc);
    }
}
