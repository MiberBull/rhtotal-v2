package mx.com.axity.services.service;

import mx.com.axity.model.FintechVeloCashDO;
import java.time.LocalDateTime;
import java.util.List;

public interface IFintechVeloCahsService {

    void saveFintechVeloCahs(FintechVeloCashDO fintechVeloCashDO);

    List<Long> findByPeriod(long idEmployee);

    Long getNextSeqVelocahs();

    String generateFolio( String section,Long idEmployee );

    List<Long> findByPeriod(long idEmployee,LocalDateTime startPeriod,LocalDateTime endPeriod);


}
