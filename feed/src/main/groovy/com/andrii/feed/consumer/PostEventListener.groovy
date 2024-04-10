package com.andrii.feed.consumer


import com.andrii.feed.model.FeedRecord
import com.andrii.feed.model.PostEvent
import com.andrii.feed.repository.FeedRecordRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class PostEventListener {
    @Autowired
    FeedRecordRepository repository

    @KafkaListener(topics = '${kafka.create-post-topic}')
    void listenPostCreated(@Header(name = KafkaHeaders.RECEIVED_KEY) String userId,
                           @Payload String postId) {
        def record = new FeedRecord(
                userId: userId,
                event: new PostEvent(postId: postId)
        )
        repository.save(record)
    }

    @KafkaListener(topics = '${kafka.delete-post-topic}')
    void listenPostDeleted(String postId) {
        repository.deletePostEvent(postId)
    }

}
