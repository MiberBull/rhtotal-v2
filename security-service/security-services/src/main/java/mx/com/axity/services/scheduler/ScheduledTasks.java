package mx.com.axity.services.scheduler;

import mx.com.axity.services.facade.IParameterFacade;
import mx.com.axity.services.facade.IUnlockingUserByTimeFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTasks {
    static final Logger LOG = LogManager.getLogger(ScheduledTasks.class);

    @Autowired
    IUnlockingUserByTimeFacade unlockingUserByTimeFacade;

    @Autowired
    IParameterFacade parameterFacade;

    static final String nameParameter="timeUnLock";

    @Scheduled(cron = "0 * * * * ?")
    void executeUnlockUserByPassWrong(){
        LOG.info("Inicia Ejecución de SCHEDULE : CAMBIAR  ESTATUS DE USUARIO",LocalDateTime.now());
        var ParameterTime=parameterFacade.getParameter(nameParameter);
        LOG.info("Obtiene Parametro de timeUnlock "+ParameterTime);
        unlockingUserByTimeFacade.saveOrUpdateUnlockFacade(ParameterTime);

        LOG.info("TERMINA PROCESO DE ACTUALIZACIÓN DE USUARIOS BLOQUEADOS POR INTENTOS FALLIDOS", LocalDateTime.now());
    }
}
