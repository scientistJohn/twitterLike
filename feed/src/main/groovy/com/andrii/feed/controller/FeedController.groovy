package com.andrii.feed.controller

import com.andrii.feed.service.FeedService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class FeedController {
    @Autowired
    FeedService service

    @GetMapping("/feed")
    def getFeed(@RequestParam("userId") String userId,
                @RequestParam(name = "page", defaultValue = "0") int page,
                @RequestParam(name = "size", defaultValue = "10") int size) {
        service.getFeed(userId, page, size)
    }
}
