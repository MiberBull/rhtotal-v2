package mx.com.axity.persistence;

import mx.com.axity.model.UserDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface IUnlockingUserByTimeDAO extends CrudRepository<UserDO,Long> {
    @Transactional
    @Modifying
    @Query("update UserDO u set u.userStatus='A' where u.userStatus='B' and u.lastModification < :nameParemeter")
    void updateStatusInUserBlock(@Param("nameParemeter")LocalDateTime parameter);

    @Transactional
    @Modifying
    @Query("update RolesUserDO u set u.status='A' where u.status='B' and u.lastModification < :nameParemeter")
    void updateStatusInRolBlock(@Param("nameParemeter")LocalDateTime parameter);
}
