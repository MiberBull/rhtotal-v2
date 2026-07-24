package mx.com.axity.services.facade;

import mx.com.axity.commons.to.ResourceAckTO;
import mx.com.axity.commons.to.ResourceCategoryTO;
import mx.com.axity.commons.to.ResourceDocumentTO;

import java.util.List;

public interface IResourceLibraryFacade {

    List<ResourceCategoryTO> getCategories(String tenantId);

    ResourceCategoryTO createCategory(ResourceCategoryTO category);

    List<ResourceDocumentTO> getAllDocuments(String tenantId);

    List<ResourceDocumentTO> getVisibleDocuments(String tenantId, Long idClient);

    ResourceDocumentTO getDocumentById(Long idDocument);

    ResourceDocumentTO createDocument(ResourceDocumentTO document);

    ResourceDocumentTO updateDocument(Long idDocument, ResourceDocumentTO document);

    void deleteDocument(Long idDocument);

    ResourceAckTO acknowledgeDocument(Long idDocument, ResourceAckTO ack);

    List<ResourceAckTO> getAcknowledgements(Long idDocument);
}
