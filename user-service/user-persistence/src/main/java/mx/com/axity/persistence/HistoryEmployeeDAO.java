package mx.com.axity.persistence;

import mx.com.axity.model.HistoryEmployeeDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface HistoryEmployeeDAO extends CrudRepository<HistoryEmployeeDO,Long> {

    @Query("select p from HistoryEmployeeDO p  where p.idUser = :idUser")
    HistoryEmployeeDO getEmployeeHistoryByIdUser(@Param("idUser") Long id);
}
