package com.andrii.user.repository

import com.andrii.user.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Update
import org.springframework.scheduling.annotation.Async

interface UserRepository extends MongoRepository<User, String> {
    @Async
    @Update("{'\$pull': { 'subscriptions': {'id': ?0} }}")
    void deleteUserInSubscriptions(String userId)
}