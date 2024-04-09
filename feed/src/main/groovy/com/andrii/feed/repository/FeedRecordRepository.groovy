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
}