package com.andrii.comment.service

import com.andrii.comment.model.Comment
import com.andrii.comment.producer.CommentEventProducer
import com.andrii.comment.producer.CommentEventType
import com.andrii.comment.repository.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CommentService {
    @Autowired
    CommentEventProducer eventProducer
    @Autowired
    CommentRepository repository

    void comment(String postId,
                 String userId,
                 Map commentRequest) {
        def comment = new Comment(userId: userId,
                postId: postId,
                text: commentRequest.text)
        repository.save(comment)
        eventProducer.notify(CommentEventType.CREATED, [:])
    }

    void removeComment(String commentId, String userId) {
        def comment = repository.findByIdAndUserId(commentId, userId)
                .orElseThrow { new ResponseStatusException(HttpStatus.NOT_FOUND, "no such comment") }
        repository.delete(comment)
        eventProducer.notify(CommentEventType.REMOVED, [:])
    }

    def getComments(String postId, int page, int size) {
        Pageable paging = PageRequest.of(page, size, Sort.Direction.DESC, "id")
        repository.findByPostId(postId, paging)
    }
}
