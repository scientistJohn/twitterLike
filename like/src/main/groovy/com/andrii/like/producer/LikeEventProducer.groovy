package com.andrii.like.producer


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class LikeEventProducer {
    @Autowired
    KafkaTemplate<String, String> producer
    @Value('${kafka.like-post-topic}')
    String likePostTopic
    @Value('${kafka.unlike-post-topic}')
    String unlikePostTopic

    void notifyLikePost(String userId, String postId) {
        producer.send(likePostTopic, userId, postId)
    }

    void notifyUnlikePost(String userId, String postId) {
        producer.send(unlikePostTopic, userId, postId)
    }
}
