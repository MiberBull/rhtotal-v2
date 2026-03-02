package mx.com.axity.persistence;

import mx.com.axity.model.EmployeeDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EmployeeDAO extends CrudRepository<EmployeeDO, Long> {

    @Query("select e.idUserDO.idUser from EmployeeDO e where e.idCliente.idCliente in :idClients")
    List<Long> getIdUsersByIdClient(@Param("idClients") List<Long> idClients);

    @Query("select e.idUserDO.idUser from EmployeeDO e where e.idProject.idProject in :idProjects order by e.name")
    List<Long> getIdUsersByIdProject(@Param("idProjects") List<Long> idProjects);

    @Query("select p from EmployeeDO p  where p.idEmployee= :idEmployee")
    List<EmployeeDO> getEmployeeById(@Param("idEmployee") Long id);

    @Query("select p from EmployeeDO p  where p.idProject.idProject = :idProject and p.idUserDO.statusUser= 'A' order by p.name")
    List<EmployeeDO> getEmployeeByIdProject(@Param("idProject") Long id);

    @Query("select p from EmployeeDO p  where p.idUserDO.idUser = :idUser")
    EmployeeDO getEmployeeByIdUser(@Param("idUser") Long id);

    @Query(value = "select count(1) as numEmployee, sum(c.qt_salary) as sumSalary from k_employee e left join k_contrating_data c on e.id_user=c.id_user where e.id_project=:idProject", nativeQuery = true)
    Object[] getCountEmployeeByIdProyect(@Param("idProject") Long idProject);
}


