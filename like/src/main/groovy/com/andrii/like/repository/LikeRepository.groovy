package com.andrii.like.repository

import com.andrii.like.model.Like
import org.springframework.data.mongodb.repository.MongoRepository

interface LikeRepository extends MongoRepository<Like, String> {
    Like findByIdAndUserId(String id, String userId)
}