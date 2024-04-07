package com.andrii.content.repository

import com.andrii.content.model.Post
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository extends MongoRepository<Post, String> {
    Optional<Post> findByIdAndUserId(String postId, String userId)
}