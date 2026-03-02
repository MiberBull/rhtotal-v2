package mx.com.axity.persistence;

import mx.com.axity.model.CountSwapDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CountSwapDAO extends CrudRepository<CountSwapDO,Long> {

    @Query(" SELECT c FROM CountSwapDO c \n" +
            "WHERE c.dsValue = :value")
    Optional<CountSwapDO> getCountSwapByValue(@Param("value") String value);

}
