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
    def getUserForHimself(@RequestParam("userId") String userId) {
        service.getUser(userId)
    }

    @GetMapping("/{userId}")
    def getAnotherUser(@PathVariable("userId") String anotherUserId, @RequestParam("userId") String userId) {
        service.getAnotherUser(anotherUserId, userId)
    }

    @PostMapping
    def createUser(@RequestBody Map createRequest) {
        service.createUser(createRequest)
    }

    @PutMapping
    def updateUser(@RequestBody Map updateRequest, @RequestParam("userId") String userId) {
        service.updateUser(userId, updateRequest)
    }

    @DeleteMapping
    void deleteUser(@RequestParam("userId") String userId) {
        service.deleteUser(userId)
    }
}

