package mx.com.axity.services.service.impl;

import mx.com.axity.persistence.IUnlockingUserByTimeDAO;
import mx.com.axity.services.service.IUnlockingUserByTimeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class UnlockingUserByTimeServicesImpl implements IUnlockingUserByTimeService {

    @Autowired
    IUnlockingUserByTimeDAO unlockingUserByTimeDAO;

    static final Logger LOG = LogManager.getLogger(UnlockingUserByTimeServicesImpl.class);

    @Override
    public void saveOrUpdateUnlockServices(String timeUnLock) {
        LocalDateTime timeblock = LocalDateTime.now().minusMinutes(Long.valueOf(timeUnLock));
        LOG.info("Se obtiene Date actual para proceso de desbloqueo "+ timeUnLock);
        LOG.info("Se Agregan minutos de parametro para realizar la validación"+timeblock);
         this.unlockingUserByTimeDAO.updateStatusInUserBlock(timeblock);
         this.unlockingUserByTimeDAO.updateStatusInRolBlock(timeblock);
    }
}
