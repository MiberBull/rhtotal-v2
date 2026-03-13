package mx.com.axity.gateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI gatewayOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RH Total API Gateway")
                        .description("API Gateway - Aggregated documentation for all microservices")
                        .version("2.0"));
    }

    @Bean
    public List<GroupedOpenApi> apis(RouteDefinitionLocator locator) {
        List<GroupedOpenApi> groups = new ArrayList<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        if (definitions != null) {
            definitions.stream()
                    .filter(rd -> rd.getId().endsWith("-service"))
                    .forEach(rd -> {
                        String name = rd.getId().replace("-service", "");
                        groups.add(GroupedOpenApi.builder()
                                .pathsToMatch("/api/" + name + "/**")
                                .group(name)
                                .build());
                    });
        }
        return groups;
    }
}
