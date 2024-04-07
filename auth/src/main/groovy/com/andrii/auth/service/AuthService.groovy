package com.andrii.auth.service

import com.andrii.auth.model.Credentials
import com.andrii.auth.repository.CredentialsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthService {
    private final String emailPattern = /[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})/
    @Autowired
    CredentialsRepository credentialsRepository

    void saveCredentials(Map saveCredentialsRequest) {
        validateCredentials(saveCredentialsRequest)
        def credentials = new Credentials(login: saveCredentialsRequest.login,
                password: saveCredentialsRequest.password,
                userId: saveCredentialsRequest.userId)
        try {
            credentialsRepository.insert(credentials)
        } catch (DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid login")
        }
    }

    void validateCredentials(Map saveCredentialsRequest) {
        if (!(saveCredentialsRequest.login ==~ emailPattern)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid login")
        }

        if (!saveCredentialsRequest.password?.trim()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "empty password")
        }
    }
}
