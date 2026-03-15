package io.codepred.task.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Todo Tasks API")
                        .version("1.0")
                        .description("REST API for managing todo tasks")
                )
                .addServersItem(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local server")
                );
    }
}
