package mx.com.axity.services.service.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.model.FintechVeloCashDO;
import mx.com.axity.persistence.FintechVeloCashDAO;
import mx.com.axity.services.service.IFintechVeloCahsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Service
public class FintechVeloCahsImpl implements IFintechVeloCahsService {

    @Autowired
    FintechVeloCashDAO veloCashDAO;

    @Override
    public void saveFintechVeloCahs(FintechVeloCashDO fintechVeloCashDO) {
        try {
            this.veloCashDAO.save(fintechVeloCashDO);
        } catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public List<Long> findByPeriod(long idEmployee) {

        try {
            return this.veloCashDAO.findByPeriod( idEmployee );
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }

    }

    @Override
    public Long getNextSeqVelocahs() {
        try {
            return this.veloCashDAO.getNextSeqVelocahs();
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public String generateFolio(String section, Long idEmployee) {

        var length = 6;
        var dateTime = LocalDateTime.now();
        StringBuilder str = new StringBuilder();

        str.append(section);
        str.append(String.format("%0"+length+"d",idEmployee));
        str.append(String.format("%0"+length+"d",this.getNextSeqVelocahs()));
        str.append(dateTime.getYear());
        var month = String.valueOf(dateTime.getMonthValue());
        str.append( month.length() == 2 ? month : String.format("%0"+2+"d",dateTime.getMonthValue()) );
        var day = String.valueOf(dateTime.getDayOfMonth());
        str.append( day.length() == 2 ? day : String.format("%0"+2+"d",dateTime.getDayOfMonth()) );
        var hour = String.valueOf(dateTime.getHour());
        str.append(hour.length() == 2 ? hour : String.format("%0"+2+"d",dateTime.getHour()));
        var minute = String.valueOf(dateTime.getMinute());
        str.append(minute.length() == 2 ? minute : String.format("%0"+2+"d",dateTime.getMinute()));
        var second = String.valueOf(dateTime.getSecond());
        str.append(second.length() == 2 ? second : String.format("%0"+2+"d",dateTime.getSecond()));
        return str.toString();

    }

    @Override
    public List<Long> findByPeriod(long idEmployee, LocalDateTime startPeriod, LocalDateTime endPeriod) {

        try {
            return this.veloCashDAO.findByPeriod( idEmployee,startPeriod,endPeriod );
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e);
        }

    }

}
