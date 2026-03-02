package mx.com.axity.persistence;

import mx.com.axity.model.HeaderDisplayDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface HeaderDisplayDAO extends CrudRepository<HeaderDisplayDO,Long> {
    @Query("select h.valueHeader from HeaderDisplayDO h where h.nameHeader = :nameHeader ")
    String getNameHeader(@Param("nameHeader") String nameHeader);
}
