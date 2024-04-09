package com.andrii.like.model

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document("stats")
class Stats {
    @Id
    StatsId id
    String userId
    String parentId
    Long comments
    Long likes = 0

    static class StatsId {
        String objectId
        ObjectType objectType
    }
}
