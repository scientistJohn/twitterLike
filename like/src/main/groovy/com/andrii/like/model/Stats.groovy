package com.andrii.like.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("stats")
class Stats {
    @Id
    StatsId id
    long comments = 0
    long likes = 0

    static class StatsId {
        String objectId
        ObjectType objectType
    }
}
