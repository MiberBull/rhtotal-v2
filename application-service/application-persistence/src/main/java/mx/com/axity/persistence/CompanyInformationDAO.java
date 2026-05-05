package mx.com.axity.persistence;

import mx.com.axity.model.CompanyInformationDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyInformationDAO extends CrudRepository<CompanyInformationDO, Long> {
    @Query("select ci from CompanyInformationDO ci where ci.active = true and ci.nameCompanyInformation = :nameCompanyInformation")
    CompanyInformationDO getCompanyInformation(@Param("nameCompanyInformation") String nameCompanyInformation );
}
