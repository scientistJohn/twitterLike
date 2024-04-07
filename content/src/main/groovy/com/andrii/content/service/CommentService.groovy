package com.andrii.content.service

import com.andrii.content.model.Comment
import com.andrii.content.repository.CommentRepository
import com.andrii.content.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CommentService {
    @Autowired
    PostRepository postRepository
    @Autowired
    CommentRepository repository

    void comment(String postId,
                 String userId,
                 Map commentRequest) {
        def post = postRepository.findByIdAndUserId(postId, userId)
                .orElseThrow { new ResponseStatusException(HttpStatus.NOT_FOUND, "no such post") }
        def comment = new Comment(userId: userId,
                postId: postId,
                text: commentRequest.text)
        repository.save(comment)
        postRepository.incrCommentsAmount(post.id)
    }
    
}
