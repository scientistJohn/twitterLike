package com.andrii.content.repository

import com.andrii.content.model.Comment
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentRepository extends MongoRepository<Comment, String> {

}