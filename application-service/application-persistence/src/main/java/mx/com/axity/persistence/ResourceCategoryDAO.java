package mx.com.axity.persistence;

import mx.com.axity.model.ResourceCategoryDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceCategoryDAO extends CrudRepository<ResourceCategoryDO, Long> {

    @Query("SELECT c FROM ResourceCategoryDO c WHERE c.tenantId = :tenantId AND c.active = true ORDER BY c.name ASC")
    List<ResourceCategoryDO> findActiveByTenant(@Param("tenantId") String tenantId);
}
