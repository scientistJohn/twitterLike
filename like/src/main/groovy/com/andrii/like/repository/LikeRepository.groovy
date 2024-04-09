package com.andrii.like.repository

import com.andrii.like.model.Like
import com.andrii.like.model.ObjectType
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface LikeRepository extends MongoRepository<Like, Like.LikeId> {
    @Query("{'id.userId': ?0, 'id.objectId': { '\$in': ?1 }, 'id.objectType': ?2}")
    Collection<Like> findUserLikes(String userId, List<String> objectId, ObjectType objectType)

    @Query(value = "{'id.userId': ?0}", delete = true)
    void deleteByUserId(String userId)

    @Query(value = "{'id.objectId': ?0, 'id.objectType': ?1}", delete = true)
    void deleteByObjectId(String objectId, ObjectType objectType)

    void deleteByParentId(String parentId)

}