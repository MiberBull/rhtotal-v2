package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.model.DocumentTypeDO;
import mx.com.axity.persistence.DocumentTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentTypeServiceImpl {

    @Autowired private DocumentTypeDAO documentTypeDAO;

    @Transactional
    public DocumentTypeDO save(DocumentTypeDO documentType) {
        return documentTypeDAO.save(documentType);
    }

    public DocumentTypeDO findById(Long id) {
        return documentTypeDAO.findById(id)
            .orElseThrow(() -> new BusinessException(404, "Tipo de documento no encontrado: " + id));
    }

    public List<DocumentTypeDO> findAll(String tenantId) {
        return documentTypeDAO.findAllByTenantIdAndFgActiveTrue(tenantId);
    }

    public List<DocumentTypeDO> findRequired(String tenantId) {
        return documentTypeDAO.findAllByTenantIdAndFgRequiredOnboardingTrueAndFgActiveTrue(tenantId);
    }

    public DocumentTypeDO findByCode(String code, String tenantId) {
        return documentTypeDAO.findByDsCodeAndTenantId(code, tenantId)
            .orElseThrow(() -> new BusinessException(404, "Tipo de documento no encontrado: " + code));
    }

    @Transactional
    public void delete(Long id) {
        DocumentTypeDO dt = findById(id);
        dt.setFgActive(false);
        documentTypeDAO.save(dt);
    }
}
