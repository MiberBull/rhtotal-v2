package mx.com.axity.services.impl;

import mx.com.axity.model.BuzonConfidencialDO;
import mx.com.axity.persistence.BuzonConfidencialDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuzonConfidencialServiceImpl {

    @Autowired private BuzonConfidencialDAO buzonDAO;

    public BuzonConfidencialDO save(BuzonConfidencialDO buzon) {
        return buzonDAO.save(buzon);
    }

    public List<BuzonConfidencialDO> findByTenant(String tenantId) {
        return buzonDAO.findAllByTenantIdOrderByDtCreacionDesc(tenantId);
    }

    public BuzonConfidencialDO findById(Long id) {
        return buzonDAO.findById(id).orElseThrow(() -> new RuntimeException("Buzón no encontrado: " + id));
    }

    public BuzonConfidencialDO updateEstatus(Long id, String estatus, String comentario) {
        BuzonConfidencialDO buzon = findById(id);
        buzon.setDsEstatus(estatus);
        if (comentario != null && !comentario.isBlank()) {
            buzon.setDsComentarioRh(comentario);
        }
        return buzonDAO.save(buzon);
    }
}
