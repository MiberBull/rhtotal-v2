package mx.com.axity.persistence;

import mx.com.axity.model.EmployeeComplementaryDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EmployeeComplementaryDAO extends CrudRepository<EmployeeComplementaryDO,Long> {
    @Query("select e from EmployeeComplementaryDO e where e.employee.idUserDO.id = :idUser")
    EmployeeComplementaryDO getEmployeeComByIdUser(@Param("idUser") Long idUser);

}
