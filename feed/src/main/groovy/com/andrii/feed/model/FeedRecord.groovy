package com.andrii.feed.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("feedRecords")
class FeedRecord {
    @Id
    String id
    String userId
    FeedRecordEvent event
}
