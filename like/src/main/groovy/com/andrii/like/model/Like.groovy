package com.andrii.like.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("likes")
class Like {
    @Id
    String id
    String userId
    String objectId
    ObjectType objectType
}
