package mx.com.axity.persistence;

import mx.com.axity.model.AssigationDataDO;
import mx.com.axity.model.EmployeeDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AsignationDataDAO extends CrudRepository<AssigationDataDO,Long> {
    @Query("select p from AssigationDataDO p  where p.idUser = :idUser")
    AssigationDataDO getAsignationDataByIdUser(@Param("idUser") Long id);
}
