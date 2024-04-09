package com.andrii.post.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("posts")
class Post {
    @Id
    String id
    String userId
    String userName
    String text
}
