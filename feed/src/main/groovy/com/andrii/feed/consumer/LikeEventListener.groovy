package com.andrii.feed.consumer

import com.andrii.feed.model.FeedEventType
import com.andrii.feed.model.FeedRecord
import com.andrii.feed.model.LikeEvent
import com.andrii.feed.repository.FeedRecordRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class LikeEventListener {
    @Autowired
    FeedRecordRepository repository

    @KafkaListener(topics = '${kafka.like-post-topic}')
    void listenCommentCreated(@Header(name = KafkaHeaders.RECEIVED_KEY) String userId,
                              @Payload String postId) {
        def record = new FeedRecord(
                userId: userId,
                event: new LikeEvent(postId: postId)
        )
        repository.save(record)
    }

    @KafkaListener(topics = '${kafka.unlike-post-topic}')
    void listenPostDeleted(@Header(name = KafkaHeaders.RECEIVED_KEY) String userId,
                           @Payload String postId) {
        repository.deleteLikeEvent(userId, postId, FeedEventType.LIKED)
    }
}
