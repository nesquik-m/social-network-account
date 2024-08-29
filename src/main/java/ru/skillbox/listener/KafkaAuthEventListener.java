package ru.skillbox.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.entity.Account;
import ru.skillbox.service.AccountService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaAuthEventListener {

    private final AccountService accountService;

    @KafkaListener(topics = "${app.kafka.kafkaAuthTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "kafkaAuthEventConcurrentKafkaListenerContainerFactory")
    public void listenEventAuth(@Payload KafkaAuthEvent kafkaAuthEvent,
                                   @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                                   @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                   @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                                   @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {

//        System.out.println(kafkaAuthEvent);
        Account createdAccount = accountService.createAccount(kafkaAuthEvent);
//        System.out.println(createdAccount);

    }

}
