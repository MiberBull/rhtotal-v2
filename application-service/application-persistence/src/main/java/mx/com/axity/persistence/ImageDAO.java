package mx.com.axity.persistence;

import mx.com.axity.model.ImageDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ImageDAO extends CrudRepository<ImageDO,Long> {

    @Query("select n from ImageDO n where n.idBanner.idBanner = :idBanner")
    List<ImageDO> findAllByIdBanner(@Param("idBanner") Long idBanner);

    @Query("SELECT img.base64 FROM NotificationAssignmentDO n \n" +
            "INNER JOIN BannerDO b ON b.idBanner = n.idNotification \n" +
            "   and n.typeNotification ='B' \n" +
            "INNER JOIN ImageDO img ON img.idBanner = b.idBanner \n " +
            "WHERE n.idUser = :idUser \n" +
            "AND CAST((CONCAT( CAST(b.startDate AS java.time.LocalDate),' ',b.timePublication)) AS java.time.LocalDateTime ) <= :currentDateTime \n" +
            "AND CAST(b.endDate AS java.time.LocalDate) >= :currentDate \n" +
            "AND b.status = 'A' ORDER BY b.lastModification DESC, img.lastModification DESC, img.typeImage desc")
    Page<String> findImagesBannerByIdUser(Pageable page, @Param("idUser") Long idUser,
                                          @Param("currentDate") LocalDate endDate,
                                          @Param("currentDateTime") LocalDateTime dateTime);

}
