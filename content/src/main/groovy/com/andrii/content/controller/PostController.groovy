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

    @PostMapping("/{postId}/comment")
    void comment(@PathVariable("postId") String postId,
                 @RequestBody Map commentRequest,
                 @RequestParam("userId") String userId) {
        commentService.comment(postId, userId, commentRequest)
    }
}
