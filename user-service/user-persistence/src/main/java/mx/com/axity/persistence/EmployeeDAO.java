package mx.com.axity.persistence;

import mx.com.axity.model.EmployeeDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeDAO extends CrudRepository<EmployeeDO, Long> {

    Optional<EmployeeDO> findOptionalById(long id);

    @Query("select p from EmployeeDO p  where p.user.id = :idUser")
    EmployeeDO getEmployeeByIdUser(@Param("idUser") Long id);

}
