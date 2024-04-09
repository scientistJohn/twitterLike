package com.andrii.comment.service

import com.andrii.comment.model.Comment
import com.andrii.comment.producer.CommentEventProducer
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
        comment = repository.save(comment)
        eventProducer.notifyCreate(comment.userId, comment.id, comment.postId)
    }

    void removeComment(String commentId, String userId) {
        def comment = repository.findByIdAndUserId(commentId, userId)
                .orElseThrow { new ResponseStatusException(HttpStatus.NOT_FOUND, "no such comment") }
        repository.delete(comment)
        eventProducer.notifyDelete(userId, commentId, comment.postId)
    }

    def getComments(String postId, int pageNum, int size) {
        Pageable paging = PageRequest.of(pageNum, size, Sort.Direction.DESC, "id")
        def page = repository.findByPostId(postId, paging)
        [
                comments     : page.getContent(),
                currentPage  : page.totalPages,
                totalPages   : page.totalPages,
                totalComments: page.totalElements
        ]
    }

    def getCommentsList(List<String> commentIds) {
        repository.findAllById commentIds
    }
}
