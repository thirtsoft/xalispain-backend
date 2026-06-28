package com.wokite.net.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI xalispainOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Serveur de développement");

        Server prodServer = new Server();
        prodServer.setUrl("https://api.xalispain.com");
        prodServer.setDescription("Serveur de production");

        Contact contact = new Contact();
        contact.setName("Équipe Xalispain");
        contact.setEmail("support@xalispain.com");
        contact.setUrl("https://www.xalispain.com");

        License license = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0");

        Info info = new Info()
                .title("API Xalispain - Gestion des Référentiels")
                .description("""
                        API REST pour la gestion des référentiels Xalispain.
                        
                        ## Fonctionnalités
                        * Gestion des fournisseurs
                        * Gestion des communes, départements, régions
                        * Gestion des modes de paiement
                        * Gestion des types de dépenses
                        
                        ## Authentification
                        Certaines routes nécessitent un token JWT.
                        """)
                .version("1.0.0")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer));
    }
}