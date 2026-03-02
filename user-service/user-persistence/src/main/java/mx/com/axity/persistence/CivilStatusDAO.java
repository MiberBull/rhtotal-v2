package mx.com.axity.persistence;

import mx.com.axity.model.CityDO;
import mx.com.axity.model.CivilStatusDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
public interface CivilStatusDAO extends CrudRepository<CivilStatusDO,Long> {
    @Query("Select s from CivilStatusDO s order by s.statusCivil asc ")
    List<CivilStatusDO> getCivilStatusOrderByName();
}


