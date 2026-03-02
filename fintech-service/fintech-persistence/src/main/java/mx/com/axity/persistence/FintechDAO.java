package mx.com.axity.persistence;

import mx.com.axity.model.FintechMyAdvanceDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface FintechDAO extends CrudRepository<FintechMyAdvanceDO,Long>  {

    @Query("SELECT f \n" +
            "FROM FintechMyAdvanceDO f INNER JOIN EmployeeDO e ON f.idEmployee = e.idEmployee\n" +
            "LEFT JOIN ProjectDO p ON e.idProject = p.idProject\n"+
            "LEFT JOIN CustomerDO c ON e.idCliente = c.idCliente\n"+
            "WHERE f.statusRequisition = :status\n" +
            "AND (:firstName IS NULL OR (Upper(e.name) LIKE %:firstName%))\n" +
            "AND (:lastName IS NULL OR (Upper(e.lastName) LIKE %:lastName%))\n" +
            "AND (:secondSurname IS NULL OR (Upper(e.mLastName) LIKE %:secondSurname%))\n" +
            "AND (:folioRequest IS NULL OR (Upper(f.requisitionFolio) LIKE %:folioRequest%))\n" +
            "AND (CAST(:requestDate AS java.time.LocalDate) IS NULL OR f.creationDate = :requestDate)\n" +
            "ORDER BY e.name ASC")
    Page<FintechMyAdvanceDO> findAllByOrderByLastModificationAsc(Pageable pageable, @Param("status") String status,
                                                              @Param("firstName") String firstName,
                                                              @Param("lastName") String lastName,
                                                              @Param("secondSurname") String secondSurname,
                                                              @Param("folioRequest") String folioRequest,
                                                              @Param("requestDate") LocalDateTime requestDate);

    @Query("SELECT f \n" +
            "FROM FintechMyAdvanceDO f INNER JOIN EmployeeDO e ON f.idEmployee = e.idEmployee\n" +
            "LEFT JOIN ProjectDO p ON e.idProject = p.idProject\n" +
            "LEFT JOIN CustomerDO c ON e.idCliente = c.idCliente\n" +
            "WHERE f.idPaysheetNow = :fintechOne")
    FintechMyAdvanceDO findOneMyAdvance(@Param("fintechOne") Long fintechOne);

    @Query("SELECT count(f) \n" +
            "FROM FintechMyAdvanceDO f INNER JOIN EmployeeDO e ON f.idEmployee = e.idEmployee\n" +
            "LEFT JOIN ProjectDO p ON e.idProject = p.idProject\n"+
            "LEFT JOIN CustomerDO c ON e.idCliente = c.idCliente\n"+
            "WHERE f.statusRequisition = :status\n" +
            "AND (:firstName IS NULL OR (e.name LIKE %:firstName%))\n" +
            "AND (:lastName IS NULL OR (e.lastName LIKE %:lastName%))\n" +
            "AND (:secondSurname IS NULL OR (e.mLastName LIKE %:secondSurname%))\n" +
            "AND (:folioRequest IS NULL OR (f.requisitionFolio LIKE %:folioRequest%))\n" +
            "AND (CAST(:requestDate AS java.time.LocalDate) IS NULL OR f.creationDate = :requestDate)")
    Long getNumberRow(@Param("status") String status,
                      @Param("firstName") String firstName,
                      @Param("lastName") String lastName,
                      @Param("secondSurname") String secondSurname,
                      @Param("folioRequest") String folioRequest,
                      @Param("requestDate") LocalDateTime requestDate);

    @Query("SELECT f.idPaysheetNow FROM FintechMyAdvanceDO f WHERE f.idEmployee.idUserDO.idUser = :idEmployee AND f.statusRequisition = 'ES'")
    List<Long> findByPeriod( @Param("idEmployee") long idEmployee);

    @Query("SELECT f.idPaysheetNow FROM FintechMyAdvanceDO f WHERE f.idEmployee.idUserDO.idUser = :idEmployee AND f.creationDate BETWEEN :startPeriod AND :endPeriod")
    List<Long> findByPeriod( @Param("idEmployee") long idEmployee,
                             @Param("startPeriod") LocalDateTime startPeriod,
                             @Param("endPeriod") LocalDateTime endPeriod);

}
