package com.andrii.auth.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("credentials")
class Credentials {
    @Id
    String login
    String password
    String userId
    String userName
}
