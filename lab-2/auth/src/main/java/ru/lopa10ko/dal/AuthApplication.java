package ru.lopa10ko.dal;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.lopa10ko.dal.client.config.CatClientConfiguration;
import ru.lopa10ko.dal.security.config.SecurityConfiguration;
import ru.lopa10ko.messaging.starter.configuration.MessagingConfiguration;
import ru.lopa10ko.owners.client.config.OwnersClientConfiguration;

@Import({
    SecurityConfiguration.class,
    MessagingConfiguration.class,
    OwnersClientConfiguration.class,
    CatClientConfiguration.class
})
@SpringBootApplication
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
