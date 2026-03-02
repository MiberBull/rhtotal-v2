package mx.com.axity.services.facade.impl;

import mx.com.axity.commons.exceptions.BusinessException;
import mx.com.axity.commons.to.InfoExcelTO;
import mx.com.axity.commons.to.totree.ExcelGenericFormatExportTO;
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
    public ExcelGenericFormatExportTO sendExcel(int section)  {
       try {
           Optional.of(section).map(t-> t > 0).orElseThrow();
          return this.genericTasksServise.sendExcel(section);
       }catch (Exception  e){
           throw new BusinessException(e.getMessage(),e);
       }
    }

    @Override
    public HeadersGenericTO getHeader(String nameHeader) {
        try {
            Optional.ofNullable(nameHeader).orElseThrow();
            return genericTasksServise.getHeader(nameHeader);
        }catch (Exception e){
            throw  new BusinessException(e.getMessage(),e);
        }
    }

    @Override
    public ExcelGenericFormatExportTO getExcelUsers(InfoExcelTO infoExcelUsersTO) {
        try {
            //Optional.of(infoExcelUsersTO).map(t-> t > 0).orElseThrow();
            return this.genericTasksServise.getExcelUsers(infoExcelUsersTO);
        }catch (Exception  e){
            throw new BusinessException(e.getMessage(),e);
        }
    }
}
