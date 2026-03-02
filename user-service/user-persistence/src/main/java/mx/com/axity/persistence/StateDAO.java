package mx.com.axity.persistence;

import mx.com.axity.model.StateDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
public interface StateDAO extends CrudRepository<StateDO,Long> {
    @Query("Select s from StateDO s order by s.state asc ")
    List<StateDO> getStateOrderByName();
}

