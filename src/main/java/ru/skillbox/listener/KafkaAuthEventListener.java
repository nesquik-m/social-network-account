package ru.skillbox.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.skillbox.dto.kafka.KafkaActionEvent;
import ru.skillbox.dto.kafka.KafkaAuthEvent;
import ru.skillbox.entity.Account;
import ru.skillbox.exception.AlreadyExistsException;
import ru.skillbox.repository.AccountRepository;
import ru.skillbox.service.AccountService;
import ru.skillbox.mapper.AccountMapper;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaAuthEventListener {

    @Value("${app.kafka.kafka-producer-new-account-topic}")
    private String topicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final AccountService accountService;

    private final AccountRepository accountRepository;

    @KafkaListener(topics = "${app.kafka.kafka-consumer-auth-topic}",
            groupId = "${app.kafka.kafka-message-group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenEventAuth(@Payload KafkaAuthEvent kafkaAuthEvent,
                                @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                                @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {

        try {
            Account createdAccount = accountService.createAccount(kafkaAuthEvent);
            kafkaTemplate.send(topicName, AccountMapper.accountToKafkaNewAccountEvent(createdAccount));
        } catch (AlreadyExistsException e) {
            log.info("Аккаунт с email {} уже существует. Пропускаем событие.", kafkaAuthEvent.getEmail());
        } catch (Exception e) {
            log.info("Ошибка при обработке события: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "${app.kafka.kafka-consumer-action-topic}",
            groupId = "${app.kafka.kafka-message-group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenEventAction(@Payload KafkaActionEvent kafkaActionEvent,
                                @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                                @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        try {
            accountRepository.updateOnlineStatus(kafkaActionEvent.getId());
        } catch (Exception e) {
            log.info("Ошибка при обработке события: {}", e.getMessage(), e);
        }
    }
}

