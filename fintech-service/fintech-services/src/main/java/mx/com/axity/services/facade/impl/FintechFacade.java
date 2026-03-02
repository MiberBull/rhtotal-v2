package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.*;
import mx.com.axity.model.FintechMyAdvanceDO;
import mx.com.axity.services.facade.IFintechFacade;
import mx.com.axity.services.service.IFintechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class FintechFacade implements IFintechFacade {

    @Autowired
    private IFintechService fintechService;
    
    @Override
    public UserTO getUserById(Long idUser) {
        try {
            return this.fintechService.getUserById(idUser);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public List<FintechTableTO> getPagedFintechMyAdvance(int page, String typeNotification, String firstName, String lastName, String secondSurname, String folioRequest, String requestDate) {
        try{
            Optional.ofNullable(typeNotification).orElseThrow();
            return this.fintechService.getPagedFintechMyAdvance(page, typeNotification,firstName,lastName,secondSurname,folioRequest,requestDate);

        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<FintechTableTO> getPagedFintechVeloCash(int page, String typeNotification,String firstName,String lastName,
                                                           String secondSurname,String folioRequest,String requestDate) {
        try{
            Optional.ofNullable(typeNotification).orElseThrow();
            return this.fintechService.getPagedFintechVeloCash(page, typeNotification,firstName,lastName,secondSurname,folioRequest,requestDate);
        } catch (Exception e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public FintechTableTO getOneFintechMyAdvance(int fintechOne,String status) {
        try{
            Optional.of(fintechOne).map(t -> t > 0).orElseThrow();
            return this.fintechService.getOneFintechMyAdvance(fintechOne,status);

        } catch(Exception e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public FintechTableTO getOneFintechVeloCash(int fintechOneVeloCash,String status) {
        try{
            Optional.of(fintechOneVeloCash).map(t -> t > 0).orElseThrow();
            return this.fintechService.getOneFintechVeloCash(fintechOneVeloCash,status);
        }catch (Exception e){
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public ResponseSwapTO updateStatuChangeMyAdvance(int idFintech, String statusFintech,String lastUserModifier,String lastModification,String reasonRejection) {
        try{
            Optional.ofNullable(statusFintech).orElseThrow();
            return this.fintechService.updateStatuChangeMyAdvance(idFintech, statusFintech,lastUserModifier,lastModification,reasonRejection);

        }catch(Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public ResponseSwapTO updateStatuChangeVeloCash(int idFintech, String statusFintech, String lastUserModifier, String lastModification,String reasonRejection) {
        try{
            Optional.ofNullable(statusFintech).orElseThrow();
            return this.fintechService.updateStatuChangeVeloCash(idFintech, statusFintech, lastUserModifier, lastModification,reasonRejection);
        }catch(Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public CountRowTO getNumberRowAdvance(String status, String firstName, String lastName, String secondSurname, String folioRequest, String requestDate) {
        return this.fintechService.getNumberRowAdvance(status,firstName, lastName, secondSurname, folioRequest, requestDate);
    }

    @Override
    public CountRowTO getNumberRowVeloCash(String status, String firstName, String lastName, String secondSurname, String folioRequest, String requestDate) {
        return this.fintechService.getNumberRowVeloCash(status,firstName,lastName,secondSurname,folioRequest,requestDate);
    }

    @Override
    public void setClickedInfoSwap(CountClickTO click) {
        this.fintechService.setClickedInfoSwap(click);
    }

    @Override
    public void saveFintechMyAdvanced(FintechMyAdvanceDO fintech) {
        this.fintechService.saveFintechMyAdvanced(fintech);
    }

    @Override
    public List<Long> findByPeriod(long idEmployee) {
        return this.fintechService.findByPeriod(idEmployee);
    }

    @Override
    public List<Long> findByPeriod(long idEmployee, LocalDateTime startPeriod, LocalDateTime endPeriod) {
        return this.fintechService.findByPeriod(idEmployee,startPeriod,endPeriod);
    }

}
