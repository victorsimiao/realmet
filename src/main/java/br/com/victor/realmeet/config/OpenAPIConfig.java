package br.com.victor.realmeet.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("API Realmeet")
                                .version("v0.0.1")
                                .description("API fornece o agendamento e controle de reserva de salas.")
                                .contact(descriptionContact())
                                .license(descriptionLicense())
                )
                .externalDocs(
                        new ExternalDocumentation().description("Documentação")
                );

    }

    private Contact descriptionContact() {
        return new Contact()
                .name("Victor Simião")
                .email("victor.sreis@hotmail.com");
    }

    private License descriptionLicense() {
        return new License()
                .name("License")
                .url("#");
    }


}
