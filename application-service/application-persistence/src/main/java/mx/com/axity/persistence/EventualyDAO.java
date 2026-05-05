package mx.com.axity.persistence;

import mx.com.axity.model.EventualyDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventualyDAO extends CrudRepository<EventualyDO,Long> {

    @Query("SELECT e " +
           "FROM EventualyDO e JOIN e.idInsurance i " +
           "WHERE i.idInsurance = :idInsurance " +
            "ORDER BY e.lastModification DESC")
    Page<EventualyDO> findAllEventualy(Pageable pageable, @Param("idInsurance") Long idInsurance);

    @Query("SELECT count(e) " +
            "FROM EventualyDO e JOIN e.idInsurance i " +
            "WHERE i.idInsurance = :idInsurance")
    Long getNumberRowEventualy(@Param("idInsurance") Long idInsurance);
}
