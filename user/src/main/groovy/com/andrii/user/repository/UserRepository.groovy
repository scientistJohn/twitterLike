package com.andrii.user.repository

import com.andrii.user.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.Update
import org.springframework.scheduling.annotation.Async

interface UserRepository extends MongoRepository<User, String> {
    @Async
    @Query("{}")
    @Update("{'\$pull': { 'subscriptions': {'id': ?0} }}")
    void deleteUserInSubscriptions(String userId)

    @Query("{'id': ?0}")
    @Update("{'\$pull': { 'subscriptions': {'id': ?1} }}")
    void deleteSubscribed(String userId, String subscribed)
}