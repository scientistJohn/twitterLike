package com.andrii.content.service


import com.andrii.content.model.Post
import com.andrii.content.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class PostService {
    @Autowired
    PostRepository repository

    def createPost(Map createRequest, String userId) {
        repository.save(new Post(userId: userId, text: createRequest.text))
    }


    def updatePost(String postId, String userId, Map updateRequest) {
        def post = repository.findByIdAndUserId(postId, userId)
                .orElseThrow { new ResponseStatusException(HttpStatus.NOT_FOUND, "no such post") }
        post.text = updateRequest.text
        repository.save(post)
    }
}
