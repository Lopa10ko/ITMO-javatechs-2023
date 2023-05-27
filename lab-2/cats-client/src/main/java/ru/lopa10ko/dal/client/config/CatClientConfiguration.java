package ru.lopa10ko.dal.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.lopa10ko.dal.client.impl.CatWebClientConfig;
import ru.lopa10ko.dal.client.impl.HttpCatsClient;

@Configuration
@Import({CatWebClientConfig.class, HttpCatsClient.class})
public class CatClientConfiguration {
}
