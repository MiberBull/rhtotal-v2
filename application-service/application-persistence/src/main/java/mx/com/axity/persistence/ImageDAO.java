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

    @Query(value = "SELECT bi.ds_image FROM k_notification_assignment n " +
            "INNER JOIN k_banner b ON b.id_banner = n.id_notification " +
            "   AND n.ds_type_notification = 'B' " +
            "INNER JOIN k_banner_images bi ON bi.id_banner = b.id_banner " +
            "WHERE n.id_user = :idUser " +
            "AND CAST(CONCAT(CAST(b.dt_start_date AS date), ' ', b.dt_time_publicacion) AS timestamp) <= CAST(:currentDateTime AS timestamp) " +
            "AND CAST(b.dt_end_date AS date) >= CAST(:currentDate AS date) " +
            "AND b.ds_status = 'A' ORDER BY b.dt_last_modification DESC, bi.dt_last_modification DESC, bi.ds_type_image DESC",
            countQuery = "SELECT count(bi.ds_image) FROM k_notification_assignment n " +
            "INNER JOIN k_banner b ON b.id_banner = n.id_notification " +
            "   AND n.ds_type_notification = 'B' " +
            "INNER JOIN k_banner_images bi ON bi.id_banner = b.id_banner " +
            "WHERE n.id_user = :idUser " +
            "AND CAST(CONCAT(CAST(b.dt_start_date AS date), ' ', b.dt_time_publicacion) AS timestamp) <= CAST(:currentDateTime AS timestamp) " +
            "AND CAST(b.dt_end_date AS date) >= CAST(:currentDate AS date) " +
            "AND b.ds_status = 'A'",
            nativeQuery = true)
    Page<String> findImagesBannerByIdUser(Pageable page, @Param("idUser") Long idUser,
                                          @Param("currentDate") LocalDate endDate,
                                          @Param("currentDateTime") LocalDateTime dateTime);

}
