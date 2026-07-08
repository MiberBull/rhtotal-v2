package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.RepseDocumentTO;
import mx.com.axity.model.RepseDocumentDO;
import mx.com.axity.persistence.RepseDocumentDAO;
import mx.com.axity.services.service.IRepseDocumentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RepseDocumentServiceImpl implements IRepseDocumentService {

    @Autowired
    RepseDocumentDAO repseDocumentDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<RepseDocumentTO> findByClientAndPeriod(Long idRepseClient, String period, String tenantId) {
        return (List<RepseDocumentTO>) modelMapper.map(
                repseDocumentDAO.findAllByIdRepseClientAndPeriodAndTenantId(idRepseClient, period, tenantId),
                new TypeToken<List<RepseDocumentTO>>(){}.getType()
        );
    }

    @Override
    public RepseDocumentTO upload(RepseDocumentTO to, String tenantId) {
        to.setTenantId(tenantId);
        to.setStatus("CARGADO");
        to.setActive(Boolean.TRUE);
        var saved = repseDocumentDAO.save(modelMapper.map(to, RepseDocumentDO.class));
        return modelMapper.map(saved, RepseDocumentTO.class);
    }

    @Override
    public RepseDocumentTO validate(Long idRepseDoc, String validatedBy, String tenantId) {
        var doc = repseDocumentDAO.findById(idRepseDoc)
                .filter(d -> tenantId.equals(d.getTenantId()))
                .orElseThrow(() -> new RuntimeException("Documento REPSE no encontrado: " + idRepseDoc));
        doc.setStatus("VALIDADO");
        doc.setValidatedBy(validatedBy);
        doc.setValidatedDate(LocalDateTime.now());
        return modelMapper.map(repseDocumentDAO.save(doc), RepseDocumentTO.class);
    }

    @Override
    public RepseDocumentTO reject(Long idRepseDoc, String rejectionReason, String tenantId) {
        var doc = repseDocumentDAO.findById(idRepseDoc)
                .filter(d -> tenantId.equals(d.getTenantId()))
                .orElseThrow(() -> new RuntimeException("Documento REPSE no encontrado: " + idRepseDoc));
        doc.setStatus("RECHAZADO");
        doc.setRejectionReason(rejectionReason);
        return modelMapper.map(repseDocumentDAO.save(doc), RepseDocumentTO.class);
    }
}
