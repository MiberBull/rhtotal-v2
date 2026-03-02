package mx.com.axity.persistence;

import mx.com.axity.model.PlanCoverageDO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanCoverageDAO extends CrudRepository<PlanCoverageDO, Long> {
    @Query("SELECT e\n"+
            "FROM PlanCoverageDO e INNER JOIN InsuranceDO i ON e.idInsurance = i.idInsurance \n"+
            "WHERE e.idInsurance.idInsurance = :idInsurance\n" +
            "ORDER BY e.lastModification DESC")
    Page<PlanCoverageDO> findAllCoverage(Pageable pageable, @Param("idInsurance") Long idInsurance);

    @Query("SELECT count(e)\n"+
            "FROM PlanCoverageDO e INNER JOIN InsuranceDO i ON e.idInsurance = i.idInsurance \n"+
            "WHERE e.idInsurance.idInsurance = :idInsurance ")
    Long getNumberRowCoverage(@Param("idInsurance") Long idInsurance);

    @Query("SELECT c.description,c.level,c.sumAssured,c.deductibles,c.coInsurance FROM PlanCoverageDO c WHERE c.status= 'A' and c.idInsurance.idInsurance = :idInsurance")
    List<Object[]> getCoverageInsurance(@Param("idInsurance") Long idInsurance);
}
