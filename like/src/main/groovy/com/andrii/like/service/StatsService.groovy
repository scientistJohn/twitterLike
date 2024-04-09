package com.andrii.like.service

import com.andrii.like.model.ObjectType
import com.andrii.like.model.Stats
import com.andrii.like.repository.LikeRepository
import com.andrii.like.repository.StatsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StatsService {
    @Autowired
    StatsRepository repository
    @Autowired
    LikeRepository likeRepository

    def getPostsStats(List<String> ids, String userId) {
        def userLikes = likeRepository.findUserLikes(userId, ids, ObjectType.POST)
        def keys = ids.collect { new Stats.StatsId(objectId: it, objectType: ObjectType.POST) }
        def stats = repository.findAllById(keys)
                .collectEntries {
                    [
                            it.id.objectId,
                            [
                                    postId  : it.id.objectId,
                                    comments: it.comments,
                                    likes   : it.likes,
                                    userLike: userLikes.any { it.id.userId == userId }
                            ]
                    ]
                }
        ids.collect { stats.getOrDefault(it, getEmptyPostStats(it)) }
    }

    private static def getEmptyPostStats(String postId) {
        [
                postId  : postId,
                comments: 0,
                likes   : 0,
                userLike: null
        ]
    }

    def getCommentsStats(List<String> ids, String userId) {
        def userLikes = likeRepository.findUserLikes(userId, ids, ObjectType.COMMENT)
        def keys = ids.collect { it -> new Stats.StatsId(objectId: it, objectType: ObjectType.POST) }
        def stats = repository.findAllById(keys)
                .collectEntries {
                    [
                            it.id.objectId,
                            [
                                    commentId: it.id.objectId,
                                    likes    : it.likes,
                                    userLike : userLikes.any { it.id.userId == userId }
                            ]
                    ]
                }
        ids.collect { stats.getOrDefault(it, getEmptyCommentStats(it)) }
    }

    private static def getEmptyCommentStats(String commentId) {
        [
                commentId: commentId,
                likes    : 0,
                userLike : null
        ]
    }
}
