package mx.com.axity.persistence;

import mx.com.axity.model.BannerDO;
import mx.com.axity.model.ImageDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.List;

public interface BannerDAO extends CrudRepository<BannerDO, Long> {

    @Query("select n from BannerDO n where idBanner > 0 and (:title is null or (Upper(n.title) LIKE %:title%)) and (:autor is null or (upper(n.lastUserModifier) LIKE %:autor%)) and (CAST(:startdate AS java.time.LocalDate) is null or n.startDate >= :startdate) and (CAST(:enddate AS java.time.LocalDate) is null or n.startDate <:enddate) order by n.lastModification desc")
    Page<BannerDO> findAllByOrderByLastModificationDesc(Pageable pageable, @Param("title") String title, @Param("autor") String autor,
                                                        @Param("startdate") LocalDateTime startdate,
                                                        @Param("enddate")  LocalDateTime enddate);

    @Query("select count(n) from BannerDO n where idBanner > 0 and (:title is null or (Upper(n.title) LIKE %:title%)) and (:autor is null or (Upper(n.lastUserModifier) LIKE %:autor%)) and (CAST(:startdate AS java.time.LocalDate) is null or n.startDate >= :startdate) and (CAST(:enddate AS java.time.LocalDate) is null or n.endDate <=:enddate)")
    Long getNumberRow(@Param("title") String title, @Param("autor") String autor,
                      @Param("startdate")  LocalDateTime startdate,
                      @Param("enddate") LocalDateTime enddate);

    @Query("select n from BannerDO n \n" +
           "ORDER BY n.lastModification DESC")
    List<BannerDO> findAllBannerDO();

}