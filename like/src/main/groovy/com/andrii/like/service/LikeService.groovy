package com.andrii.like.service

import com.andrii.like.model.Like
import com.andrii.like.model.ObjectType
import com.andrii.like.producer.LikeEventProducer
import com.andrii.like.repository.LikeRepository
import com.andrii.like.repository.StatsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LikeService {
    @Autowired
    LikeRepository repository
    @Autowired
    LikeEventProducer producer
    @Autowired
    StatsRepository statsRepository

    void likePost(String userId, String postId) {
        def like = new Like(id: new Like.LikeId(userId: userId, objectId: postId, objectType: ObjectType.POST))
        repository.save(like)
        statsRepository.incrLikes(postId, ObjectType.POST)
        producer.notifyLikePost(userId, postId)
    }

    void likeComment(String userId, String commentId, String postId) {
        def id = new Like.LikeId(userId: userId, objectId: commentId, objectType: ObjectType.COMMENT)
        def like = new Like(id: id, parentId: postId)
        repository.save(like)
        statsRepository.incrLikes(commentId, ObjectType.COMMENT)
    }

    void unlikePost(String userId, String postId) {
        def id = new Like.LikeId(userId: userId, objectId: postId, objectType: ObjectType.POST)
        repository.findById(id)
                .ifPresent { like ->
                    repository.delete(like)
                    statsRepository.decrLikes(postId, ObjectType.POST)
                    producer.notifyUnlikePost(userId, postId)
                }
    }

    void unlikeComment(String userId, String commentId, String postId) {
        def id = new Like.LikeId(userId: userId, objectId: commentId, objectType: ObjectType.COMMENT)
        repository.findById(id)
                .ifPresent { like ->
                    repository.delete(like)
                    statsRepository.decrLikes(commentId, ObjectType.COMMENT)
                }
    }
}
