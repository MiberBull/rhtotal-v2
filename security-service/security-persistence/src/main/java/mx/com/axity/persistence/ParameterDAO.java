package mx.com.axity.persistence;

import mx.com.axity.model.ParameterDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ParameterDAO extends CrudRepository<ParameterDO,Long> {

    @Query("select p.value from ParameterDO p where p.nameParameter = :nameParameter ")
    String findByNameParameter(@Param("nameParameter") String nameParameter);
}
