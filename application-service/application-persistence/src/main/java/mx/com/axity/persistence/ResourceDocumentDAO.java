package mx.com.axity.persistence;

import mx.com.axity.model.ResourceDocumentDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceDocumentDAO extends CrudRepository<ResourceDocumentDO, Long> {

    @Query("SELECT d FROM ResourceDocumentDO d WHERE d.tenantId = :tenantId AND d.active = true " +
           "AND (d.expiryDate IS NULL OR d.expiryDate > CURRENT_TIMESTAMP) " +
           "ORDER BY d.creationDate DESC")
    List<ResourceDocumentDO> findAllActiveByTenant(@Param("tenantId") String tenantId);

    @Query("SELECT d FROM ResourceDocumentDO d WHERE d.tenantId = :tenantId AND d.active = true " +
           "AND (d.expiryDate IS NULL OR d.expiryDate > CURRENT_TIMESTAMP) " +
           "AND (d.visibility = 'GENERAL' OR (d.visibility = 'BY_CLIENT' AND d.idClient = :idClient)) " +
           "ORDER BY d.publicationDate DESC")
    List<ResourceDocumentDO> findVisibleForEmployee(@Param("tenantId") String tenantId, @Param("idClient") Long idClient);

    @Query("SELECT d FROM ResourceDocumentDO d WHERE d.tenantId = :tenantId AND d.active = true " +
           "AND (d.expiryDate IS NULL OR d.expiryDate > CURRENT_TIMESTAMP) " +
           "AND d.category.idCategory = :idCategory ORDER BY d.creationDate DESC")
    List<ResourceDocumentDO> findByTenantAndCategory(@Param("tenantId") String tenantId, @Param("idCategory") Long idCategory);
}
