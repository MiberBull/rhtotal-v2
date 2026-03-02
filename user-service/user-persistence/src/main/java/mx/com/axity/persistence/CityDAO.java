package mx.com.axity.persistence;

import mx.com.axity.model.CityDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityDAO extends CrudRepository<CityDO,Long> {
@Query("Select ci from CityDO ci inner join StateCity sc on ci.idCity= sc.cityId  where sc.stateId =:idState order by ci.city asc ")
    List<CityDO> getCityOrderByCity(@Param("idState")Long idState);
}


