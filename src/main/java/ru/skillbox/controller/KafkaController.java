package ru.skillbox.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.dto.kafka.KafkaAuthEvent;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
@Slf4j
public class KafkaController {

    @Value("${app.kafka.kafkaAuthTopic}")
    private String topicName;

    private final KafkaTemplate<String, KafkaAuthEvent> kafkaAuthEventTemplate;

    @PostMapping("/send-auth")
    public String sendAuth(@RequestBody KafkaAuthEvent kafkaAuthEvent) {
        kafkaAuthEventTemplate.send(topicName, kafkaAuthEvent);
        return "Сообщение отправлено!";
    }
}
