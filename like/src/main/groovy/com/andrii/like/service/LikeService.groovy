package com.andrii.like.service

import com.andrii.like.model.Like
import com.andrii.like.model.ObjectType
import com.andrii.like.producer.LikeEventProducer
import com.andrii.like.producer.LikeEventType
import com.andrii.like.repository.LikeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LikeService {
    @Autowired
    LikeRepository repository
    @Autowired
    LikeEventProducer producer

    void like(String userId, String objectId, ObjectType objectType) {
        Like like = new Like(userId: userId, objectId: objectId, objectType: objectType)
        repository.save(like)
        producer.notify(userId, objectId, objectType, LikeEventType.LIKE)
    }

    void unlike(String id, String userId) {
        def like = repository.findByIdAndUserId(id, userId)
        if (like) {
            repository.delete(like)
            producer.notify(userId, like.objectId, like.objectType, LikeEventType.LIKE)
        }
    }
}