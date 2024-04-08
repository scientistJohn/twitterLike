package com.andrii.post.repository


import com.andrii.post.model.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.Update
import org.springframework.stereotype.Repository

@Repository
interface PostRepository extends MongoRepository<Post, String> {
    Optional<Post> findByIdAndUserId(String postId, String userId)

    @Query("{'id': ?0 }")
    @Update("{'\$inc' : {'commentsAmount':1}} ")
    void incrCommentsAmount(String postId)

    @Query("{'id': ?0 }")
    @Update("{'\$inc' : {'commentsAmount':-1}} ")
    void decrCommentsAmount(String postId)

    Page<Post> findByUserId(String userId, Pageable pageable)
}