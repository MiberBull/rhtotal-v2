package mx.com.axity.services.service;

import mx.com.axity.commons.to.SicoEmployeeSearchRequestTO;
import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PaysheetSicoServiceTest extends BaseTest {
    protected void setUp() {

    }

    @Test
    public void shouldGetDataFromSico() {

        String resultMock = "{\"total\":1,\"to\":100,\"prev_page_url\":null,\"per_page\":100,\"next_page_url\":null,\"last_page\":1,\"from\":1,\"data\":[{\"personal\":{\"rfc\":\"LAGS800522RTM\",\"paisNacimiento\":\"MEXICANA\",\"numCelular\":\"\",\"nss\":\"01236547789\",\"nombres\":\"SAMIR\",\"genero\":\"M\",\"fechaNacimiento\":\"22/05/1980\",\"estadoNacimiento\":\"VERACRUZ\",\"estadoCivil\":\"\",\"emailPersonal\":\"laragtz@prueba.com\",\"curp\":\"LAGS800522HVZRTM04\",\"apellidoPaterno\":\"LARA\",\"apellidoMaterno\":\"GUTIERREZ\"},\"direccion\":{\"numeroInterior\":\"A\",\"numeroExterior\":\"123\",\"estado\":\"PUEBLA\",\"delegacionMunicipio\":\"PUEBLA\",\"cp\":\"72014\",\"colonia\":\"BARRANCA HONDA\",\"calle\":\"13 PONIENTE\"}}],\"current_page\":1}";

        String data = this.paysheetsicoService.getUserDataByEmail("laragtz@prueba.com");

        Assert.assertEquals(resultMock, data);

    }

    @Test
    public void shouldCreateUrlForGetData() {

        String expectedUrl =
                "https://nominaenlanube.com:8085/api-nomen/v1/get_empleado_general?nombres=alberto+mu%C3%B1os&apellidoMaterno=luna";
        SicoEmployeeSearchRequestTO searchRequestTO = new SicoEmployeeSearchRequestTO();

        List<String> names = new ArrayList<>();

        names.add("AlbeRtO");
        names.add("MuÑos");

        searchRequestTO.setNames(names);
        searchRequestTO.setLastMotherName("luna");

        String result = this.paysheetsicoService.buildSearchUrl(searchRequestTO);

        Assert.assertEquals(expectedUrl, result);
    }

    @Test
    public void shouldCreateUrlWithLastName() {

        String expectedUrl =
                "https://nominaenlanube.com:8085/api-nomen/v1/get_empleado_general?nombres=alberto+mu%C3%B1os&apellidoPaterno=alvarez";

        SicoEmployeeSearchRequestTO searchRequestTO = new SicoEmployeeSearchRequestTO();
        List<String> names = new ArrayList<>();
        names.add("alberto");
        names.add("muños");

        searchRequestTO.setNames(names);


        searchRequestTO.setLastName("alvarez");

        String result = this.paysheetsicoService.buildSearchUrl(searchRequestTO);


        Assert.assertEquals(expectedUrl, result);
    }


    @Test public void shouldCreateUrlWithLastMotherName() {
        String expectedUrl =
                "https://nominaenlanube.com:8085/api-nomen/v1/get_empleado_general?nombres=alberto+mu%C3%B1os&apellidoMaterno=luna";

        SicoEmployeeSearchRequestTO searchRequestTO = new SicoEmployeeSearchRequestTO();
        List<String> names = new ArrayList<>();
        names.add("alberto");
        names.add("muños");

        searchRequestTO.setLastMotherName("luna");
        searchRequestTO.setNames(names);

        String result = this.paysheetsicoService.buildSearchUrl(searchRequestTO);


        Assert.assertEquals(expectedUrl, result);

    }

    @Test public void shouldCreateUrlWithBirthday() {
        String expectedUrl =
                "https://nominaenlanube.com:8085/api-nomen/v1/get_empleado_general?nombres=alberto+mu%C3%B1os&apellidoPaterno=cruz&fechaNacimiento=29/04/1990";

        SicoEmployeeSearchRequestTO searchRequestTO = new SicoEmployeeSearchRequestTO();
        List<String> names = new ArrayList<>();
        names.add("alberto");
        names.add("muños");

        searchRequestTO.setNames(names);

        searchRequestTO.setLastName("cruz");

        searchRequestTO.setBirthday("29-04-1990");

        String result = this.paysheetsicoService.buildSearchUrl(searchRequestTO);

        Assert.assertEquals(expectedUrl, result);

    }


    @Test
    public void souldGetDatafromSicoByParams() {
        String expectedResult = "{\"total\":1,\"to\":100,\"prev_page_url\":null,\"per_page\":100,\"next_page_url\":null,\"last_page\":1,\"from\":1,\"data\":[{\"personal\":{\"rfc\":\"LAGS800522RTM\",\"paisNacimiento\":\"MEXICANA\",\"numCelular\":\"\",\"nss\":\"01236547789\",\"nombres\":\"SAMIR\",\"genero\":\"M\",\"fechaNacimiento\":\"22/05/1980\",\"estadoNacimiento\":\"VERACRUZ\",\"estadoCivil\":\"\",\"emailPersonal\":\"laragtz@prueba.com\",\"curp\":\"LAGS800522HVZRTM04\",\"apellidoPaterno\":\"LARA\",\"apellidoMaterno\":\"GUTIERREZ\"},\"direccion\":{\"numeroInterior\":\"A\",\"numeroExterior\":\"123\",\"estado\":\"PUEBLA\",\"delegacionMunicipio\":\"PUEBLA\",\"cp\":\"72014\",\"colonia\":\"BARRANCA HONDA\",\"calle\":\"13 PONIENTE\"}}],\"current_page\":1}";

        SicoEmployeeSearchRequestTO searchRequestTO = new SicoEmployeeSearchRequestTO();
        List<String> names = new ArrayList<>();
        names.add("alberto");
        names.add("muños");
        searchRequestTO.setNames(names);
        searchRequestTO.setLastName("cruz");

        String result = this.paysheetsicoService.getUserDataByParams(this.paysheetsicoService.buildSearchUrl(searchRequestTO));

        Assert.assertEquals(expectedResult, result);
    }



}
