package mx.com.axity.persistence;

import mx.com.axity.model.ProjectDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectDAO extends CrudRepository<ProjectDO,Long> {
   @Query("select p from ProjectDO p  where p.idClient.idCliente = :idCliente and(:name is  null or p.name = :name) and p.status='A' order by p.name")
   List<ProjectDO> getProjectsClient(@Param("idCliente") Long id,@Param("name") String name);

   @Query("select p from ProjectDO p  where p.idClient.idCliente = :idCliente and(:name is  null or p.name = :name)  order by p.name")
   List<ProjectDO> getProjectsClientAll(@Param("idCliente") Long id,@Param("name") String name);

   @Query("select p from ProjectDO p \n"+
           "order by p.lastModification DESC")
   List<ProjectDO> getProjectsExcelAll();


}

