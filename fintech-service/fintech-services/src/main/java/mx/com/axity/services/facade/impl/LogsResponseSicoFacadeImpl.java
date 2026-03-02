package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.LogsResponseSicoTO;
import mx.com.axity.model.LogsResponseSicoDO;
import mx.com.axity.services.facade.ILogsResponseSicoFacade;
import mx.com.axity.services.service.ILogsResponseSicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogsResponseSicoFacadeImpl implements ILogsResponseSicoFacade {

    @Autowired
    private ILogsResponseSicoService logsResposeSico;

    @Override
    public void saveLogsResponseSicoTO(LogsResponseSicoTO responseSico) {
        try {
            this.logsResposeSico.saveLogsResponseSicoTO(responseSico);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }
}
