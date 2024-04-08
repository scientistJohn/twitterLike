package com.andrii.feed.model

class LikeEvent implements FeedRecordEvent {
    String objectId
    ObjectType objectType
    FeedEventType eventType = FeedEventType.LIKED
}
