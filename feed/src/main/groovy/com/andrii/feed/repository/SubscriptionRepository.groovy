package com.andrii.feed.repository

import com.andrii.feed.model.Subscription
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface SubscriptionRepository extends MongoRepository<Subscription, Subscription.SubscriptionId> {
    @Query("{'id.subscriberId': ?0}")
    List<Subscription> findAllBySubscriberId(String userId)

    @Query(value = "{ \$or: [{'id.subscriberId': ?0}, {'id.subscribedId': ?0}]}", delete = true)
    void deleteByUserId(String userId)
}