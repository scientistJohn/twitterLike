package com.andrii.like.repository

import com.andrii.like.model.ObjectType
import com.andrii.like.model.Stats
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.Update

interface StatsRepository extends MongoRepository<Stats, Stats.StatsId> {
    @Query("{'id.objectId': ?0,  'id.objectType': ?1}")
    @Update("{'\$inc' : {'comments':1}} ")
    void incrComments(String objectId, ObjectType objectType)

    @Query("{'id.objectId': ?0,  'id.objectType': ?1}")
    @Update("{'\$inc' : {'comments':-1}} ")
    void decrComments(String objectId, ObjectType objectType)

    @Query("{'id.objectId': ?0,  'id.objectType': ?1}")
    @Update("{'\$inc' : {'likes':1}} ")
    void incrLikes(String objectId, ObjectType objectType)

    @Query("{'id.objectId': ?0,  'id.objectType': ?1}")
    @Update("{'\$inc' : {'likes':-1}} ")
    void decrLikes(String objectId, ObjectType objectType)

    void deleteByUserId(String userId)

    void deleteByParentId(String parentId)
}
