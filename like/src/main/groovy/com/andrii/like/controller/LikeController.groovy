package com.andrii.like.controller


import com.andrii.like.service.LikeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class LikeController {
    @Autowired
    LikeService service

    @PostMapping("/post/{postId}/like")
    void likePost(@PathVariable("postId") String postId,
                  @RequestParam("userId") String userId) {
        service.likePost(userId, postId)
    }

    @PostMapping("/post/{postId}/comment/{commentId}/like")
    void likeComment(@PathVariable("postId") String postId,
                     @PathVariable("commentId") String commentId,
                     @RequestParam("userId") String userId) {
        service.likeComment(userId, commentId, postId)
    }

    @DeleteMapping("/post/{postId}/like")
    void unlikePost(@PathVariable("postId") String postId,
                    @RequestParam("userId") String userId) {
        service.unlikePost(userId, postId)
    }

    @DeleteMapping("/post/{postId}/comment/{commentId}/like")
    void unlikeComment(@PathVariable("postId") String postId,
                       @PathVariable("commentId") String commentId,
                       @RequestParam("userId") String userId) {
        service.unlikeComment(userId, commentId, postId)
    }

}
