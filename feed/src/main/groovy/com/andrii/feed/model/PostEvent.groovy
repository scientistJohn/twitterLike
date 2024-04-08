package com.andrii.feed.model

class PostEvent implements FeedRecordEvent {
    String postId
    FeedEventType eventType = FeedEventType.POSTED
}
