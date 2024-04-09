package com.andrii.user


import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

import static org.apache.kafka.clients.producer.ProducerConfig.*

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.andrii.user.repository")
class Application {
    static void main(String[] args) {
        SpringApplication.run Application, args
    }

    @Bean
    ProducerFactory<String, String> producerFactory(@Value('${kafka.bootstrap.servers}') String bootstrapServers) {
        Map<String, Object> props = [:]
        props[BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer.class
        props[VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer.class
        new DefaultKafkaProducerFactory<>(props)
    }

    @Bean
    KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
        new KafkaTemplate<String, String>(producerFactory)
    }
}