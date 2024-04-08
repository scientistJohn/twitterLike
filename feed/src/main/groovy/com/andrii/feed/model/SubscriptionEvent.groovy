package com.andrii.feed.model

class SubscriptionEvent implements FeedRecordEvent {
    String userId
    FeedEventType eventType = FeedEventType.SUBSCRIPTION
}
