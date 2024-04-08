package com.andrii.feed.service

import com.andrii.feed.repository.FeedRecordRepository
import com.andrii.feed.repository.SubscriptionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class FeedService {
    @Autowired
    FeedRecordRepository feedRecordRepository
    @Autowired
    SubscriptionRepository subscriptionRepository

    def getFeed(String userId, int pageNum, int size) {
        def subscriptions = subscriptionRepository.findAllBySubscriberId(userId)
                .collect { it.id.subscribedId }
        Pageable paging = PageRequest.of(pageNum, size, Sort.Direction.DESC, "id")
        def feed = feedRecordRepository.findByUserIdIn(subscriptions, paging)
        if (!feed) {
            feed = feedRecordRepository.findAll(paging).getContent()
        }
        feed
    }
}
