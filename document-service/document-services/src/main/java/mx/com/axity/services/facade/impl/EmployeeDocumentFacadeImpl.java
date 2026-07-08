package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.to.EmployeeDocumentTO;
import mx.com.axity.model.EmployeeDocumentDO;
import mx.com.axity.services.impl.EmployeeDocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeDocumentFacadeImpl {

    @Autowired private EmployeeDocumentServiceImpl employeeDocumentService;

    public EmployeeDocumentTO upload(EmployeeDocumentTO documentTO, String tenantId) {
        EmployeeDocumentDO d = toDO(documentTO);
        d.setTenantId(tenantId);
        return toTO(employeeDocumentService.upload(d));
    }

    public EmployeeDocumentTO getById(Long id) {
        return toTO(employeeDocumentService.findById(id));
    }

    public List<EmployeeDocumentTO> getByEmployee(Long idEmployee, String tenantId) {
        return employeeDocumentService.findByEmployee(idEmployee, tenantId).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public List<EmployeeDocumentTO> getPending(String tenantId) {
        return employeeDocumentService.findPending(tenantId).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public EmployeeDocumentTO validate(Long id, String validatedBy) {
        return toTO(employeeDocumentService.validate(id, validatedBy));
    }

    public EmployeeDocumentTO reject(Long id, String validatedBy, String reason) {
        return toTO(employeeDocumentService.reject(id, validatedBy, reason));
    }

    public void delete(Long id) {
        employeeDocumentService.delete(id);
    }

    private EmployeeDocumentTO toTO(EmployeeDocumentDO d) {
        EmployeeDocumentTO to = new EmployeeDocumentTO();
        to.setIdDocument(d.getIdDocument()); to.setTenantId(d.getTenantId());
        to.setIdEmployee(d.getIdEmployee()); to.setIdDocumentType(d.getIdDocumentType());
        to.setDsFilename(d.getDsFilename()); to.setDsMimeType(d.getDsMimeType());
        to.setDsContent(d.getDsContent()); to.setDsS3Key(d.getDsS3Key());
        to.setDsStatus(d.getDsStatus()); to.setDsRejectionReason(d.getDsRejectionReason());
        to.setDsValidatedBy(d.getDsValidatedBy()); to.setDtValidatedDate(d.getDtValidatedDate());
        to.setDsNotes(d.getDsNotes()); to.setFgActive(d.getFgActive());
        return to;
    }

    private EmployeeDocumentDO toDO(EmployeeDocumentTO to) {
        EmployeeDocumentDO d = new EmployeeDocumentDO();
        d.setIdEmployee(to.getIdEmployee()); d.setIdDocumentType(to.getIdDocumentType());
        d.setDsFilename(to.getDsFilename()); d.setDsMimeType(to.getDsMimeType());
        d.setDsContent(to.getDsContent()); d.setDsNotes(to.getDsNotes());
        return d;
    }
}
