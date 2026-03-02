package mx.com.axity.persistence;

import mx.com.axity.model.UserDO;
import org.springframework.data.repository.CrudRepository;

public interface UserLoginDAO extends CrudRepository<UserDO,Long> {

    UserDO findByEmail(String email);
}
