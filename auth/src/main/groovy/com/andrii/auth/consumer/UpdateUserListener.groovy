package com.andrii.auth.consumer

import com.andrii.auth.repository.CredentialsRepository
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UpdateUserListener {
    @Autowired
    CredentialsRepository repository
    def jsonSlurper = new JsonSlurper()

    @KafkaListener(topics = '${kafka.user-event-topic}')
    void listenUsersEvents(String message) {
        def event = jsonSlurper.parseText(message) as Map<String, Object>
        UserEventType eventType = UserEventType.valueOf(event.eventType as String)
        switch (eventType) {
            case UserEventType.UPDATED:
                def credentials = repository.findByUserId(event.user.userId)
                if(!credentials) {
                    return
                }
                credentials.userName = event.user.name
                repository.save(credentials)
                break
            case UserEventType.DELETED:
                repository.deleteByUserId(event.user.userId)
                break
        }
    }
}
