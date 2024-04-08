package com.andrii.like.controller

import com.andrii.like.service.StatsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class StatsController {
    @Autowired
    StatsService service

    @PostMapping("/post/stats")
    def getPostsStats(@RequestBody List<String> postIds,
                      @RequestParam("userId") String userId) {
        service.getPostsStats(postIds, userId)
    }

    @PostMapping("/comment/stats")
    def getCommentsStats(@RequestBody List<String> commentIds,
                         @RequestParam("userId") String userId) {
        service.getCommentsStats(commentIds, userId)
    }
}
