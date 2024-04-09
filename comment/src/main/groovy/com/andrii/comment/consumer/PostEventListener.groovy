package com.andrii.comment.consumer

import com.andrii.comment.repository.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class PostEventListener {
    @Autowired
    CommentRepository repository

    @KafkaListener(topics = '${kafka.delete-post-topic}')
    void listenPostDeleted(String postId) {
        repository.deleteByPostId(postId)
    }
}
