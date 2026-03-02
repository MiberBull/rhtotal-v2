package mx.com.axity.services.facade;

import mx.com.axity.model.FintechVeloCashDO;

import java.time.LocalDateTime;
import java.util.List;

public interface IFintechVelocahsFacade {

    void saveFintechVeloCahs(FintechVeloCashDO fintechVeloCashDO);

    List<Long> findByPeriod(long idEmployee);

    String generateFolio( String section,Long idEmployee );

    List<Long> findByPeriod(long idEmployee,LocalDateTime startPeriod,LocalDateTime endPeriod);

}
