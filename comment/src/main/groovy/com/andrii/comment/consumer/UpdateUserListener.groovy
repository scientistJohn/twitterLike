package com.andrii.comment.consumer

import com.andrii.comment.repository.CommentRepository
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UpdateUserListener {
    @Autowired
    CommentRepository repository
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
