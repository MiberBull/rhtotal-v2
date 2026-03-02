package mx.com.axity.persistence;

import mx.com.axity.model.EmployeeDO;
import mx.com.axity.model.SocialNetworkDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeSocialNetworkDAO extends CrudRepository<SocialNetworkDO,Long> {

    @Query("select p from SocialNetworkDO p  where p.idUSer = :idUser")
    List<SocialNetworkDO>  getEmployeeSocialNetworkByIdUser(@Param("idUser") Long id);
}
