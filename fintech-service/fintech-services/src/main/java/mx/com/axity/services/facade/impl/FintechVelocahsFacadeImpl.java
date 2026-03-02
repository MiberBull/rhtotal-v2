package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.model.FintechVeloCashDO;
import mx.com.axity.services.facade.IFintechVelocahsFacade;
import mx.com.axity.services.service.IFintechVeloCahsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class FintechVelocahsFacadeImpl implements IFintechVelocahsFacade {

    @Autowired
    public IFintechVeloCahsService fintechVeloCahsService;

    @Override
    public void saveFintechVeloCahs(FintechVeloCashDO fintechVeloCashDO) {
        try {
            this.fintechVeloCahsService.saveFintechVeloCahs(fintechVeloCashDO);
        } catch ( BusinessException e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public List<Long> findByPeriod(long idEmployee) {

        try {
            return this.fintechVeloCahsService.findByPeriod(idEmployee);
        } catch ( BusinessException e) {
            throw new BusinessException(e.getMessage(),e);
        }

    }

    @Override
    public String generateFolio(String section, Long idEmployee) {
        try {
            return this.fintechVeloCahsService.generateFolio(section,idEmployee);
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public List<Long> findByPeriod(long idEmployee, LocalDateTime startPeriod, LocalDateTime endPeriod) {

        try {
            return this.fintechVeloCahsService.findByPeriod(idEmployee,startPeriod,endPeriod);
        } catch ( BusinessException e) {
            throw new BusinessException(e.getMessage(),e);
        }

    }


}
