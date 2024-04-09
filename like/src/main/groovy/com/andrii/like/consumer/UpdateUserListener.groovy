package com.andrii.like.consumer

import com.andrii.like.repository.LikeRepository
import com.andrii.like.repository.StatsRepository
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UpdateUserListener {
    @Autowired
    StatsRepository statsRepository
    @Autowired
    LikeRepository likeRepository
    def jsonSlurper = new JsonSlurper()

    @KafkaListener(topics = '${kafka.user-event-topic}')
    void listenUsersEvents(String message) {
        def event = jsonSlurper.parseText(message) as Map<String, Object>
        UserEventType eventType = UserEventType.valueOf(event.eventType as String)
        if (UserEventType.DELETED == eventType) {
            statsRepository.deleteByUserId(event.user.userId)
            likeRepository.deleteByUserId(event.user.userId)
        }
    }
}
