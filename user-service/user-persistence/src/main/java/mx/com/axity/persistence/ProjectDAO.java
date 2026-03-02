package mx.com.axity.persistence;

import mx.com.axity.model.ProjectDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectDAO extends CrudRepository<ProjectDO, Long> {

    @Query("Select s from ProjectDO s where s.status='A' and s.idClient = :idClient order by s.name asc ")
    List<ProjectDO> getProjectOrderByName(@Param("idClient") Long id);


}
