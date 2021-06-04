package com.pw.lrs.infrastructure.kafka;

import com.google.protobuf.Message;
import com.pw.lrs.domain.match.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class KafkaEventPublisher implements EventPublisher {

    private static final String OUTPUT_TOPIC = "lrs-lost-reports-proto";

    private final KafkaTemplate<String, Message> kafka;

    @Autowired
    KafkaEventPublisher(KafkaTemplate<String, Message> kafka) {
        this.kafka = kafka;
    }

    @Override
    public <T extends Message> void publishDomainEvent(String userId, T event) {

        kafka.send(OUTPUT_TOPIC, userId, event)
            .addCallback(
                success -> log.debug("Event {} successfully sent to kafka", event),
                e -> log.error("Exception during sending event {} to kafka", event, e));
    }
}
