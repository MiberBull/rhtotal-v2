package mx.com.axity.services.service.impl;

import mx.com.axity.commons.to.RepseComplianceTO;
import mx.com.axity.model.RepseComplianceDO;
import mx.com.axity.model.RepseDocumentDO;
import mx.com.axity.persistence.RepseClientDAO;
import mx.com.axity.persistence.RepseComplianceDAO;
import mx.com.axity.persistence.RepseDocumentDAO;
import mx.com.axity.services.service.IRepseComplianceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RepseComplianceServiceImpl implements IRepseComplianceService {

    private static final int DOCUMENTS_REQUIRED_DEFAULT = 6;

    @Autowired
    RepseComplianceDAO repseComplianceDAO;

    @Autowired
    RepseDocumentDAO repseDocumentDAO;

    @Autowired
    RepseClientDAO repseClientDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Optional<RepseComplianceTO> findByClientAndPeriod(Long idRepseClient, String period, String tenantId) {
        return repseComplianceDAO.findByIdRepseClientAndPeriodAndTenantId(idRepseClient, period, tenantId)
                .map(this::enrichCompliance);
    }

    @Override
    public List<RepseComplianceTO> findAllByTenantAndPeriod(String tenantId, String period) {
        return repseComplianceDAO.findAllByTenantIdAndPeriod(tenantId, period)
                .stream()
                .map(this::enrichCompliance)
                .collect(Collectors.toList());
    }

    @Override
    public List<RepseComplianceTO> findByTenantAndSemaforo(String tenantId, String semaforo) {
        return repseComplianceDAO.findAllByTenantIdAndSemaforo(tenantId, semaforo)
                .stream()
                .map(this::enrichCompliance)
                .collect(Collectors.toList());
    }

    @Override
    public RepseComplianceTO recalculate(Long idRepseClient, String period, String tenantId) {
        List<RepseDocumentDO> docs = repseDocumentDAO
                .findAllByIdRepseClientAndPeriodAndTenantId(idRepseClient, period, tenantId);

        int submitted = (int) docs.stream().filter(d -> !"RECHAZADO".equals(d.getStatus())).count();
        int validated = (int) docs.stream().filter(d -> "VALIDADO".equals(d.getStatus())).count();
        int rejected  = (int) docs.stream().filter(d -> "RECHAZADO".equals(d.getStatus())).count();
        int required  = DOCUMENTS_REQUIRED_DEFAULT;
        String semaforo = calculateSemaforo(required, submitted, validated, rejected);

        var existing = repseComplianceDAO.findByIdRepseClientAndPeriodAndTenantId(idRepseClient, period, tenantId);
        RepseComplianceDO compliance;
        if (existing.isPresent()) {
            compliance = existing.get();
        } else {
            compliance = new RepseComplianceDO();
            compliance.setTenantId(tenantId);
            compliance.setIdRepseClient(idRepseClient);
            compliance.setPeriod(period);
            compliance.setActive(Boolean.TRUE);
        }
        compliance.setDocumentsRequired(required);
        compliance.setDocumentsSubmitted(submitted);
        compliance.setDocumentsValidated(validated);
        compliance.setDocumentsRejected(rejected);
        compliance.setSemaforo(semaforo);

        return enrichCompliance(repseComplianceDAO.save(compliance));
    }

    private String calculateSemaforo(int required, int submitted, int validated, int rejected) {
        if (required > 0 && validated >= required) {
            return "VERDE";
        } else if (rejected > 0 || submitted < required) {
            return "ROJO";
        } else {
            return "AMARILLO";
        }
    }

    private RepseComplianceTO enrichCompliance(RepseComplianceDO do_) {
        var to = modelMapper.map(do_, RepseComplianceTO.class);
        repseClientDAO.findById(do_.getIdRepseClient()).ifPresent(client -> {
            to.setRazonSocialCliente(client.getRazonSocial());
            to.setRfcCliente(client.getRfc());
        });
        return to;
    }
}
