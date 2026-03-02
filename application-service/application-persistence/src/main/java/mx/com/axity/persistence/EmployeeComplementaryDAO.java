package mx.com.axity.persistence;

import mx.com.axity.model.EmployeeComplementaryDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface EmployeeComplementaryDAO extends CrudRepository<EmployeeComplementaryDO, Long> {

    ////////
    @Query("select n from EmployeeComplementaryDO n \n" +
            "ORDER BY n.lastModification DESC")
    List<EmployeeComplementaryDO> findEmployeeByClientProyectCurp();


}
