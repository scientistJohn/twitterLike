package com.andrii.feed

import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

import static org.apache.kafka.clients.consumer.ConsumerConfig.*

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.andrii.feed.repository")
class Application {
    static void main(String[] args) {
        SpringApplication.run Application, args
    }

    @Bean
    ConsumerFactory<String, String> consumerFactory(@Value('${kafka.bootstrap.servers}') String bootstrapServers,
                                                    @Value('${kafka.group-id}') String groupId) {
        Map<String, Object> props = [:]
        props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
        props.put(GROUP_ID_CONFIG, groupId)
        props.put(AUTO_OFFSET_RESET_CONFIG, "earliest")
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
        return new DefaultKafkaConsumerFactory<>(props)
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
        def factory = new ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.setConsumerFactory(consumerFactory)
        factory
    }
}
