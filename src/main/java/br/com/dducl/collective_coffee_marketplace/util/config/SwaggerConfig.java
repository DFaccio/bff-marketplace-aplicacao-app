package br.com.dducl.collective_coffee_marketplace.util.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Grupo de Compra Coletiva de Café ")
                                .description("API para gerenciamento do grupo, produtos e clientes")
                                .version("1.0.0")
                );
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("Marketplace")
                .packagesToScan("br.com.dducl.collective_coffee_marketplace.controller")
                .build();
    }
}
