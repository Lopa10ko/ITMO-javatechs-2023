package ru.lopa10ko.owners;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.lopa10ko.dal.security.config.SecurityConfiguration;
import ru.lopa10ko.messaging.starter.configuration.MessagingConfiguration;

@Import({
    SecurityConfiguration.class,
    MessagingConfiguration.class
})
@SpringBootApplication
public class OwnersApplication {
    public static void main(String[] args) {
        SpringApplication.run(OwnersApplication.class, args);
    }
}