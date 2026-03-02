package mx.com.axity.services.scheduler;

import mx.com.axity.commons.to.FintechMyAdvanceTO;
import mx.com.axity.commons.to.FintechVeloCashTO;
import mx.com.axity.commons.to.ResponseSwapTO;
import mx.com.axity.persistence.ParameterDAO;
import mx.com.axity.services.facade.IGenericTasksFacade;
import mx.com.axity.services.facade.ISchedulesEnviosSolPendFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduledTask {
    static final Logger LOG = LogManager.getLogger(ScheduledTask.class);

    @Autowired
    ISchedulesEnviosSolPendFacade iSchedulesEnviosSolPendFacade;

    @Autowired
    IGenericTasksFacade genericTasksFacade;

    @Autowired
    ParameterDAO parameterDao;

/*
    @Scheduled(cron = "0 * * * * ?")
    void executSolStatusPendingFintech(){
        LOG.info("FINTECH   :::::INICIA ENVIO DE SOLICITUDES PENDIENTES");
        String paramaterDate = this.parameterDao.getParameterFromDb("timeFintch");
        LocalDateTime timeblock = LocalDateTime.now().minusMinutes(Integer.parseInt(paramaterDate));

        LOG.info("SHELL SE EJECUTA CADA "+timeblock +"MINUTOS");
        List<FintechMyAdvanceTO> ids = this.iSchedulesEnviosSolPendFacade.getSolicicitudesPendientesPayshetAdvanced(timeblock);
        LOG.info("SE OBTIENEN FOLIOS DE FintechMyAdvanceTO "+ids.size());
        List<FintechVeloCashTO> idsVeloCash = this.iSchedulesEnviosSolPendFacade.getSolicitudesPendientesFintechVeloCash(timeblock);
        LOG.info("SE OBTIENEN FOLIOS DE ADELANTO DE FintechVeloCashTO "+idsVeloCash.size());

        if(ids.size()>0 || idsVeloCash.size()>0) {
            LOG.info("SE INICIA ENVIO DE SOLCITUDES A FINTECH DE ACUERDO A HORARIO ESTABLECIDO");
            ResponseSwapTO response = iSchedulesEnviosSolPendFacade.sendSolPendientes(ids, idsVeloCash);
        } else{
            LOG.info("NO HAY SOLICITUDES EN ESPERA");
        }
    }*/
}
