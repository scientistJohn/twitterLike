package com.andrii.user.controller

import com.andrii.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping('/user')
class UserController {
    @Autowired
    UserService service

    @GetMapping
    String getUser() {
        return "User"
    }

    @PostMapping
    def createUser(@RequestBody Map createRequest) {
        service.createUser(createRequest)
    }
}

