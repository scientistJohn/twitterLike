package com.andrii.post.producer

import com.andrii.post.model.Post
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class PostEventNotifier {
    @Autowired
    KafkaTemplate<String, String> producer
    @Value('${kafka.create-post-topic}')
    String createTopic
    @Value('${kafka.delete-post-topic}')
    String deleteTopic

    void notifyCreate(Post post) {
        producer.send(createTopic, post.userId, post.id)
    }

    void notifyDelete(String postId, String userId) {
        producer.send(deleteTopic, userId, postId)
    }
}
