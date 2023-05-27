package ru.lopa10ko.messaging.starter.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lopa10ko.messaging.dto.constants.QueueName;

@Configuration
public class QueueConfiguration {
    @Bean
    public Queue ownerCreate() {
        return new Queue(QueueName.OWNER_CREATE);
    }

    @Bean
    public Queue catCreate() {
        return new Queue(QueueName.CAT_CREATE);
    }

    @Bean
    public Queue addFriend() {
        return new Queue(QueueName.ADD_FRIEND);
    }

    @Bean
    public Queue addFriendAdmin() {
        return new Queue(QueueName.ADD_FRIEND_ADMIN);
    }
}
