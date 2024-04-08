package com.andrii.feed.controller

import com.andrii.feed.service.SubscriptionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController("/subscription")
class SubscriptionController {
    @Autowired
    SubscriptionService service

    @PostMapping("/user/{userId}")
    void subscribeTo(@PathVariable("userId") String subscribedId,
                     @RequestParam("userId") String subscriberId) {
        service.subscribeTo(subscriberId, subscribedId)
    }

    @DeleteMapping("/user/{userId}")
    void unsubscribeFrom(@PathVariable("userId") String subscribedId,
                         @RequestParam("userId") String subscriberId) {
        service.unsubscribeFrom(subscriberId, subscribedId)
    }
}
