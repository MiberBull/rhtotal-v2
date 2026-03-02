package mx.com.axity.persistence;

import mx.com.axity.model.TokenNotificationDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TokenNotificationDAO extends CrudRepository<TokenNotificationDO, Long> {

    @Query("select t.token from TokenNotificationDO t where t.idUser in :idUsers and t.active = true")
    List<String> getTokensByIdUsersList(@Param("idUsers") List<Long> idUsers);

    @Query("select count(t) from TokenNotificationDO t where t.idUser = :idUser and t.token = :token and t.active = true")
    Long getCountTokensByIdUserAndToken(@Param("idUser") Long idUser, @Param("token") String token);

    @Query("select t from TokenNotificationDO t where t.idUser = :idUser and t.token = :token and t.active = true")
    List<TokenNotificationDO> getTokensByIdUserAndToken(@Param("idUser") Long idUser, @Param("token") String token);
}
