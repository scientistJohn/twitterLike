package com.andrii.user.consumer

import com.andrii.user.model.User
import com.andrii.user.repository.UserRepository
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class SubscriptionEventListener {
    @Autowired
    UserRepository repository
    def jsonSlurper = new JsonSlurper()

    @KafkaListener(topics = '${kafka.subscription-event-topic}')
    void listenSubscriptionEvents(String message) {
        def event = jsonSlurper.parseText(message) as Map<String, String>
        SubscriptionEvent type = SubscriptionEvent.valueOf(event.type as String)
        switch (type) {
            case SubscriptionEvent.SUBSCRIBE:
                def user = repository.findById(event.subscriberId).get()
                if (!user) {
                    return
                }
                def subscribed = repository.findById(event.subscribedId).get()
                if (!subscribed) {
                    return
                }
                user.subscriptions.add(new User.Subscription(id: subscribed.id, name: subscribed.name))
                repository.save(user)
                break
            case SubscriptionEvent.UNSUBSCRIBE:
                repository.deleteSubscribed(event.subscriberId, event.subscribedId)
                break
        }
    }
}
