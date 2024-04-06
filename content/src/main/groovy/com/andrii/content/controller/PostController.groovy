package com.andrii.content.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/post')
class PostController {

    @PostMapping
    String createPost() {
        return "Posts";
    }
}
