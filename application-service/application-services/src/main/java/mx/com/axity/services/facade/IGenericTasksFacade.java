package mx.com.axity.services.facade;

import mx.com.axity.commons.to.InfoExcelTO;
import mx.com.axity.commons.to.totree.ExcelGenericFormatExportTO;
import mx.com.axity.commons.to.HeadersGenericTO;


public interface IGenericTasksFacade {
    ExcelGenericFormatExportTO sendExcel(int section);
    HeadersGenericTO getHeader(String nameHeader);
    ExcelGenericFormatExportTO getExcelUsers(InfoExcelTO infoExcelUsersTO);
}
