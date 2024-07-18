package com.aashdit.prod.cmc.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ibm.icu.text.IDNA.Info;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;


@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition()
@Configuration
public class SwaggerOpenApi30Config {

    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI();
//                .info(new Info().title("HEADS CORE API's")
//                        .version("1.0.0")
//                        .description("HEADS CORE API's Documentation ")
//                        .license(new License()
//                                .name("Apache 2.0"))
//                        .contact(new Contact()
//                                .name("Badal Kumar Behera")
//                                .email("badal.behera@aashdit.com")
//                                .url("https://www.aashdit.com")));
    }
}


