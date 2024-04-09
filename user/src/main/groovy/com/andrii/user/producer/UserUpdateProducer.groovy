package com.andrii.user.producer

import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class UserUpdateProducer {
    @Autowired
    KafkaTemplate<String, String> producer
    @Value('${kafka.user-event-topic}')
    String topic

    void notifyUpdated(Map event) {
        producer.send(topic, event.user.userId, JsonOutput.toJson(event))
    }
}
