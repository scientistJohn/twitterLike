package com.andrii.feed.consumer

import com.andrii.feed.model.CommentEvent
import com.andrii.feed.model.FeedRecord
import com.andrii.feed.repository.FeedRecordRepository
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class CommentEventListener {
    @Autowired
    FeedRecordRepository repository
    def slurper = new JsonSlurper()

    @KafkaListener(topics = '${kafka.create-comment-topic}')
    void listenCommentCreated(@Header(name = KafkaHeaders.RECEIVED_KEY) String userId,
                              @Payload String message) {
        def comment = slurper.parseText(message) as Map<String, String>
        def record = new FeedRecord(
                userId: userId,
                event: new CommentEvent(postId: comment.postId, commentId: comment.commentId)
        )
        repository.save(record)
    }

    @KafkaListener(topics = '${kafka.delete-comment-topic}')
    void listenCommentDeleted(String message) {
        def comment = slurper.parseText(message) as Map<String, String>
        repository.deleteCommentEvent(comment.commentId)
    }
}
