package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.HeadersGenericTO;
import mx.com.axity.services.facade.IGenericTasksFacade;
import mx.com.axity.services.service.IGenericTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GenericTasksFacadeImpl implements IGenericTasksFacade {

    @Autowired
    IGenericTasksService genericTasksServise;

    @Override
    public HeadersGenericTO getHeader(String nameHeader) {
        try {
            Optional.ofNullable(nameHeader).orElseThrow();
            return genericTasksServise.getHeader(nameHeader);
        }catch (Exception e){
            throw  new BusinessException(e.getMessage(),e);
        }
    }
}
