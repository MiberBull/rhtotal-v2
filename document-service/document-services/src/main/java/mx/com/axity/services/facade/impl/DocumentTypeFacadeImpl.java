package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.to.DocumentTypeTO;
import mx.com.axity.model.DocumentTypeDO;
import mx.com.axity.services.impl.DocumentTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DocumentTypeFacadeImpl {

    @Autowired private DocumentTypeServiceImpl documentTypeService;

    public DocumentTypeTO create(DocumentTypeTO documentTypeTO, String tenantId) {
        DocumentTypeDO d = toDO(documentTypeTO);
        d.setTenantId(tenantId);
        return toTO(documentTypeService.save(d));
    }

    public DocumentTypeTO getById(Long id) {
        return toTO(documentTypeService.findById(id));
    }

    public List<DocumentTypeTO> getAll(String tenantId) {
        return documentTypeService.findAll(tenantId).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public List<DocumentTypeTO> getRequired(String tenantId) {
        return documentTypeService.findRequired(tenantId).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public void delete(Long id) {
        documentTypeService.delete(id);
    }

    private DocumentTypeTO toTO(DocumentTypeDO d) {
        DocumentTypeTO to = new DocumentTypeTO();
        to.setIdDocumentType(d.getIdDocumentType()); to.setTenantId(d.getTenantId());
        to.setDsCode(d.getDsCode()); to.setDsName(d.getDsName());
        to.setDsDescription(d.getDsDescription());
        to.setFgRequiredOnboarding(d.getFgRequiredOnboarding());
        to.setFgEmployeeUploadable(d.getFgEmployeeUploadable());
        to.setFgActive(d.getFgActive());
        return to;
    }

    private DocumentTypeDO toDO(DocumentTypeTO to) {
        DocumentTypeDO d = new DocumentTypeDO();
        d.setDsCode(to.getDsCode()); d.setDsName(to.getDsName());
        d.setDsDescription(to.getDsDescription());
        d.setFgRequiredOnboarding(to.getFgRequiredOnboarding() != null ? to.getFgRequiredOnboarding() : false);
        d.setFgEmployeeUploadable(to.getFgEmployeeUploadable() != null ? to.getFgEmployeeUploadable() : true);
        return d;
    }
}
