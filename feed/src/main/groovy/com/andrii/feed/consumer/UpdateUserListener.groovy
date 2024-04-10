package com.andrii.feed.consumer

import com.andrii.feed.model.FeedEventType
import com.andrii.feed.repository.FeedRecordRepository
import com.andrii.feed.repository.SubscriptionRepository
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UpdateUserListener {
    @Autowired
    FeedRecordRepository feedRecordRepository
    @Autowired
    SubscriptionRepository subscriptionRepository
    def jsonSlurper = new JsonSlurper()

    @KafkaListener(topics = '${kafka.user-event-topic}')
    void listenUsersEvents(String message) {
        def event = jsonSlurper.parseText(message) as Map<String, Object>
        UserEventType eventType = UserEventType.valueOf(event.eventType as String)
        if (UserEventType.DELETED == eventType) {
            feedRecordRepository.deleteByUserId(event.user.userId)
            feedRecordRepository.deleteSubscriptionsToUserEvents(event.user.userId, FeedEventType.SUBSCRIPTION)
            subscriptionRepository.deleteByUserId(event.user.userId)
        }
    }
}
