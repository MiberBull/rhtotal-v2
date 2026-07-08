package mx.com.axity.persistence;

import mx.com.axity.model.RolesUserDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface RolesUserDAO extends CrudRepository<RolesUserDO,Long> {
    Page<RolesUserDO> findAllByOrderByLastModificationDesc(Pageable pageable);

    @Query("select n from RolesUserDO n where (n.email = :email and (:id is null or n.idRolAssig <>:id))")
    List<RolesUserDO> findByEmail(@Param("email") String email, @Param("id") Long id);

    @Query("select n from RolesUserDO n where (n.email = :email and (:id is null or n.idRolAssig <>:id)) and n.tenantId = :tenantId")
    List<RolesUserDO> findByEmailAndTenantId(@Param("email") String email, @Param("id") Long id, @Param("tenantId") String tenantId);

    @Query("select count(n) from RolesUserDO n where n.nameRol not in('Administrador Master')")
    Long getNumberRow();

    @Query("select n from RolesUserDO n where (n.email = :email) and status <>'I' ")
    Optional<RolesUserDO> findByEmailReset(@Param("email") String email);

    @Query("select n from RolesUserDO n where (n.email = :email) and status <>'I' and n.tenantId = :tenantId")
    Optional<RolesUserDO> findByEmailResetAndTenantId(@Param("email") String email, @Param("tenantId") String tenantId);

    @Query("select n from RolesUserDO n \n" +
            "order by n.lastModification DESC")
    List<RolesUserDO> findAllRolesUserDO();

}
