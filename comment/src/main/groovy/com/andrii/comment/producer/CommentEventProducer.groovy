package com.andrii.comment.producer


import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class CommentEventProducer {
    @Autowired
    KafkaTemplate<String, String> producer
    @Value('${kafka.create-comment-topic}')
    String createTopic
    @Value('${kafka.delete-comment-topic}')
    String deleteTopic

    void notifyCreate(String userId, String commentId, String postId) {
        producer.send(createTopic, userId, JsonOutput.toJson([commentId: commentId, postId: postId]))
    }

    void notifyDelete(String userId, String commentId, String postId) {
        producer.send(deleteTopic, userId, JsonOutput.toJson([commentId: commentId, postId: postId]))
    }
}
