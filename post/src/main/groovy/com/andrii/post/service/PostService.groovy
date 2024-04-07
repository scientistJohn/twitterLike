package com.andrii.post.service


import com.andrii.post.model.Post
import com.andrii.post.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
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
        def post = getPost(postId, userId)
        post.text = updateRequest.text
        repository.save(post)
    }

    def getPost(String postId, String userId) {
        repository.findByIdAndUserId(postId, userId)
                .orElseThrow { new ResponseStatusException(HttpStatus.NOT_FOUND, "no such post") }
    }

    def deletePost(String postId, String userId) {
        def post = getPost(postId, userId)
        repository.delete(post)
    }

    def getPosts(String userId, int page, int size) {
        Pageable paging = PageRequest.of(page, size, Sort.Direction.DESC, "id")
        repository.findByUserId(userId, paging)
    }
}
