package mx.com.axity.persistence;

import mx.com.axity.model.ResourceAckDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResourceAckDAO extends CrudRepository<ResourceAckDO, Long> {

    @Query("SELECT a FROM ResourceAckDO a WHERE a.document.idDocument = :idDocument ORDER BY a.acknowledgedAt DESC")
    List<ResourceAckDO> findByDocument(@Param("idDocument") Long idDocument);

    @Query("SELECT a FROM ResourceAckDO a WHERE a.document.idDocument = :idDocument AND a.idEmployee = :idEmployee")
    Optional<ResourceAckDO> findByDocumentAndEmployee(@Param("idDocument") Long idDocument, @Param("idEmployee") Long idEmployee);
}
