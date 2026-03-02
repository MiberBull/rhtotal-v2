package mx.com.axity.persistence;

import mx.com.axity.model.NotificationAssignmentDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface NotificationAssignmentDAO extends CrudRepository<NotificationAssignmentDO, Long> {

    @Query("select u.idUser from NotificationAssignmentDO u where u.idNotification = ?1 and u.typeNotification = ?2 and u.active = true")
    List<Long> findUsersAssignedToNotificationByIdNotification(Long idNotification, String notificationType);

	//TODO:Cambiar el nombre de este metodo, porque ahora tambien contempla el tipo
    @Query("select n from NotificationAssignmentDO n where n.idNotification = :idNotification and n.typeNotification = :typeNotification")
    List<NotificationAssignmentDO> findAllNotificationById(@Param("idNotification") Long idNotification, @Param("typeNotification") String typeNotification);

    @Modifying
    @Query("delete from NotificationAssignmentDO n where n.idNotification = :idNotification and n.typeNotification = :typeNotification")
    void deleteAllByNotificationById(@Param("idNotification") Long idNotification, @Param("typeNotification") String typeNotification);

    @Query("select n.idNotification from NotificationAssignmentDO n where n.idUser = :idUser and n.typeNotification = :typeNotification")
    List<Long> getNotificationAssignmentByIdUserAndTypeNotification(@Param("idUser") Long idUser,@Param("typeNotification") String typeNotification);

    @Query("select distinct n.idNotification, n.typeNotification from NotificationAssignmentDO n where n.idUser = :idUser and n.active = true")
    List<Object[]> getIdNotificationAndTypeByIdUser(@Param("idUser") Long idUser);

    @Query("select count(n.idCliente) from NotificationAssignmentDO n  where n.idNotification = :idNotification and n.typeNotification = :typeNotification and n.idCliente =0 ")
    int validatosCkeckExterno(@Param("idNotification") Long idNotification, @Param("typeNotification") String typeNotification);


    @Query("SELECT a.idNotification FROM  NotificationAssignmentDO  a  where a.typeNotification = 'I' and a.idUser = :idUser ")
     List<Long> getInsurangeUserAssigment(@Param("idUser") Long idUser);
}
