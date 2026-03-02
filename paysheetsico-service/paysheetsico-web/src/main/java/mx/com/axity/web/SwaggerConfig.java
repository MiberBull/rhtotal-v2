package mx.com.axity.web;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class SwaggerConfig {

    @Bean
    public OpenAPI paysheetsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PaysheetSico Service")
                        .description("PaysheetSico Service - Payroll Integration")
                        .version("2.0"));
    }
}
