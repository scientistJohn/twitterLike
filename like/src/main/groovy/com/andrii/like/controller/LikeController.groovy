package com.andrii.like.controller

import com.andrii.like.model.ObjectType
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
        service.like(userId, postId, ObjectType.POST)
    }

    @PostMapping("/comment/{commentId}/like")
    void likeComment(@PathVariable("commentId") String commentId,
                     @RequestParam("userId") String userId) {
        service.like(userId, commentId, ObjectType.COMMENT)
    }

    @DeleteMapping("/like/{likeId}")
    void unlike(@PathVariable("likeId") String likeId,
                @RequestParam("userId") String userId) {
        service.unlike(likeId, userId)
    }

}
