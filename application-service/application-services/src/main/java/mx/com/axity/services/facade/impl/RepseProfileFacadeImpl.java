package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.RepseClientTO;
import mx.com.axity.commons.to.RepseDocumentTO;
import mx.com.axity.commons.to.RepseProfileTO;
import mx.com.axity.services.facade.IRepseProfileFacade;
import mx.com.axity.services.service.IRepseClientService;
import mx.com.axity.services.service.IRepseDocumentService;
import mx.com.axity.services.service.IRepseProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class RepseProfileFacadeImpl implements IRepseProfileFacade {

    @Autowired
    IRepseProfileService repseProfileService;

    @Autowired
    IRepseClientService repseClientService;

    @Autowired
    IRepseDocumentService repseDocumentService;

    @Override
    public RepseProfileTO getProfile(String tenantId) {
        try {
            return repseProfileService.findByTenantId(tenantId)
                    .orElseThrow(() -> new RuntimeException("Perfil REPSE no encontrado para tenant: " + tenantId));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public RepseProfileTO saveProfile(RepseProfileTO to, String tenantId) {
        try {
            return repseProfileService.save(to, tenantId);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public RepseProfileTO updateProfile(RepseProfileTO to, String tenantId) {
        try {
            return repseProfileService.update(to, tenantId);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<RepseClientTO> getClients(String tenantId) {
        try {
            return repseClientService.findAllByTenant(tenantId);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public RepseClientTO getClient(Long id, String tenantId) {
        try {
            return repseClientService.findById(id, tenantId)
                    .orElseThrow(() -> new RuntimeException("Cliente REPSE no encontrado: " + id));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public RepseClientTO saveClient(RepseClientTO to, String tenantId) {
        try {
            return repseClientService.save(to, tenantId);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public RepseClientTO updateClient(RepseClientTO to, String tenantId) {
        try {
            return repseClientService.update(to, tenantId);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<RepseDocumentTO> getDocuments(Long idRepseClient, String period, String tenantId) {
        try {
            return repseDocumentService.findByClientAndPeriod(idRepseClient, period, tenantId);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public RepseDocumentTO uploadDocument(RepseDocumentTO to, String tenantId) {
        try {
            return repseDocumentService.upload(to, tenantId);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public RepseDocumentTO validateDocument(Long idRepseDoc, String validatedBy, String tenantId) {
        try {
            return repseDocumentService.validate(idRepseDoc, validatedBy, tenantId);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public RepseDocumentTO rejectDocument(Long idRepseDoc, String rejectionReason, String tenantId) {
        try {
            return repseDocumentService.reject(idRepseDoc, rejectionReason, tenantId);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
}
