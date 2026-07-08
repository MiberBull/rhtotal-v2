package mx.com.axity.services.facade;

import mx.com.axity.commons.to.RepseClientTO;
import mx.com.axity.commons.to.RepseDocumentTO;
import mx.com.axity.commons.to.RepseProfileTO;

import java.util.List;

public interface IRepseProfileFacade {
    RepseProfileTO getProfile(String tenantId);
    RepseProfileTO saveProfile(RepseProfileTO to, String tenantId);
    RepseProfileTO updateProfile(RepseProfileTO to, String tenantId);
    List<RepseClientTO> getClients(String tenantId);
    RepseClientTO getClient(Long id, String tenantId);
    RepseClientTO saveClient(RepseClientTO to, String tenantId);
    RepseClientTO updateClient(RepseClientTO to, String tenantId);
    List<RepseDocumentTO> getDocuments(Long idRepseClient, String period, String tenantId);
    RepseDocumentTO uploadDocument(RepseDocumentTO to, String tenantId);
    RepseDocumentTO validateDocument(Long idRepseDoc, String validatedBy, String tenantId);
    RepseDocumentTO rejectDocument(Long idRepseDoc, String rejectionReason, String tenantId);
}
