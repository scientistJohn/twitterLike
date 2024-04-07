package com.andrii.content

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.andrii.content.repository")
class Application {
    static void main(String[] args) {
        SpringApplication.run Application, args
    }
}