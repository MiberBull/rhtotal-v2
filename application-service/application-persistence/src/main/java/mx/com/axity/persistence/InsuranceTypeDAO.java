package mx.com.axity.persistence;

import mx.com.axity.model.InsuranceTypeDO;
import org.springframework.data.repository.CrudRepository;

public interface InsuranceTypeDAO extends CrudRepository<InsuranceTypeDO,Long> {

}
