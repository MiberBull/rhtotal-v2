package mx.com.axity.persistence;

import mx.com.axity.model.ImageDiscountDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ImageDiscountDAO extends CrudRepository<ImageDiscountDO,Long>  {
    @Query("select n from ImageDiscountDO n where n.idDiscount.idDiscount = :idDiscount  order by n.typeImage,n.idImage")
    List<ImageDiscountDO> findAllByIdDiscount(@Param("idDiscount") Long idDiscount);

    @Query("select m from ImageDiscountDO m where m.idDiscount.idDiscount in(:idDiscount) and (CAST((CONCAT( CAST(m.idDiscount.startDate AS java.time.LocalDate),' ',m.idDiscount.publicationTime)) AS java.time.LocalDateTime)  <= :timeday) and (CAST(m.idDiscount.endDate AS java.time.LocalDate) >= :dateday)   and m.typeImage = :typeImage and m.idDiscount.status = 'A' order by m.idDiscount.idDiscount")
    Page<ImageDiscountDO> getImagePageByIdDiscount(Pageable pageable, @Param("idDiscount") List<Long> idDiscount, @Param("typeImage") String typeImage, @Param("dateday")LocalDate dateday, @Param("timeday") LocalDateTime timeday);

    @Query("select m from ImageDiscountDO m where m.idDiscount.idDiscount = :idDiscount and m.typeImage = :typeImage")
    List<ImageDiscountDO> getImagesSecundary(@Param("idDiscount") Long idDiscount, @Param("typeImage") String typeImage);
}
