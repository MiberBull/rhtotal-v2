package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.to.CfdiTO;
import mx.com.axity.model.CfdiDO;
import mx.com.axity.services.impl.CfdiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CfdiFacadeImpl {

    @Autowired private CfdiServiceImpl cfdiService;

    public CfdiTO importCfdi(CfdiTO cfdiTO, String tenantId) {
        CfdiDO d = toDO(cfdiTO);
        d.setTenantId(tenantId);
        return toTO(cfdiService.importCfdi(d));
    }

    public CfdiTO getById(Long id) {
        return toTO(cfdiService.findById(id));
    }

    public List<CfdiTO> getByEmployee(Long idEmployee, String tenantId) {
        return cfdiService.findByEmployee(idEmployee, tenantId).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public List<CfdiTO> getByEmployeeAndPeriod(Long idEmployee, String tenantId, String period) {
        return cfdiService.findByEmployeeAndPeriod(idEmployee, tenantId, period).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    public List<CfdiTO> getByPeriod(String tenantId, String period) {
        return cfdiService.findByPeriod(tenantId, period).stream()
            .map(this::toTO).collect(Collectors.toList());
    }

    private CfdiTO toTO(CfdiDO d) {
        CfdiTO to = new CfdiTO();
        to.setIdCfdi(d.getIdCfdi()); to.setTenantId(d.getTenantId());
        to.setIdEmployee(d.getIdEmployee()); to.setDsPeriod(d.getDsPeriod());
        to.setDsType(d.getDsType()); to.setDsUuid(d.getDsUuid());
        to.setDsRfcEmisor(d.getDsRfcEmisor()); to.setDsRfcReceptor(d.getDsRfcReceptor());
        to.setNbTotal(d.getNbTotal());
        to.setNbTotalPercepciones(d.getNbTotalPercepciones());
        to.setNbTotalDeducciones(d.getNbTotalDeducciones());
        to.setDsXmlContent(d.getDsXmlContent()); to.setDsXmlS3Key(d.getDsXmlS3Key());
        to.setDsPdfS3Key(d.getDsPdfS3Key()); to.setFgActive(d.getFgActive());
        return to;
    }

    private CfdiDO toDO(CfdiTO to) {
        CfdiDO d = new CfdiDO();
        d.setIdEmployee(to.getIdEmployee()); d.setDsPeriod(to.getDsPeriod());
        d.setDsType(to.getDsType()); d.setDsUuid(to.getDsUuid());
        d.setDsRfcEmisor(to.getDsRfcEmisor()); d.setDsRfcReceptor(to.getDsRfcReceptor());
        d.setNbTotal(to.getNbTotal());
        d.setNbTotalPercepciones(to.getNbTotalPercepciones());
        d.setNbTotalDeducciones(to.getNbTotalDeducciones());
        d.setDsXmlContent(to.getDsXmlContent());
        return d;
    }
}
