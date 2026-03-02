package mx.com.axity.persistence;

import mx.com.axity.model.DiscountDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DiscountDAO extends CrudRepository<DiscountDO,Long> {
    @Query("select n from DiscountDO n where idDiscount > 0 and (:supplier is null or (Upper(n.supplier) LIKE %:supplier%)) and (:autor is null or (Upper(n.lastUserModifier) LIKE %:autor%)) and (CAST(:startdate AS java.time.LocalDate) is null or n.startDate >= :startdate) and (CAST(:enddate AS java.time.LocalDate) is null or n.startDate <:enddate) order by n.lastModification desc")
    Page<DiscountDO> findAllByOrderByLastModificationDesc(Pageable pageable, @Param("supplier") String supplier,
                                                          @Param("autor") String autor,
                                                          @Param("startdate") LocalDateTime startdate,
                                                          @Param("enddate") LocalDateTime enddate);

    @Query("select count(n) from DiscountDO n where idDiscount > 0 and (:supplier is null or (Upper(n.supplier) LIKE %:supplier%)) and (:autor is null or (Upper(n.lastUserModifier) LIKE %:autor%)) and (CAST(:startdate AS java.time.LocalDate) is null or n.startDate >= :startdate) and (CAST(:enddate AS java.time.LocalDate) is null or n.endDate <=:enddate)")
    Long getNumberRow(@Param("supplier") String supplier,
                       @Param("autor") String autor,
                       @Param("startdate") LocalDateTime startdate,
                       @Param("enddate") LocalDateTime enddate);

    @Query("select d.idDiscount from  DiscountDO d where d.typeDiscount = :typeDiscount and (CAST(d.endDate AS java.time.LocalDate) >= :todayDate) and  (:idCategory is null or  d.categoryDO.idCategory = :idCategory ) and (:idSubCategory is null or d.subCategoryDO.idSubCategory = :idSubCategory) and d.idDiscount in(:idDiscount)")
    List<Long> getIdDiscountByParameters(@Param("typeDiscount") String typeDiscount, @Param("todayDate")LocalDate todayDate, @Param("idCategory") Long idCategory, @Param("idSubCategory") Long idSubCategory, @Param("idDiscount") List<Long> idDiscount);


    @Query("select n from DiscountDO n \n" +
            "ORDER BY n.lastModification DESC")
    List<DiscountDO> findAllDiscountDO();

    @Query("select l.levelRh from DiscountDO l where l.idDiscount = :idDiscount")
    Long getLevetDiscount(@Param("idDiscount") Long idDiscount);

}
