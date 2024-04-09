package com.andrii.like.consumer

import com.andrii.like.model.ObjectType
import com.andrii.like.model.Stats
import com.andrii.like.repository.LikeRepository
import com.andrii.like.repository.StatsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class PostEventListener {
    @Autowired
    StatsRepository statsRepository
    @Autowired
    LikeRepository likeRepository

    @KafkaListener(topics = '${kafka.create-post-topic}')
    void listenPostCreated(@Header(name = KafkaHeaders.RECEIVED_KEY) String userId,
                           @Payload String postId) {
        def stats = new Stats(
                id: new Stats.StatsId(objectId: postId, objectType: ObjectType.POST),
                userId: userId,
                comments: 0
        )
        statsRepository.save(stats)
    }

    @KafkaListener(topics = '${kafka.delete-post-topic}')
    void listenPostDeleted(String postId) {
        statsRepository.deleteById(new Stats.StatsId(objectId: postId, objectType: ObjectType.POST))
        statsRepository.deleteByParentId(postId)
        likeRepository.deleteByObjectId(postId, ObjectType.POST)
        likeRepository.deleteByParentId(postId)
    }

}
