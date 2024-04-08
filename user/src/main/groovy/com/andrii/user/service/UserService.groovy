package com.andrii.user.service

import com.andrii.user.client.AuthServiceClient
import com.andrii.user.model.User
import com.andrii.user.producer.EventType
import com.andrii.user.producer.UserUpdateProducer
import com.andrii.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService {

    @Autowired
    UserRepository repository
    @Autowired
    AuthServiceClient authServiceClient
    @Autowired
    UserUpdateProducer updateProducer

    def createUser(Map createRequest) {
        def user = repository.save(new User(name: createRequest.name))
        def createCredentials = [login   : createRequest.credentials.login,
                                 password: createRequest.credentials.password,
                                 userId  : user.id]
        try {
            authServiceClient.saveCredentials(createCredentials)
        } catch (Exception e) {
            repository.delete(user)
            throw e
        }
        user
    }

    def updateUser(String userId, Map updateRequest) {
        def user = getUser(userId)
        user.name = updateRequest.name
        user = repository.save(user)
        updateProducer.notifyUpdated([eventType: EventType.UPDATED, user: user])
        user
    }

    def getUser(String userId) {
        repository.findById(userId)
                .orElseThrow { new ResponseStatusException(HttpStatus.NOT_FOUND, "no such user") }
    }

    def getAnotherUser(String anotherUserId, String userId) {
        def anotherUser = getUser(anotherUserId)
        def user = getUser(userId)
        def isSubscribed = user.subscriptions.any { it.id == anotherUserId }
        [id: anotherUser.id, name: anotherUser.name, isSubscribed: isSubscribed]
    }

    void deleteUser(String userId) {
        def user = getUser(userId)
        user = repository.delete(user)
        updateProducer.notifyUpdated([eventType: EventType.DELETED, user: user])
    }

}
