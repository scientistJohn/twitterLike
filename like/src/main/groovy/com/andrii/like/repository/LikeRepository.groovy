package com.andrii.like.repository

import com.andrii.like.model.Like
import com.andrii.like.model.ObjectType
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface LikeRepository extends MongoRepository<Like, String> {
    Like findByIdAndUserId(String id, String userId)

    @Query("{'userIs': ?0, 'objectId': { '\$in': ?1 }, 'objectType': ?2}")
    Collection<Like> findUserLikes(String userId, List<String> objectId, ObjectType objectType)
}