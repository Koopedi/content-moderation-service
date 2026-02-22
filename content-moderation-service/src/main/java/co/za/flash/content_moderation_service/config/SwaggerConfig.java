package co.za.flash.content_moderation_service.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Content Moderation API")
                        .version("1.0")
                        .description("API for managing sensitive words and text sanitization")
                        .contact(new Contact()
                                .name("Tshepo Koopedi")
                                .email("koopedi98@gmail.com")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Project Repository")
                        .url("https://github.com/Koopedi/")
                );
    }
}
