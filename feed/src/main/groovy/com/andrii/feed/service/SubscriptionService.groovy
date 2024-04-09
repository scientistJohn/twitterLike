package com.andrii.feed.service

import com.andrii.feed.model.FeedEventType
import com.andrii.feed.model.FeedRecord
import com.andrii.feed.model.Subscription
import com.andrii.feed.model.SubscriptionEvent
import com.andrii.feed.producer.SubscriptionEventType
import com.andrii.feed.producer.SubscriptionNotifier
import com.andrii.feed.repository.FeedRecordRepository
import com.andrii.feed.repository.SubscriptionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SubscriptionService {
    @Autowired
    SubscriptionRepository repository
    @Autowired
    FeedRecordRepository feedRecordRepository
    @Autowired
    SubscriptionNotifier subscriptionNotifier

    void subscribeTo(String subscriberId, String subscribedId) {
        repository.save(new Subscription(subscriberId, subscribedId))
        def record = new FeedRecord(
                userId: subscriberId,
                event: new SubscriptionEvent(userId: subscribedId)
        )
        feedRecordRepository.save(record)
        def subscriptionEvent = [
                subscriberId: subscriberId,
                subscribedId: subscribedId,
                type: SubscriptionEventType.SUBSCRIBE
        ]
        subscriptionNotifier.notify(subscriptionEvent)
    }

    void unsubscribeFrom(String subscriberId, String subscribedId) {
        repository.delete(new Subscription(subscriberId, subscribedId))
        feedRecordRepository.deleteSubscriptionEvent(subscriberId, subscribedId, FeedEventType.SUBSCRIPTION)
        def subscriptionEvent = [
                subscriberId: subscriberId,
                subscribedId: subscribedId,
                type: SubscriptionEventType.UNSUBSCRIBE
        ]
        subscriptionNotifier.notify(subscriptionEvent)
    }
}
