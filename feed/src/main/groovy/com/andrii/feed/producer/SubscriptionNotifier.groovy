package com.andrii.feed.producer

import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class SubscriptionNotifier {
    @Autowired
    KafkaTemplate<String, String> producer
    @Value('${kafka.subscription-event-topic}')
    String topic

    void notify(Map event) {
        producer.send(topic, event.subscriberId, JsonOutput.toJson(event))
    }
}
