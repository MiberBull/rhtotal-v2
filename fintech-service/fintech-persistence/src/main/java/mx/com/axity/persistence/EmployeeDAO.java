package mx.com.axity.persistence;

import mx.com.axity.model.EmployeeDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EmployeeDAO extends CrudRepository<EmployeeDO,Long> {

    @Query("SELECT e FROM EmployeeDO e WHERE e.idUserDO.idUser = :idUser")
    EmployeeDO getEmployeeByIdUser(@Param("idUser") Long id);

    @Modifying
    @Query("UPDATE EmployeeComplementaryDO e SET e.idSwap= :idSwap WHERE e.idEmployee =:idEmployee")
    void updateSwapIdForEmploye(@Param("idEmployee") long idEmployee,
                                @Param("idSwap")long idSwap );
                                
    @Query("SELECT e.idEmployee FROM EmployeeDO e WHERE e.idUserDO.idUser = :idUser")
    Long getIdEmployeeByIdUser(@Param("idUser") Long id);

}
