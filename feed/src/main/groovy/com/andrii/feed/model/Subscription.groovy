package com.andrii.feed.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("subscriptions")
class Subscription {
    @Id
    SubscriptionId id

    Subscription() {
    }

    Subscription(String subscriberId, String subscribedId) {
        id = new SubscriptionId(subscriberId: subscriberId, subscribedId: subscribedId)
    }

    static class SubscriptionId {
        String subscriberId
        String subscribedId
    }
}
