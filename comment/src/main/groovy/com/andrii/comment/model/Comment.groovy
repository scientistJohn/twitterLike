package com.andrii.comment.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("comments")
class Comment {
    @Id
    String id
    String userId
    String postId
    String text
    String likesAmount = 0
}
