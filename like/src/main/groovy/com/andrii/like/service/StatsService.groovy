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
        def userLikes = likeRepository.findUserLikes(userId, ids, ObjectType.POST).collect { it.objectId } as HashSet
        repository.findAllById(ids.collect { new Stats.StatsId(objectId: it, objectType: ObjectType.POST) })
                .collect { [postId: it.id.objectId, comments: it.comments, likes: it.likes, isLikedByUser: userLikes.contains(it.id.objectId)] }
    }

    def getCommentsStats(List<String> ids, String userId) {
        def userLikes = likeRepository.findUserLikes(userId, ids, ObjectType.COMMENT).collect { it.objectId } as HashSet
        repository.findAllById(ids.collect(it -> new Stats.StatsId(objectId: it, objectType: ObjectType.POST)))
                .collect { [commentId: it.id.objectId, likes: it.likes, isLikedByUser: userLikes.contains(it.id.objectId)] }
    }
}
