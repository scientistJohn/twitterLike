package com.andrii.content.controller

import com.andrii.content.service.CommentService
import com.andrii.content.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping('/post')
class PostController {
    @Autowired
    PostService service
    @Autowired
    CommentService commentService

    @PostMapping
    def createPost(@RequestBody Map createRequest, @RequestParam("userId") String userId) {
        service.createPost(createRequest, userId)
    }

    @PutMapping("/{postId}")
    def updatePost(@PathVariable("postId") String postId,
                   @RequestBody Map updateRequest,
                   @RequestParam("userId") String userId) {
        service.updatePost(postId, userId, updateRequest)
    }

    @DeleteMapping("/{postId}")
    void deletePost(@PathVariable("postId") String postId,
                    @RequestParam("userId") String userId) {
        service.deletePost(postId, userId)
    }

    @GetMapping("/{postId}")
    def getPost(@PathVariable("postId") String postId,
                @RequestParam("userId") String userId) {
        service.getPost(postId, userId)
    }

    @GetMapping
    def getPosts(@RequestParam("userId") String userId,
                 @RequestParam(name = "page", defaultValue = "0") int page,
                 @RequestParam(name = "size", defaultValue = "10") int size) {
        service.getPosts(userId, page, size)
    }

    @PostMapping("/{postId}/comment")
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

    @GetMapping("/{postId}/comment")
    def getComments(@PathVariable("postId") String postId,
                    @RequestParam(name = "page", defaultValue = "0") int page,
                    @RequestParam(name = "size", defaultValue = "10") int size) {
        commentService.getComments(postId, page, size)
    }
}
