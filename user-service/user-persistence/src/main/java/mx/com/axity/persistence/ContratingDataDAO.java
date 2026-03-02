package mx.com.axity.persistence;

import mx.com.axity.model.ContratingDataDO;
import mx.com.axity.model.EmployeeDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContratingDataDAO extends CrudRepository<ContratingDataDO,Long> {

    @Query("select p from ContratingDataDO p  where p.idUser = :idUser")
    ContratingDataDO getEmployeeContratingDataByIdUser(@Param("idUser") Long id);
}
