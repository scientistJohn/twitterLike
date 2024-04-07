package com.andrii.post.controller


import com.andrii.post.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping('/post')
class PostController {
    @Autowired
    PostService service

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
}
