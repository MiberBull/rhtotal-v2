package mx.com.axity.services.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.model.CfdiDO;
import mx.com.axity.persistence.CfdiDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CfdiServiceImpl {

    @Autowired private CfdiDAO cfdiDAO;

    @Transactional
    public CfdiDO importCfdi(CfdiDO cfdi) {
        if (cfdi.getDsUuid() != null) {
            cfdiDAO.findByDsUuidAndTenantId(cfdi.getDsUuid(), cfdi.getTenantId())
                .ifPresent(existing -> {
                    throw new BusinessException(409, "CFDI ya existe con UUID: " + cfdi.getDsUuid());
                });
        }
        return cfdiDAO.save(cfdi);
    }

    public CfdiDO findById(Long id) {
        return cfdiDAO.findById(id)
            .orElseThrow(() -> new BusinessException(404, "CFDI no encontrado: " + id));
    }

    public List<CfdiDO> findByEmployee(Long idEmployee, String tenantId) {
        return cfdiDAO.findAllByIdEmployeeAndTenantIdOrderByDsPeriodDesc(idEmployee, tenantId);
    }

    public List<CfdiDO> findByEmployeeAndPeriod(Long idEmployee, String tenantId, String period) {
        return cfdiDAO.findAllByIdEmployeeAndTenantIdAndDsPeriod(idEmployee, tenantId, period);
    }

    public List<CfdiDO> findByPeriod(String tenantId, String period) {
        return cfdiDAO.findAllByTenantIdAndDsPeriod(tenantId, period);
    }
}
