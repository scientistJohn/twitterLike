package com.andrii.auth.repository

import com.andrii.auth.model.Credentials
import org.springframework.data.mongodb.repository.MongoRepository

interface CredentialsRepository extends MongoRepository<Credentials, String> {
    Credentials findByUserId(String userId)

    void deleteByUserId(String userId)
}
