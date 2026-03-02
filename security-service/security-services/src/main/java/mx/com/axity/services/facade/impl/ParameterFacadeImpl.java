package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.services.facade.IParameterFacade;
import mx.com.axity.services.service.IParameterService;
import mx.com.axity.services.service.impl.ParameterServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ParameterFacadeImpl implements IParameterFacade {

    static final Logger LOG = LogManager.getLogger(ParameterFacadeImpl.class);

    @Autowired
    IParameterService ParameterService;

    @Override
    public String getParameter(String parameter)  {
        try {
            return this.ParameterService.getParameter(parameter);
        }catch (Exception e){
            LOG.info("Error al obtener parametro [" + e.getMessage() + "]");
            throw new BusinessException(Constants.CONTROLLED_MENSSAGE,e);
        }
    }
}
