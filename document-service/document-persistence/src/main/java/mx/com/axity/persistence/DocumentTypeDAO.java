package mx.com.axity.persistence;

import mx.com.axity.model.DocumentTypeDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentTypeDAO extends JpaRepository<DocumentTypeDO, Long> {
    List<DocumentTypeDO> findAllByTenantIdAndFgActiveTrue(String tenantId);
    List<DocumentTypeDO> findAllByTenantIdAndFgRequiredOnboardingTrueAndFgActiveTrue(String tenantId);
    Optional<DocumentTypeDO> findByDsCodeAndTenantId(String dsCode, String tenantId);
}
