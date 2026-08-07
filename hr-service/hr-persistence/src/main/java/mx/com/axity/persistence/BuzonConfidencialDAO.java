package mx.com.axity.persistence;

import mx.com.axity.model.BuzonConfidencialDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuzonConfidencialDAO extends JpaRepository<BuzonConfidencialDO, Long> {
    List<BuzonConfidencialDO> findAllByTenantIdOrderByDtCreacionDesc(String tenantId);
    List<BuzonConfidencialDO> findAllByTenantIdAndDsEstatusOrderByDtCreacionDesc(String tenantId, String dsEstatus);
    long countByTenantIdAndDsEstatus(String tenantId, String dsEstatus);
}
