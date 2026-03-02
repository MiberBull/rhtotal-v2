package mx.com.axity.persistence;

import mx.com.axity.model.UserDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDAO  extends CrudRepository<UserDO,Long> {

    @Query("select u.idUser from UserDO u where u.userType = ?1 and u.active = true")
    List<Long> findUsersByUserType(String userType);

    @Query("select u.email from UserDO u where u.idUser in ?1 and u.statusUser='A' and u.active = true")
    List<String> findUsersById(List<Long> userType);
}
