package com.andrii.feed.model

class CommentEvent implements FeedRecordEvent {
    String postId
    String commentId
    FeedEventType eventType = FeedEventType.COMMENTED
}
