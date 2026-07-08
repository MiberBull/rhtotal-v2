package mx.com.axity.persistence;

import mx.com.axity.model.FaqDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqDAO extends JpaRepository<FaqDO, Long> {
    List<FaqDO> findAllByTenantIdAndFgActiveTrueOrderByNbOrderAsc(String tenantId);
    List<FaqDO> findAllByTenantIdAndDsCategoryAndFgActiveTrueOrderByNbOrderAsc(String tenantId, String dsCategory);
}
