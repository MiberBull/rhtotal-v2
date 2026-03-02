package mx.com.axity.persistence;

import mx.com.axity.model.FintechVeloCashDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FintechVeloCashDAO extends CrudRepository<FintechVeloCashDO, Long> {
    @Query("SELECT f \n" +
            "FROM FintechVeloCashDO f INNER JOIN EmployeeDO e ON f.idEmployee = e.idEmployee\n" +
            "LEFT JOIN ProjectDO p ON e.idProject = p.idProject\n"+
            "LEFT JOIN CustomerDO c ON e.idCliente = c.idCliente\n"+
            "WHERE f.statusRequisition = :status\n" +
            "ORDER BY e.name DESC")
    List<FintechVeloCashDO> findAllByOrderByLastModificationAsc(@Param("status") String status);



}
