package mx.com.axity.persistence;

import mx.com.axity.model.EmployeeAddressDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EmployeeAddressDAO extends CrudRepository<EmployeeAddressDO, Long> {


    @Query("select p from EmployeeAddressDO p  where p.employee.idUserDO.idUser= :idEmployee")
    EmployeeAddressDO getEmployeeAdressByIdEmployee(@Param("idEmployee") Long id);
}
