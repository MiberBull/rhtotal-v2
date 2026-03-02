package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.FintechMyAdvanceTO;
import mx.com.axity.services.facade.IFintechFacade;
import mx.com.axity.services.service.IFintechService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

import java.util.List;

@Component
public class FintechFacadeImpl implements IFintechFacade {

    @Autowired
    IFintechService fintechService;

    @Override
    public List<FintechMyAdvanceTO> getPagedFintechMyAdvance(int page, String typeNotification) {
        try{
            Optional.ofNullable(typeNotification).orElseThrow();
            return this.fintechService.getPagedFintechMyAdvance(page, typeNotification);

        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
}
