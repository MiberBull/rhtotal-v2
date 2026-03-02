package mx.com.axity.services.service;
import mx.com.axity.commons.to.totree.ExcelGenericFormatExportTO;
import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GenericTasksServiceTest extends BaseTest {
    @Test
    public void getHeaderTest(){
      this.genericTasksServiceTest.getHeader("headersBanners");
    }

    @Test
    @Ignore(value = "cuando se ejecuta aleatorio falla ")
    public void excel_Correct_Test() throws NoSuchMethodException, IOException, IllegalAccessException, InvocationTargetException {
        ExcelGenericFormatExportTO excelGenericFormatExportTO = this.genericTasksServiceTest.sendExcel(7);
        Assert.assertNotNull(excelGenericFormatExportTO);
    }
}
