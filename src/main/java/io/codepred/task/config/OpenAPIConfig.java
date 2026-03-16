package io.codepred.task.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for OpenAPI documentation.
 * <p>
 * Configures the API documentation for the Tasks management endpoints,
 * including API metadata and server information.
 */
@Configuration
public class OpenAPIConfig {
    /**
     * Creates and configures the OpenAPI specification.
     *
     * @return configured OpenAPI instance with API info and server details
     */
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
