package mx.com.axity.persistence;

import mx.com.axity.model.ParameterDO;
import org.springframework.data.repository.CrudRepository;

public interface ParameterDAO extends CrudRepository<ParameterDO, Long> {
    ParameterDO findByName(String name);
}
