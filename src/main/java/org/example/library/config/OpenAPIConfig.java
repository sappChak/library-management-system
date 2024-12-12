package org.example.library.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

   @Bean
   OpenAPI defineOpenApi() {
      Server server = new Server();
      server.setUrl("http://localhost:8080");
      server.setDescription("Development");

      Contact myContact = new Contact();
      myContact.setName("Andrii Konotop");
      myContact.setEmail("konotop401@gmail.com");

      Info information = new Info()
            .title("Library API")
            .version("1.0")
            .description("This API exposes endpoints to manage library.")
            .contact(myContact);

      SecurityScheme securityScheme = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(SecurityScheme.In.HEADER)
            .name("Authorization");

      SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

      return new OpenAPI()
            .info(information)
            .servers(List.of(server))
            .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
            .addSecurityItem(securityRequirement);
   }
}
