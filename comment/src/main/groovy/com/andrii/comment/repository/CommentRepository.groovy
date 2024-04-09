package com.andrii.comment.repository

import com.andrii.comment.model.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentRepository extends MongoRepository<Comment, String> {
    Optional<Comment> findByIdAndUserId(String commentId, String userId)

    Page<Comment> findByPostId(String postId, Pageable pageable)

    List<Comment> findAllByUserId(String userId)

    void deleteByUserId(String userId)
}