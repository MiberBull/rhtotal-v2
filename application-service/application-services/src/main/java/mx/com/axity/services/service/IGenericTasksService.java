package mx.com.axity.services.service;

import mx.com.axity.commons.to.InfoExcelTO;
import mx.com.axity.commons.to.totree.ExcelGenericFormatExportTO;
import mx.com.axity.commons.to.HeadersGenericTO;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IGenericTasksService<T>{
    ExcelGenericFormatExportTO sendExcel(int section) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException;
    String createExelBase64(List<T> list, List<String> headers,String namePage) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;
    HeadersGenericTO getHeader(String nameHeader);

    ExcelGenericFormatExportTO getExcelUsers(InfoExcelTO infoExcelUsersTO);
}
