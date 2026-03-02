package mx.com.axity.persistence;

import mx.com.axity.model.CategoryDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryDAO extends CrudRepository<CategoryDO,Long> {
    @Query("select n from CategoryDO n order by n.category  asc")
    List<CategoryDO> getAllOrderCategory();


    @Query("select distinct c from CategoryDO c " +
            "inner join DiscountDO d on (c.idCategory = d.categoryDO.idCategory) " +
            "inner join NotificationAssignmentDO n on (n.idNotification = d.idDiscount) " +
            "where n.typeNotification ='D' and n.idUser = :idUser  " +
            "and d.startDate <= current_date and d.endDate >= current_date " +
            "and d.typeDiscount = :typeDiscount and d.status = 'A'" +
            "order by c.category  asc")
    List<CategoryDO> getOrderCategoryIdUser(@Param("idUser") long idUser, @Param("typeDiscount")String typeDiscount);
}
