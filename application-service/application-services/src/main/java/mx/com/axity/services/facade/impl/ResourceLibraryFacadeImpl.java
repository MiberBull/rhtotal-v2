package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.ResourceAckTO;
import mx.com.axity.commons.to.ResourceCategoryTO;
import mx.com.axity.commons.to.ResourceDocumentTO;
import mx.com.axity.model.ResourceAckDO;
import mx.com.axity.model.ResourceCategoryDO;
import mx.com.axity.model.ResourceDocumentDO;
import mx.com.axity.persistence.ResourceAckDAO;
import mx.com.axity.persistence.ResourceCategoryDAO;
import mx.com.axity.persistence.ResourceDocumentDAO;
import mx.com.axity.services.facade.IResourceLibraryFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResourceLibraryFacadeImpl implements IResourceLibraryFacade {

    @Autowired
    private ResourceCategoryDAO categoryDAO;

    @Autowired
    private ResourceDocumentDAO documentDAO;

    @Autowired
    private ResourceAckDAO ackDAO;

    @Override
    public List<ResourceCategoryTO> getCategories(String tenantId) {
        return categoryDAO.findActiveByTenant(tenantId).stream()
                .map(this::toTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResourceCategoryTO createCategory(ResourceCategoryTO to) {
        try {
            ResourceCategoryDO entity = new ResourceCategoryDO();
            entity.setTenantId(to.getTenantId());
            entity.setName(to.getName());
            entity.setDescription(to.getDescription());
            entity.setIcon(to.getIcon() != null ? to.getIcon() : "folder");
            entity.setActive(true);
            entity.setCreationDate(LocalDateTime.now());
            return toTO(categoryDAO.save(entity));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<ResourceDocumentTO> getAllDocuments(String tenantId) {
        return documentDAO.findAllActiveByTenant(tenantId).stream()
                .map(this::toTOWithoutContent)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResourceDocumentTO> getVisibleDocuments(String tenantId, Long idClient) {
        return documentDAO.findVisibleForEmployee(tenantId, idClient).stream()
                .map(this::toTOWithoutContent)
                .collect(Collectors.toList());
    }

    @Override
    public ResourceDocumentTO getDocumentById(Long idDocument) {
        ResourceDocumentDO entity = documentDAO.findById(idDocument)
                .orElseThrow(() -> new BusinessException("Documento no encontrado: " + idDocument, null));
        return toTO(entity);
    }

    @Override
    public ResourceDocumentTO createDocument(ResourceDocumentTO to) {
        try {
            ResourceCategoryDO category = categoryDAO.findById(to.getIdCategory())
                    .orElseThrow(() -> new BusinessException("Categoría no encontrada: " + to.getIdCategory(), null));

            ResourceDocumentDO entity = new ResourceDocumentDO();
            mapToEntity(to, entity, category);
            entity.setActive(true);
            entity.setCreationDate(LocalDateTime.now());
            entity.setPublicationDate(to.getPublicationDate() != null ? to.getPublicationDate() : LocalDateTime.now());
            return toTOWithoutContent(documentDAO.save(entity));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public ResourceDocumentTO updateDocument(Long idDocument, ResourceDocumentTO to) {
        try {
            ResourceDocumentDO entity = documentDAO.findById(idDocument)
                    .orElseThrow(() -> new BusinessException("Documento no encontrado: " + idDocument, null));

            ResourceCategoryDO category = categoryDAO.findById(to.getIdCategory())
                    .orElseThrow(() -> new BusinessException("Categoría no encontrada: " + to.getIdCategory(), null));

            mapToEntity(to, entity, category);
            entity.setModificationDate(LocalDateTime.now());

            if (to.getFileContent() != null && !to.getFileContent().isBlank()) {
                entity.setFileContent(to.getFileContent());
                entity.setFileName(to.getFileName());
                entity.setMimeType(to.getMimeType());
            }

            return toTOWithoutContent(documentDAO.save(entity));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteDocument(Long idDocument) {
        try {
            ResourceDocumentDO entity = documentDAO.findById(idDocument)
                    .orElseThrow(() -> new BusinessException("Documento no encontrado: " + idDocument, null));
            entity.setActive(false);
            entity.setModificationDate(LocalDateTime.now());
            documentDAO.save(entity);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public ResourceAckTO acknowledgeDocument(Long idDocument, ResourceAckTO to) {
        try {
            // Evitar duplicados
            ackDAO.findByDocumentAndEmployee(idDocument, to.getIdEmployee())
                    .ifPresent(existing -> {
                        throw new BusinessException("El empleado ya confirmó este documento", null);
                    });

            ResourceDocumentDO document = documentDAO.findById(idDocument)
                    .orElseThrow(() -> new BusinessException("Documento no encontrado: " + idDocument, null));

            ResourceAckDO entity = new ResourceAckDO();
            entity.setTenantId(to.getTenantId());
            entity.setDocument(document);
            entity.setIdEmployee(to.getIdEmployee());
            entity.setEmployeeName(to.getEmployeeName());
            entity.setAcknowledgedAt(LocalDateTime.now());

            return toTO(ackDAO.save(entity));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<ResourceAckTO> getAcknowledgements(Long idDocument) {
        return ackDAO.findByDocument(idDocument).stream()
                .map(this::toTO)
                .collect(Collectors.toList());
    }

    // --- Mappers ---

    private void mapToEntity(ResourceDocumentTO to, ResourceDocumentDO entity, ResourceCategoryDO category) {
        entity.setTenantId(to.getTenantId());
        entity.setCategory(category);
        entity.setTitle(to.getTitle());
        entity.setDescription(to.getDescription());
        entity.setVersion(to.getVersion() != null ? to.getVersion() : "1.0");
        entity.setVisibility(to.getVisibility() != null ? to.getVisibility() : "GENERAL");
        entity.setIdClient(to.getIdClient());
        entity.setRequiresAck(to.getRequiresAck() != null ? to.getRequiresAck() : false);
        entity.setExpiryDate(to.getExpiryDate());
        entity.setPublishedBy(to.getPublishedBy());
    }

    private ResourceCategoryTO toTO(ResourceCategoryDO entity) {
        ResourceCategoryTO to = new ResourceCategoryTO();
        to.setIdCategory(entity.getIdCategory());
        to.setTenantId(entity.getTenantId());
        to.setName(entity.getName());
        to.setDescription(entity.getDescription());
        to.setIcon(entity.getIcon());
        to.setActive(entity.getActive());
        to.setCreationDate(entity.getCreationDate());
        return to;
    }

    private ResourceDocumentTO toTO(ResourceDocumentDO entity) {
        ResourceDocumentTO to = toTOWithoutContent(entity);
        to.setFileContent(entity.getFileContent());
        return to;
    }

    private ResourceDocumentTO toTOWithoutContent(ResourceDocumentDO entity) {
        ResourceDocumentTO to = new ResourceDocumentTO();
        to.setIdDocument(entity.getIdDocument());
        to.setTenantId(entity.getTenantId());
        if (entity.getCategory() != null) {
            to.setIdCategory(entity.getCategory().getIdCategory());
            to.setCategoryName(entity.getCategory().getName());
            to.setCategoryIcon(entity.getCategory().getIcon());
        }
        to.setTitle(entity.getTitle());
        to.setDescription(entity.getDescription());
        to.setFileName(entity.getFileName());
        to.setMimeType(entity.getMimeType());
        to.setVersion(entity.getVersion());
        to.setVisibility(entity.getVisibility());
        to.setIdClient(entity.getIdClient());
        to.setRequiresAck(entity.getRequiresAck());
        to.setActive(entity.getActive());
        to.setPublicationDate(entity.getPublicationDate());
        to.setExpiryDate(entity.getExpiryDate());
        to.setPublishedBy(entity.getPublishedBy());
        to.setCreationDate(entity.getCreationDate());
        to.setModificationDate(entity.getModificationDate());
        return to;
    }

    private ResourceAckTO toTO(ResourceAckDO entity) {
        ResourceAckTO to = new ResourceAckTO();
        to.setIdAck(entity.getIdAck());
        to.setTenantId(entity.getTenantId());
        if (entity.getDocument() != null) {
            to.setIdDocument(entity.getDocument().getIdDocument());
        }
        to.setIdEmployee(entity.getIdEmployee());
        to.setEmployeeName(entity.getEmployeeName());
        to.setAcknowledgedAt(entity.getAcknowledgedAt());
        return to;
    }
}
