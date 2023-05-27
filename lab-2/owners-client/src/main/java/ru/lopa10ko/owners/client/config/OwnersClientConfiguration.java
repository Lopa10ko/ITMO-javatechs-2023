package ru.lopa10ko.owners.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.lopa10ko.owners.client.impl.HttpOwnersClient;
import ru.lopa10ko.owners.client.impl.OwnersWebClientConfig;

@Configuration
@Import({OwnersWebClientConfig.class, HttpOwnersClient.class})
public class OwnersClientConfiguration {
}
