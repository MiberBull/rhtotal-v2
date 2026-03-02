package mx.com.axity.persistence;

import mx.com.axity.model.SubCategoryDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubCategoryDAO extends CrudRepository<SubCategoryDO,Long> {
    @Query("select t from SubCategoryDO t where t.categoryDO.idCategory = :idCategory order by t.subcategory asc")
    List<SubCategoryDO> finByAllSubCategoryByIdCategory(@Param("idCategory") Long idCategory);


    @Query("select distinct c from SubCategoryDO c " +
            "inner join DiscountDO d on (c.idSubCategory = d.subCategoryDO.idSubCategory ) " +
            "inner join NotificationAssignmentDO n on (n.idNotification = d.idDiscount) " +
            "where n.typeNotification ='D' and n.idUser = :idUser  " +
            "and d.startDate <= current_date and d.endDate >= current_date " +
            "and d.typeDiscount = :typeDesc  and  d.status = 'A' " +
            "and d.categoryDO.idCategory =:idCategory  order by c.subcategory" )
    List<SubCategoryDO> finByAllSubCategoryByIdCategoryAndUser(@Param("idCategory") Long idCategory,@Param("idUser") Long idUser,@Param("typeDesc") String typeDesc);

}
