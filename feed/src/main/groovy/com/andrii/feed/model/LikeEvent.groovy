package com.andrii.feed.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class LikeEvent implements FeedRecordEvent {
    String postId
    FeedEventType eventType = FeedEventType.LIKED
}
