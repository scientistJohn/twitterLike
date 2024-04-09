package com.andrii.post.consumer

import com.andrii.post.repository.PostRepository
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UpdateUserListener {
    @Autowired
    PostRepository repository
    def jsonSlurper = new JsonSlurper()

    @KafkaListener(topics = '${kafka.user-event-topic}')
    void listenUsersEvents(String message) {
        def event = jsonSlurper.parseText(message) as Map<String, Object>
        UserEventType eventType = UserEventType.valueOf(event.eventType as String)
        switch (eventType) {
            case UserEventType.UPDATED:
                def comments = repository.findAllByUserId(event.user.userId)
                        .each { it.userName = event.user.name }
                repository.saveAll(comments)
                break
            case UserEventType.DELETED:
                repository.deleteByUserId(event.user.userId)
                break
        }
    }
}
