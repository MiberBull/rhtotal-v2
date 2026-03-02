package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.util.Constants;
import mx.com.axity.services.facade.IParameterFacade;
import mx.com.axity.services.service.IParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class IParameterFacadeImpl implements IParameterFacade {

    @Autowired
    IParameterService parameterService;

    @Override
    public String getParameterFromDb(String nameParameter) {
        try {
            Optional.ofNullable(nameParameter).orElseThrow();
            return parameterService.getParameterFromDb(nameParameter);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public Map<String,LocalDateTime> getLocalDateTime() {
        try {
            Map<String,LocalDateTime> info = new HashMap<>();
            info.put(Constants.DATE,parameterService.getLocalDateTime());
            return info;
        } catch (Exception e) {
            throw  new BusinessException(e.getMessage(),e);
        }
    }
}
