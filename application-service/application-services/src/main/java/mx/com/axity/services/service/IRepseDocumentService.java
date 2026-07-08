package mx.com.axity.services.service;

import mx.com.axity.commons.to.RepseDocumentTO;

import java.util.List;

public interface IRepseDocumentService {
    List<RepseDocumentTO> findByClientAndPeriod(Long idRepseClient, String period, String tenantId);
    RepseDocumentTO upload(RepseDocumentTO to, String tenantId);
    RepseDocumentTO validate(Long idRepseDoc, String validatedBy, String tenantId);
    RepseDocumentTO reject(Long idRepseDoc, String rejectionReason, String tenantId);
}
