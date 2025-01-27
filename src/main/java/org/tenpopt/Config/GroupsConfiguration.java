package org.tenpopt.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GroupsConfiguration {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder().group("user").pathsToExclude("/api/v2/**").pathsToMatch("/api/v1/**").build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder().group("admin").pathsToExclude("/api/v1/**").pathsToMatch("/api/v2/**").build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components()).info(new Info().title("Spring MVC REST API")
                .contact(new Contact().name("Rasika Kaluwalgoda")).version("1.0.0"));
    }

}
