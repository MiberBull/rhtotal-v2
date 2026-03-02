package mx.com.axity.persistence;

import mx.com.axity.model.ConfigEmailDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ConfigEmailDAO extends CrudRepository<ConfigEmailDO,Long> {
    @Query("select p.value from ConfigEmailDO as p where nameParemeter = :nameParemeter")
    String findByNameParemeter(@Param("nameParemeter") String parameter);
}
