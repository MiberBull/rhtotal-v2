package mx.com.axity.persistence;

import mx.com.axity.model.NotificationRepositoryDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface NotificationRepositoryDAO  extends CrudRepository<NotificationRepositoryDO,Long> {

    @Query("select d from NotificationRepositoryDO d WHERE idElement = :idElement AND type =:type")
    NotificationRepositoryDO findByIdAndType(@Param("idElement") long idElement,
                                              @Param("type")String type);

    @Query("select n from NotificationRepositoryDO n where n.dateNotification between ?1 and ?2 and n.status = ?3 and n.fgActive = true")
    List<NotificationRepositoryDO> getActiveNotifications(LocalDateTime initDate, LocalDateTime endDate, String status);

    //TODO: Cambiar la sentencia por una como esta
    //@Query(nativeQuery = true, value = "select a.* from k_notification_repository a where (a.ds_type, a.id_element) in (:curr, :iban)")
    //List<NotificationRepositoryDO> getNotificationsByIdElementAndType(@Param("curr") List<String> currencies, @Param("iban") List<Integer> ibans);

    @Query(value = "select n from NotificationRepositoryDO n where n.idElement = :idElement and n.type = :notificationType and n.fgActive = true and n.status = 'E' order by n.dateNotification desc")
    NotificationRepositoryDO getNotificationsByIdElementAndType(@Param("idElement") Long idElement, @Param("notificationType") String notificationType);

    @Query("select n.idElement from NotificationRepositoryDO n where n.type=:type and n.status=:status")
     List<String> getNotificationSends(@Param("type")String type,@Param("status")String status);

    @Modifying
    @Query("delete from NotificationRepositoryDO n where n.idElement = :idElement and n.type = :notificationType")
    void deleteNotificationRepository(@Param("notificationType")String type,@Param("idElement")Long idElement);


}
