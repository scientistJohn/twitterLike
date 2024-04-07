package com.andrii.like.producer

import com.andrii.like.model.ObjectType
import org.springframework.stereotype.Component

@Component
class LikeEventProducer {
    void notify(String userId, String objectId, ObjectType objectType, LikeEventType eventType) {

    }
}
