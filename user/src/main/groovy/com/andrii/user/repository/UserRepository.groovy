package com.andrii.user.repository

import com.andrii.user.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository extends MongoRepository<User, String> {

}