package com.andrii.user.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
class User {
    @Id
    String id
    String name
    List<User> subscriptions = []
}
