package com.andrii.like.model

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document("likes")
class Like {
    @Id
    LikeId id
    String parentId

    static class LikeId {
        String userId
        String objectId
        ObjectType objectType
    }
}
