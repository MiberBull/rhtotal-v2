package mx.com.axity.services.client;

import mx.com.axity.services.BaseTest;
import org.junit.Assert;
import org.junit.Test;

public class SicoAdapterTest extends BaseTest {

    @Test
    public void shouldGetDataFromSicoGet() {

        String resultMock = "{\"total\":1,\"to\":100,\"prev_page_url\":null,\"per_page\":100,\"next_page_url\":null,\"last_page\":1,\"from\":1,\"data\":[{\"personal\":{\"rfc\":\"LAGS800522RTM\",\"paisNacimiento\":\"MEXICANA\",\"numCelular\":\"\",\"nss\":\"01236547789\",\"nombres\":\"SAMIR\",\"genero\":\"M\",\"fechaNacimiento\":\"22/05/1980\",\"estadoNacimiento\":\"VERACRUZ\",\"estadoCivil\":\"\",\"emailPersonal\":\"laragtz@prueba.com\",\"curp\":\"LAGS800522HVZRTM04\",\"apellidoPaterno\":\"LARA\",\"apellidoMaterno\":\"GUTIERREZ\"},\"direccion\":{\"numeroInterior\":\"A\",\"numeroExterior\":\"123\",\"estado\":\"PUEBLA\",\"delegacionMunicipio\":\"PUEBLA\",\"cp\":\"72014\",\"colonia\":\"BARRANCA HONDA\",\"calle\":\"13 PONIENTE\"}}],\"current_page\":1}";

        String result = this.sicoAdapter.get("https://nominaenlanube.com:8085/api-nomen/v1/get_empleado/laragtz@prueba.com");

        Assert.assertEquals(resultMock, result);
    }
}
