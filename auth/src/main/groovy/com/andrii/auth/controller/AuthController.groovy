package com.andrii.auth.controller

import com.andrii.auth.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/auth')
class AuthController {
    @Autowired
    AuthService service

    @PostMapping
    void saveCredentials(@RequestBody Map saveCredentialsRequest) {
        service.saveCredentials(saveCredentialsRequest)
    }
}
