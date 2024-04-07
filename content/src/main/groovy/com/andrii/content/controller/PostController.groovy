package com.andrii.content.controller

import com.andrii.content.service.PostService
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
}
