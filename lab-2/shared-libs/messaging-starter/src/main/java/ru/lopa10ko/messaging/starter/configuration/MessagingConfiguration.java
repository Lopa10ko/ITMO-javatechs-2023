package ru.lopa10ko.messaging.starter.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    RabbitConfiguration.class,
    QueueConfiguration.class
})
public class MessagingConfiguration {
}
