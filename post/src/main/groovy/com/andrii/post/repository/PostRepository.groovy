package com.andrii.post.repository

import com.andrii.post.model.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository extends MongoRepository<Post, String> {
    Optional<Post> findByIdAndUserId(String postId, String userId)

    Page<Post> findByUserId(String userId, Pageable pageable)
}