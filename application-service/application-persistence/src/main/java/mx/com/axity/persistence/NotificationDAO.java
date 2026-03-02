package mx.com.axity.persistence;

import mx.com.axity.model.NotificationDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface NotificationDAO extends CrudRepository<NotificationDO,Long> {
    @Query("select n from NotificationDO n where n.status in(:status)" +
            "order by n.lastModification DESC")
    List<NotificationDO> getNotificationStatus(@Param("status") List<String> status);

    @Query("select n from NotificationDO n where n.startDate between ?1 and ?2 and n.status = ?3 and n.active = true")
    List<NotificationDO> getActiveNotifications(LocalDateTime initDate, LocalDateTime endDate, String status);

    @Query("select n from NotificationDO n where n.status in(:status) and (:title is null or (Upper(n.title) LIKE %:title%)) and (:autor is null or (Upper(n.lastUserModifier) LIKE %:autor%)) and (CAST(:startdate AS java.time.LocalDate) is null or n.startDate >= :startdate) and (CAST(:enddate AS java.time.LocalDate) is null or n.startDate <:enddate) order by n.lastModification desc")
    Page<NotificationDO> findAllByOrderByLastModificationAsc(Pageable pageable,
                                                             @Param("status") List<String> status,
                                                             @Param("title") String title,
                                                             @Param("autor") String autor,
                                                             @Param("startdate") LocalDateTime startdate,
                                                             @Param("enddate") LocalDateTime enddate);

    @Query("select count(n) from NotificationDO n where n.status in(:status) and (:title is null or (Upper(n.title) LIKE %:title%)) and (:autor is null or (Upper(n.lastUserModifier) LIKE %:autor%)) and (CAST(:startdate AS java.time.LocalDate) is null or n.startDate >= :startdate) and (CAST(:enddate AS java.time.LocalDate) is null or n.startDate <:enddate)")
    Long getNumberRow( @Param("status") List<String> status,
                       @Param("title") String title,
                       @Param("autor") String autor,
                       @Param("startdate") LocalDateTime startdate,
                       @Param("enddate") LocalDateTime enddate);

    @Modifying
    @Query("update NotificationDO n set n.status ='E' where n.idNotificacion IN (select n.idElement from NotificationRepositoryDO n where n.type='N' and n.status='E')")
    void updateNotificationSend();

}
