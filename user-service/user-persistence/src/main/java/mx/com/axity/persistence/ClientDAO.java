package mx.com.axity.persistence;

import mx.com.axity.model.ClientDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientDAO extends CrudRepository<ClientDO, Long> {

    @Query("Select s from ClientDO s  where s.status='A' order by s.name asc ")
    List<ClientDO> getClientOrderByName();
}
