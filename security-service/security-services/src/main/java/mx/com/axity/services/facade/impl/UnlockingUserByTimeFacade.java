package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.services.facade.IUnlockingUserByTimeFacade;
import mx.com.axity.services.service.IUnlockingUserByTimeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnlockingUserByTimeFacade implements IUnlockingUserByTimeFacade {

    static final Logger LOG = LogManager.getLogger(UnlockingUserByTimeFacade.class);

    @Autowired
    IUnlockingUserByTimeService unlockingUserByTimeService;


    @Override
    public void saveOrUpdateUnlockFacade(String timeBlock) {
        try {
            LOG.info("Se solicita actualización de estatus a Capa DAO" + timeBlock);
            unlockingUserByTimeService.saveOrUpdateUnlockServices(timeBlock);

        }catch (Exception e){
            LOG.info("Error al Actualizar Estaus de usuarios bloqueados [" + e.getMessage() + "]");
            throw new BusinessException(Constants.CONTROLLED_MENSSAGE, e);
        }

    }
}
