package mx.com.axity.persistence;

import mx.com.axity.model.CustomerDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomerDAO extends CrudRepository<CustomerDO,Long> {
    @Query("select n from CustomerDO n where n.active = true and(:name is null or n.name = :name) order by n.lastModification DESC")
    Page<CustomerDO> findAllByOrderByLastModificationAsc(Pageable pageable, @Param("name") String name);

    @Query("select n from CustomerDO n where n.status ='A' and n.active = true ORDER BY n.name ASC")
    List<CustomerDO> findAllOrden();

    @Query("select count(n) from CustomerDO n where n.active=true")
    Long getNumberRow();

}
