package mx.com.axity.persistence;

import mx.com.axity.model.ContratingDataDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContratingDataDAO extends CrudRepository<ContratingDataDO,Long> {

    @Query("select sum(cd.qtSalary) from ContratingDataDO cd where cd.idUser.idUser in (select e.idUserDO.idUser from EmployeeDO e where e.idProject.idProject = :idProject)")
    Long getSumEmployeeByIdProject(@Param("idProject") Long id);


    @Query("select p from ContratingDataDO p  where p.idUser.idUser = :idUser")
    ContratingDataDO getEmployeeContratingDataByIdUser(@Param("idUser") Long idUser);
}
