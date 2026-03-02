package mx.com.axity.services.service;

import mx.com.axity.commons.to.ExcelFormatExportTO;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IRoleExcelService<T> {
    ExcelFormatExportTO sendExcel() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException;
    String createExcelBase64(List<T> list,List<String> headers) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException;
    byte[] getBytesExcelRole(XSSFWorkbook workbook) throws IOException;
    void headersExcelRole(List headers, XSSFRow headerRow, XSSFCellStyle headerCellStyle);
    XSSFCellStyle getCellStyleExcelRole(XSSFWorkbook workbook);
}
