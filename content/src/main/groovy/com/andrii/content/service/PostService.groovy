package com.andrii.content.service


import com.andrii.content.model.Post
import com.andrii.content.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PostService {
    @Autowired
    PostRepository repository

    def createPost(Map createRequest, String userId) {
        repository.save(new Post(userId: userId, text: createRequest.text))
    }



}
