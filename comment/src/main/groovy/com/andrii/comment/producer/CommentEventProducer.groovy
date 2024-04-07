package com.andrii.comment.producer

import org.springframework.stereotype.Component

@Component
class CommentEventProducer {

    void notify(CommentEventType type, Map event) {

    }
}
