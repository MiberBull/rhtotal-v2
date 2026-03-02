package mx.com.axity.persistence;

import mx.com.axity.model.CompesationPackageDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompesationPackageDAO extends CrudRepository<CompesationPackageDO,Long> {
    @Query("select p from CompesationPackageDO p  where p.idUser = :idUser")
    List<CompesationPackageDO> getCompesationPackageByIdUser(@Param("idUser") Long id);
}
