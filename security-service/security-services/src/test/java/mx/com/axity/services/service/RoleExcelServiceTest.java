package mx.com.axity.services.service;

import mx.com.axity.model.RolesUserDO;
import mx.com.axity.services.BaseTest;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class RoleExcelServiceTest extends BaseTest {

    // TODO:VER EL CONFLICTO QUE MANDA ESTA PRUEBA UNITARIA
    @Test
    @Ignore("test incorrect")
    public void excel_Send_Correct_Test() throws NoSuchMethodException, IOException, IllegalAccessException, InvocationTargetException {
            var testCorrectExcelFormatExportTO = this.roleExcelService.sendExcel();
            Assert.assertNotNull(testCorrectExcelFormatExportTO);
    }


    @Test(expected = NullPointerException.class)
    public void create_Error_Excel_Base64_Test() throws InvocationTargetException, NoSuchMethodException, IOException, IllegalAccessException {
        this.roleExcelService.createExcelBase64(null, null);
    }

    @Test
    public void get_Correct_Bytes_Excel_Role_Test() throws IOException {
        var workbook = new XSSFWorkbook();
        byte[] testCorrectBytesExcelRole = this.roleExcelService.getBytesExcelRole(workbook);
        Assert.assertNotNull(testCorrectBytesExcelRole);
    }

    @Test(expected = NullPointerException.class)
    public void get_Error_Bytes_Excel_Role_Test() throws IOException {
        this.roleExcelService.getBytesExcelRole(null);
    }

    @Test(expected = Exception.class)
    public void headers_Error_Excel_Role_Test() {
        this.roleExcelService.headersExcelRole(null, null, null);
    }

    @Test
    public void get_Correct_Cell_Style_Excel_Role_Test() {
        var testCorrectCellStyleExcelRole = this.roleExcelService.getCellStyleExcelRole(new XSSFWorkbook());
        Assert.assertNotNull(testCorrectCellStyleExcelRole);
    }

    // TODO:VER EL CONFLICTO QUE MANDA ESTA PRUEBA UNITARIA
    @Test
    @Ignore("test incorrect")
    public void create_Correct_Excel_Base64_Test() throws InvocationTargetException, NoSuchMethodException, IOException, IllegalAccessException {
        var rolesUser = new RolesUserDO();
        rolesUser.setIdRolAssig(4L);
        rolesUser.setNameRol("test");
        rolesUser.setName("test");
        rolesUser.setLastName("test");
        rolesUser.setmLastName("test");
        rolesUser.setActive(Boolean.TRUE);
        rolesUser.setPhone("23242343");
        rolesUser.setEmail("test");
        var list = new ArrayList<RolesUserDO>();
        list.add(rolesUser);

        var headers = new ArrayList<String>();
        headers.add("Tipo de Administrador");
        headers.add("Nombre(s)");
        headers.add("Apellido Paterno");
        headers.add("Apellido Materno");
        headers.add("Teléfono");
        headers.add("Email");
        headers.add("Estatus");

        var testCorrectExcelBase64 = this.roleExcelService.createExcelBase64(list, headers);
        Assert.assertNotNull(testCorrectExcelBase64);
    }
    @Test(expected = NullPointerException.class)
    public void get_Error_Cell_Style_Excel_Role_Test() {
        this.roleExcelService.getCellStyleExcelRole(null);
    }


}