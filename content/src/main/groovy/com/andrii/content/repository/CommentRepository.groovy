package com.andrii.content.repository

import com.andrii.content.model.Comment
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentRepository extends MongoRepository<Comment, String> {
    Optional<Comment> findByIdAndUserId(String commentId, String userId)

    List<Comment> findByPostId(String postId, Pageable pageable)
}