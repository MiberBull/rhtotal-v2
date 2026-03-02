package mx.com.axity.services.service.impl;

import annotations.ExelAnnotations;
import mx.com.axity.commons.to.ExcelFormatExportTO;
import mx.com.axity.commons.to.RolesUserTO;
import mx.com.axity.commons.util.enumerators.EnumHeadersRole;
import mx.com.axity.model.CatalogoRolDO;
import mx.com.axity.model.RolesUserDO;
import mx.com.axity.persistence.ParameterDAO;
import mx.com.axity.persistence.RolesUserDAO;
import mx.com.axity.services.service.IRoleExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class RoleExcelServiceImpl implements IRoleExcelService {

    @Autowired
    RolesUserDAO rolesUserDAO;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ParameterDAO parameterDAO;

    @Override
    public ExcelFormatExportTO sendExcel() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        var roles = (List<RolesUserDO>) this.modelMapper.map(this.rolesUserDAO.findAllRolesUserDO(), new TypeToken<List<RolesUserDO>>() {
        }.getType());
        var newListRoles = new  ArrayList<RolesUserDO>();
        for(RolesUserDO rol : roles){
            if(!(rol.getIdRol().getIdRol().longValue()==1L)){
               newListRoles.add(rol);
            }
        }
        roles.clear();
        roles.addAll(newListRoles);
        var parameter = this.parameterDAO.findByNameParameter(EnumHeadersRole.HEADERS_ROL_EXCEL.getParameterHeaders());
        String[] cadena = parameter.split(",");
        var headers = Arrays.asList(cadena);
        return new ExcelFormatExportTO(createExcelBase64(roles, headers));
    }

    @Override
    public String createExcelBase64(List list, List headers) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        var getter = new ArrayList<>();
        var workbook = new XSSFWorkbook();
        var sheet = workbook.createSheet("Roles");
        var headerRow = sheet.createRow(0);

        XSSFCellStyle headerCellStyle = getCellStyleExcelRole(workbook);
        getMethodNameRole(Arrays.asList(list.get(0).getClass().getDeclaredFields()), getter);
        headersExcelRole(headers, headerRow, headerCellStyle);
        bodyExcelRole(list, getter, sheet);
        for (int i =0;i < headers.size();i++){
            sheet.autoSizeColumn(i);
        }
        return  new org.apache.commons.codec.binary.Base64().encodeToString(getBytesExcelRole(workbook));
    }

    @Override
    public byte[] getBytesExcelRole(XSSFWorkbook workbook) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        byte[] bytes = outputStream.toByteArray();
        workbook.close();
        outputStream.close();
        return bytes;
    }


    private void bodyExcelRole(List list, ArrayList<Object> getter, XSSFSheet sheet) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int rowNum =1;
        for (Object typeClass : list) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < getter.size(); i++) {
                Method getNameMethod = typeClass.getClass().getDeclaredMethod(String.valueOf(getter.get(i)));
                String value = String.valueOf(getNameMethod.invoke(typeClass, null));
                if(getNameMethod.getName().equalsIgnoreCase("getStatus")){
                    value = (value.equalsIgnoreCase("A")?"Activo":
                            value.equalsIgnoreCase("I")?"Inactivo":
                                    value.equalsIgnoreCase("N")?"Nuevo":"");
                }
                row.createCell(i).setCellValue(value);
            }
        }
    }


    private void getMethodNameRole(List<Field> fieldList, ArrayList<Object> getter) {
        fieldList.forEach(t -> {
            if (!t.getAnnotation(ExelAnnotations.class).getMethod().equals("N/R"))
                getter.add(t.getAnnotation(ExelAnnotations.class).getMethod());
        });
    }

    @Override
    public void headersExcelRole(List headers, XSSFRow headerRow, XSSFCellStyle headerCellStyle) {
        for (int i = 0; i < headers.size();i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(String.valueOf(headers.get(i)));
            cell.setCellStyle(headerCellStyle);
        }

    }


    @Override
    public XSSFCellStyle getCellStyleExcelRole(XSSFWorkbook workbook) {
        var headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 11);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        var headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        return headerCellStyle;
    }
}