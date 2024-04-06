package com.andrii.user.service

import com.andrii.user.client.AuthServiceClient
import com.andrii.user.model.User
import com.andrii.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    UserRepository repository
    @Autowired
    AuthServiceClient authServiceClient

    def createUser(Map createRequest) {
        def user = repository.save(new User(name: createRequest.name))
        def createCredentials = [login   : createRequest.credentials.login,
                                 password: createRequest.credentials.password,
                                 userId  : user.id]
        try {
            authServiceClient.createLoginCredentials(createCredentials)
        } catch (RuntimeException e) {
            repository.delete(user)
            throw e
        }
        user
    }
}
