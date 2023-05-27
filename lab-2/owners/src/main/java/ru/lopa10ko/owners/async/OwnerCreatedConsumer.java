package ru.lopa10ko.owners.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.lopa10ko.messaging.dto.constants.QueueName;
import ru.lopa10ko.messaging.dto.owner.OwnerCreateMessage;
import ru.lopa10ko.owners.service.OwnerService;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerCreatedConsumer {

    private final OwnerService ownerService;

    @RabbitListener(queues = QueueName.OWNER_CREATE)
    public void consume(OwnerCreateMessage customMessage) {
        log.info("Received {}", customMessage);

        ownerService.createCatOwner(
            customMessage.getUuid(),
            customMessage.getName(),
            customMessage.getBirthDay()
        );
    }
}
