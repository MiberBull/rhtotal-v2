package mx.com.axity.persistence;

import mx.com.axity.model.UserDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends CrudRepository<UserDO, Long> {

    List<UserDO> findByEmail(String email);

    Optional<UserDO> findOptionalByEmail(String email);

    @Query("select u from UserDO u where email = :email and userStatus <>'I'")
    Optional<UserDO> findOptionalByEmailActive(@Param("email")String email);

    @Query("SELECT u.id,u.creationDate,e.name,e.lastName,e.lastMName,ec.photography \n" +
            "FROM UserDO u LEFT JOIN\n" +
            "EmployeeDO e ON\n" +
            "u.id = e.user.id LEFT JOIN\n" +
            "EmployeeComplementaryDO ec ON\n" +
            "e.id = ec.employee.id WHERE u.id = :idUser")
    Optional<Object> getInfoForCredential(@Param("idUser") Long idUser);


}
