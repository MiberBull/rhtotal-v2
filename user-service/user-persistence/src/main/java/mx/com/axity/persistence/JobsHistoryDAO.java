package mx.com.axity.persistence;

import mx.com.axity.model.JobsHistoryDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobsHistoryDAO extends CrudRepository<JobsHistoryDO,Long> {

    @Query(" SELECT j FROM JobsHistoryDO j WHERE j.idUser = :idUser ORDER BY j.idJobHistory DESC")
    public List<JobsHistoryDO> getJobsHistoryByIdUser(@Param("idUser") Long idUser);


}
