package mx.com.axity.persistence;

import mx.com.axity.model.EmployeeComplementaryDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface EmployeeComplementaryDAO extends CrudRepository<EmployeeComplementaryDO, Long> {

    @Query("select e from EmployeeComplementaryDO e where e.employee.name = :name and e.employee.lastName = :lastName and e.birthDate= :birthDate")
    EmployeeComplementaryDO getEmployeeComplementaryByName(@Param("name") String name,
                                                           @Param("lastName") String lastName,
                                                           @Param("birthDate") LocalDateTime birthDate);

    @Query("select e from EmployeeComplementaryDO e order by e.employee.name")
    List<EmployeeComplementaryDO> getAllEmployees();

    @Query("select em from EmployeeComplementaryDO em where em.employee.user.userStatus='A' and em.employee.name = :name and  em.employee.lastName = :lastName and em.birthDate = CAST(:birthDate AS java.time.LocalDate)")
    EmployeeComplementaryDO getUserRegisterSico(@Param("name") String name, @Param("lastName") String lastName, @Param("birthDate") LocalDateTime birthDate);



    @Query("select n from EmployeeComplementaryDO n where (Upper(n.curp) LIKE %:curp%) and ( :client is null or Upper(n.employee.client.name) LIKE %:client%)and( '' =:project  or (Upper(n.employee.project.name) LIKE %:project%))")
    List<EmployeeComplementaryDO> findEmployeeByClientProyectCurp(@Param("curp") String curp,
                                                                  @Param("client") String client,
                                                                  @Param("project")String project);


    @Query("select em from EmployeeComplementaryDO em where em.employee.user.id = :id")
    EmployeeComplementaryDO getUserRegisterById(@Param("id") Long id);

    @Query(value = "SELECT * FROM k_employee_complementary e " +
            "WHERE EXTRACT(MONTH FROM e.ds_birthdate) = :month " +
            "AND EXTRACT(DAY FROM e.ds_birthdate) = :day " +
            "AND e.fg_active = true", nativeQuery = true)
    List<EmployeeComplementaryDO> findBirthdaysToday(@Param("month") int month, @Param("day") int day);

    @Query(value = " SELECT  p.nivel ,\n" +
            "            p.total_user ,\n" +
            "            p.total ,\n" +
            "            p.porcentaje ,\n" +
            "            p.porcentaje_grafica ,\n" +
            "            COALESCE(t.promedio_final,0) promedio_final,\n" +
            "            COALESCE(t.promedio_final_grafica,0) promedio_final_grafica\n" +
            "      FROM (SELECT  1 dummy ,\n" +
            "                    csw.ds_section_name                                          nivel ,\n" +
            "                    SUM(ts.total_user)                                           total_user ,\n" +
            "                    SUM(ts.total)                                                total ,\n" +
            "                    ROUND(CAST(SUM(ts.total_user) AS DECIMAL)/SUM(ts.total)*100)     porcentaje ,\n" +
            "                    ROUND(CAST(SUM(ts.total_user) AS DECIMAL)/SUM(ts.total)*100)*(7) porcentaje_grafica\n" +
            "               FROM c_section_weighing csw\n" +
            "         INNER JOIN ( SELECT ctw.id_section_weighing ,\n" +
            "                             ctw.id_table_name_weighing ,\n" +
            "                             mobile_weighting(ctw.id_section_weighing\n" +
            "                                             ,ctw.id_table_name_weighing\n" +
            "                                             ,ktw.id_user)     total ,\n" +
            "                             COALESCE(ktw.qt_total_weighing,0) total_user \n" +
            "                        FROM c_total_weighing ctw\n" +
            "                   LEFT JOIN k_total_weighing_user ktw ON ctw.id_section_weighing = ktw.id_section_weighing\n" +
            "                                                      AND ctw.id_table_name_weighing = ktw.id_table_name_weighing\n" +
            "                                                      AND ktw.id_user = ?1) ts ON csw.id_section_weighing = ts.id_section_weighing\n" +
            "            GROUP BY csw.ds_section_name ) p\n" +
            "INNER JOIN (SELECT 1 dummy\n" +
            "                 , percentage_weighting(?1) promedio_final \n" +
            "                 , percentage_weighting_graph(?1) promedio_final_grafica) t ON t.dummy = p.dummy;" ,nativeQuery = true)
    List<Object[]> getPonderationSection(Long idUser);
}
