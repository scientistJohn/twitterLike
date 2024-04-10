package com.andrii.feed.repository

import com.andrii.feed.model.FeedEventType
import com.andrii.feed.model.FeedRecord
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface FeedRecordRepository extends MongoRepository<FeedRecord, String> {
    List<FeedRecord> findByUserIdIn(Collection<String> userId, Pageable pageable)

    @Query(value = "{'userId': ?0, 'event.userId': ?1, 'event.eventType': ?2}", delete = true)
    void deleteSubscriptionEvent(String subscriberId, String subscribedId, FeedEventType eventType)

    void deleteByUserId(String userId)

    @Query(value = "{'event.userId': ?0, 'event.eventType': ?1}", delete = true)
    void deleteSubscriptionsToUserEvents(String userId, FeedEventType eventType)

    @Query(value = "{'event.commentId': ?0}", delete = true)
    void deleteCommentEvent(String commentId)

    @Query(value = "{'event.postId': ?0}", delete = true)
    void deletePostEvent(String postId)

    @Query(value = "{'userId': ?0, 'event.postId': ?1, 'event.eventType': ?2}", delete = true)
    void deleteLikeEvent(String userId, String postId, FeedEventType eventType)
}