package com.andrii.like.consumer

import com.andrii.like.model.ObjectType
import com.andrii.like.model.Stats
import com.andrii.like.repository.LikeRepository
import com.andrii.like.repository.StatsRepository
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
    StatsRepository statsRepository
    @Autowired
    LikeRepository likeRepository
    def slurper = new JsonSlurper()

    @KafkaListener(topics = '${kafka.create-comment-topic}')
    void listenCommentCreated(@Header(name = KafkaHeaders.RECEIVED_KEY) String userId,
                              @Payload String message) {
        def comment = slurper.parseText(message) as Map<String, String>
        def stats = new Stats(
                id: new Stats.StatsId(objectId: comment.commentId, objectType: ObjectType.COMMENT),
                userId: userId,
                parentId: comment.postId
        )
        statsRepository.save(stats)
        statsRepository.incrComments(comment.postId, ObjectType.POST)
    }

    @KafkaListener(topics = '${kafka.delete-comment-topic}')
    void listenCommentDeleted(String message) {
        def comment = slurper.parseText(message) as Map<String, String>
        statsRepository.decrComments(comment.postId, ObjectType.POST)
        statsRepository.deleteById(new Stats.StatsId(objectId: comment.commentId, objectType: ObjectType.COMMENT))
        likeRepository.deleteByObjectId(comment.commentId, ObjectType.COMMENT)
    }
}
