package mx.com.axity.persistence;

import mx.com.axity.model.UserDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserDAO extends CrudRepository<UserDO, Long> {

    @Query("SELECT u.email,e.name,e.lastName,e.mLastName,c.curp,c.rfc,c.birthdate,c.phone from UserDO u INNER JOIN EmployeeDO e ON u.idUser = e.idUserDO.idUser INNER JOIN EmployeeComplementaryDO c ON e.idEmployee = c.idEmployee WHERE u.idUser = :idUser")
    Object[] getDataByIdUser(@Param("idUser") Long idUser);

}
