package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.PaysheetRawRequestTO;
import mx.com.axity.commons.to.UserTO;
import mx.com.axity.model.EmployeeDO;
import mx.com.axity.services.facade.IUserFacade;
import mx.com.axity.services.service.IFintechService;
import mx.com.axity.services.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.RestTemplate;

@Component
public class UserFacadeImpl implements IUserFacade {

    @Autowired
    private IFintechService fintechService;

    @Autowired
    private IUserService userService;

    @Override
    public UserTO getUserById(Long idUser) {
        try {
            return this.fintechService.getUserById(idUser);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public Object[] getDataForQuerySico(Long idUser) {
        try {
            return this.userService.getDataForQuerySico(idUser);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public String buildUrlForUser(Object[] data, int type) {
        try {
            return this.userService.buildUrlForUser( data,type );
        } catch ( BusinessException e ) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public EmployeeDO getEmployeByIdUser(long idUser) {
        try{
            return this.userService.getEmployeByIdUser(idUser);
        } catch ( BusinessException e ){
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public String buildUrlForUserWithMonth(Object[] data, int type, String mounth) {
        try{
            return this.userService.buildUrlForUserWithMonth(data,type,mounth);
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }
    
	@Override
    public Long getIdEmployeeByIdUser(Long id) {
        try{
            return this.userService.getIdEmployeeByIdUser(id);
        } catch ( BusinessException e ){
            throw new BusinessException(e.getMessage(),e);
        }
    }
}
