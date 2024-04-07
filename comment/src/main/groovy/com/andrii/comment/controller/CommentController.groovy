package com.andrii.comment.controller

import com.andrii.comment.service.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class CommentController {
    @Autowired
    CommentService commentService

    @PostMapping("/post/{postId}/comment")
    void comment(@PathVariable("postId") String postId,
                 @RequestBody Map commentRequest,
                 @RequestParam("userId") String userId) {
        commentService.comment(postId, userId, commentRequest)
    }

    @DeleteMapping("/comment/{commentId}")
    void removeComment(@PathVariable("commentId") String commentId,
                       @RequestParam("userId") String userId) {
        commentService.removeComment(commentId, userId)
    }

    @GetMapping("/post/{postId}/comment")
    def getComments(@PathVariable("postId") String postId,
                    @RequestParam(name = "page", defaultValue = "0") int page,
                    @RequestParam(name = "size", defaultValue = "10") int size) {
        commentService.getComments(postId, page, size)
    }

    @PostMapping("/comment")
    def getCommentsList(@RequestBody List<String> commentIds) {
        commentService.getCommentsList(commentIds)
    }
}
